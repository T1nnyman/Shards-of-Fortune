package com.tinnyman.shardsoffortune.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class TargetPool {
    public static final BuilderCodec<TargetPool> CODEC =
            BuilderCodec.builder(TargetPool.class, TargetPool::new)
                    .append(new KeyedCodec<>("Name", Codec.STRING),
                            (r, v, info) -> r.name = v,
                            (r, info) -> r.name)
                    .add()
                    .append(new KeyedCodec<>("Mode", Codec.STRING),
                            (r, v, info) -> r.mode = v,
                            (r, info) -> r.mode)
                    .add()
                    .append(new KeyedCodec<>("LootPoolIds", Codec.STRING_ARRAY),
                            (r, v, info) -> r.lootPoolIds = v,
                            (r, info) -> r.lootPoolIds)
                    .add()
                    .append(new KeyedCodec<>("Targets", Codec.STRING_ARRAY),
                            (r, v, info) -> { r.targets = v; },
                            (r, info) -> r.targets)
                    .add()
                    .build();

    private String name = "Pots";
    private String[] lootPoolIds = new String[] { "Ores" };
    private String mode = "All";
    private String[] targets = new String[0];

    public TargetPool() {}

    public TargetPool(String name, String mode, String[] lootPoolIds, String[] targets) {
        this.name = name;
        this.mode = mode;
        this.lootPoolIds = lootPoolIds;
        this.targets = targets;
    }

    public String getName() { return name; }
    public String getMode() { return mode; }
    public String[] getLootPoolIds() { return lootPoolIds; }
    public String[] getTargets() { return targets; }
}
