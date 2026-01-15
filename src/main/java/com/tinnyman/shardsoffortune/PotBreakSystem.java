package com.tinnyman.shardsoffortune;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.event.events.ecs.BreakBlockEvent;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.tinnyman.shardsoffortune.config.LootPool;
import com.tinnyman.shardsoffortune.config.SofConfig;
import com.tinnyman.shardsoffortune.services.LootService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PotBreakSystem extends EntityEventSystem<EntityStore, BreakBlockEvent> {
    private final SofConfig config;
    private final LootService lootService;

    public PotBreakSystem(SofConfig config, LootService lootService) {
        super(BreakBlockEvent.class);
        this.config = config;
        this.lootService = lootService;
    }

    @Override
    public void handle(int index,
                       @Nonnull ArchetypeChunk<EntityStore> archetypeChunk,
                       @Nonnull Store<EntityStore> store,
                       @Nonnull CommandBuffer<EntityStore> commandBuffer,
                       @Nonnull BreakBlockEvent event
    ) {
        if (config == null || !config.isEnabled()) return;

        final String blockId = event.getBlockType().getId();
        if (blockId == null || blockId.isBlank()) return;

        // Resolve which pool this target should use (TargetPools -> DefaultPool fallback)
        final LootPool pool = config.resolvePoolForTarget(blockId);
        if (pool == null) return;

        // Roll drops from that pool
        final List<LootService.Drop> drops = lootService.rollDrops(config, pool);
        if (drops == null || drops.isEmpty()) return;

        final World world = store.getExternalData().getWorld();
        final Vector3d dropPos = blockCenter(event.getTargetBlock());

        world.execute(() -> {
            for (LootService.Drop d : drops) {
                if (d == null) continue;

                final String itemId = d.itemId();
                final int count = d.count();
                if (itemId == null || itemId.isBlank() || count <= 0) continue;

                spawnItemDrop(store, dropPos, itemId, count);
            }
        });
    }

    @Nonnull
    @Override
    public Query<EntityStore> getQuery() {
        return Archetype.empty();
    }

    @Nullable
    @Override
    public SystemGroup<EntityStore> getGroup() {
        return null;
    }

    private static com.hypixel.hytale.math.vector.Vector3d blockCenter(@Nonnull Vector3i block) {
        return new Vector3d(block.x + 0.5, block.y + 0.5, block.z + 0.5);
    }

    private static void spawnItemDrop(@Nonnull Store<EntityStore> store, @Nonnull com.hypixel.hytale.math.vector.Vector3d pos, @Nonnull String itemId, int count) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        float velX = (float) (random.nextGaussian() * 0.75f);
        float velY = 0.35f + random.nextFloat() * 0.25f;
        float velZ = (float) (random.nextGaussian() * 0.75f);

        var holder = ItemComponent.generateItemDrop(store, new ItemStack(itemId, count), pos, Vector3f.ZERO, velX, velY, velZ);
        if (holder == null) return;
        ItemComponent itemComponent = holder.getComponent(ItemComponent.getComponentType());
        if (itemComponent != null) {
            itemComponent.setPickupDelay(1.5f);
        }

        store.addEntity(holder, AddReason.SPAWN);
    }
}
