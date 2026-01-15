package com.tinnyman.shardsoffortune.config;

import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.array.ArrayCodec;

import java.util.*;

public class SofConfig {
    private static final ArrayCodec<LootPool> LOOT_POOL_ARRAY_CODEC = new ArrayCodec<>(LootPool.CODEC, LootPool[]::new);
    private static final ArrayCodec<TargetPoolRule> TARGET_POOL_RULE_ARRAY_CODEC = new ArrayCodec<>(TargetPoolRule.CODEC, TargetPoolRule[]::new);

    public static final BuilderCodec<SofConfig> CODEC =
            BuilderCodec.builder(SofConfig.class, SofConfig::new)
                    .append(new KeyedCodec<>("Enabled", Codec.BOOLEAN),
                            (c, v, info) -> c.Enabled = v,
                            (c, info) -> c.Enabled)
                    .add()
                    .append(new KeyedCodec<>("Documentation", Codec.STRING_ARRAY),
                            (c, v, info) -> c.Documentation = v,
                            (c, info) -> c.Documentation)
                    .add()
                    .append(new KeyedCodec<>("TargetPools", TARGET_POOL_RULE_ARRAY_CODEC),
                            (c, v, info) -> { c.TargetPools = v; c.CachedPoolByTarget = null; },
                            (c, info) -> c.TargetPools)
                    .add()
                    .append(new KeyedCodec<>("LootPools", LOOT_POOL_ARRAY_CODEC),
                            (c, v, info) -> { c.LootPools = v; c.CachedPoolsById = null; },
                            (c, info) -> c.LootPools)
                    .add()
                    .build();

    // ------------ Defaults ------------
    private String[] Documentation = new String[] {
            "Shards-of-Fortune config",
            "",
            "How this works:",
            "- When a block is broken, we look for a matching TargetPools rule.",
            "- If the broken block id appears in Targets[], we roll drops from that rule's PoolId.",
            "",
            "Notes:",
            "- PoolId must match a LootPools[].Id (case-sensitive).",
            "- ItemId values must be valid Hytale item asset ids.",
            "- Target values must be valid Hytale block asset ids.",
            "- RollsMin/RollsMax control how many successful loot picks are attempted per break.",
            "- OverallDropChance is a gate for the entire pool (0.0 - 1.0).",
            "- Each Loot entry also has RollChance (0.0 - 1.0).",
            "",
            "Tip: create multiple TargetPools rules to route different breakables to different loot pools."
    };

    private boolean Enabled = true;

    private TargetPoolRule[] TargetPools = new TargetPoolRule[] {
            new TargetPoolRule(
                    "Pots",     // Name
                    "Ores",     // PoolId (LootPool Id)
                    new String[] {
                            "Furniture_Frozen_Castle_Pot",
                            "Furniture_Frozen_Castle_Secondary_Pot",
                            "Furniture_Jungle_Pot",
                            "Furniture_Jungle_Pot_Secondary",
                            "Furniture_Human_Ruins_Pot",
                            "Furniture_Human_Ruins_Pot_Small",
                            "Furniture_Feran_Pot",
                            "Furniture_Feran_Pot_Secondary",
                            "Furniture_Ancient_Pot",
                            "Furniture_Royal_Magic_Pot",
                            "Furniture_Village_Pot",
                            "Furniture_Temple_Scarak_Pot",
                            "Furniture_Temple_Dark_Pot",
                            "Furniture_Temple_Light_Pot",
                            "Furniture_Temple_Wind_Pot"
                    }
            )
    };

    private LootPool[] LootPools = new LootPool[] {
            new LootPool(
                    "Ores",
                    1,      // RollsMin
                    3,      // RollsMax
                    1.0,    // OverallDropChance
                    true,   // AllowDuplicates
                    new LootEntry[] {
                            new LootEntry("Ore_Copper", 10, 1.0, 1, 2),
                            new LootEntry("Ore_Iron",    6, 0.8, 1, 1),
                            new LootEntry("Ore_Silver",  3, 0.5, 1, 1),
                    }
            ),
            new LootPool(
                    "Consumables",
                    1,      // RollsMin
                    2,      // RollsMax
                    0.60,   // OverallDropChance
                    true,   // AllowDuplicates
                    new LootEntry[] {
                            // Placeholder IDs — replace with real item ids you want.
                            new LootEntry("Potion_Health_Lesser",       10, 1.0, 1, 2),
                            new LootEntry("Potion_Health",   6, 0.8, 1, 1),
                            new LootEntry("Potion_Poison",   3, 0.5, 1, 1),
                    }
            )
    };

    // Caches
    private transient Map<String, LootPool> CachedPoolsById;
    private transient Map<String, LootPool> CachedPoolByTarget;

    public SofConfig() {}

    public boolean isEnabled() { return Enabled; }

    public LootPool resolvePoolForTarget(String blockId) {
        if (blockId == null || blockId.isBlank()) return  null;

        if (CachedPoolByTarget == null) {
            CachedPoolByTarget = new HashMap<>();
            Map<String, LootPool> poolsById = getPoolsById();
            if (TargetPools != null) {
                for (TargetPoolRule rule : TargetPools) {
                    if (rule == null) continue;
                    LootPool pool = poolsById.get(rule.getLootPoolId());
                    if (pool == null) continue;

                    String[] t = rule.getTargets();
                    if (t == null) continue;
                    for (String id : t) {
                        if (id == null || id.isBlank()) continue;
                        CachedPoolByTarget.put(id, pool);
                    }
                }
            }
        }

        return CachedPoolByTarget.get(blockId);
    }

    private Map<String, LootPool> getPoolsById() {
        if (CachedPoolsById == null) {
            CachedPoolsById = new HashMap<>();
            if (LootPools != null) {
                for (LootPool p : LootPools) {
                    if (p == null) continue;
                    String id = p.getId();
                    if (id == null || id.isBlank()) continue;
                    CachedPoolsById.put(id, p);
                }
            }
        }
        return CachedPoolsById;
    }
}
