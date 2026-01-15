package com.tinnyman.shardsoffortune.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.array.ArrayCodec;

public class LootPool {
    private static final ArrayCodec<LootEntry> LOOT_ENTRY_ARRAY_CODEC = new ArrayCodec<>(LootEntry.CODEC, LootEntry[]::new);

    public static final BuilderCodec<LootPool> CODEC =
            BuilderCodec.builder(LootPool.class, LootPool::new)
                    .append(new KeyedCodec<>("Id", Codec.STRING),
                            (p, v, info) -> p.id = v,
                            (p, info) -> p.id)
                    .add()
                    .append(new KeyedCodec<>("RollsMin", Codec.INTEGER),
                            (p, v, info) -> p.rollsMin = v,
                            (p, info) -> p.rollsMin)
                    .add()
                    .append(new KeyedCodec<>("RollsMax", Codec.INTEGER),
                            (p, v, info) -> p.rollsMax = v,
                            (p, info) -> p.rollsMax)
                    .add()
                    .append(new KeyedCodec<>("OverallDropChance", Codec.DOUBLE),
                            (p, v, info) -> p.overallDropChance = v,
                            (p, info) -> p.overallDropChance)
                    .add()
                    .append(new KeyedCodec<>("AllowDuplicates", Codec.BOOLEAN),
                            (p, v, info) -> p.allowDuplicates = v,
                            (p, info) -> p.allowDuplicates)
                    .add()
                    .append(new KeyedCodec<>("Loot", LOOT_ENTRY_ARRAY_CODEC),
                            (p, v, info) -> p.loot = v,
                            (p, info) -> p.loot)
                    .add()
                    .build();

    private String id = "Ores";
    private int rollsMin = 1;
    private int rollsMax = 3;
    private double overallDropChance = 1.0;
    private boolean allowDuplicates = true;
    private LootEntry[] loot = new LootEntry[0];

    public LootPool() {}

    public LootPool(String id, int rollsMin, int rollsMax, double overallDropChance, boolean allowDuplicates, LootEntry[] loot) {
        this.id = id;
        this.rollsMin = rollsMin;
        this.rollsMax = rollsMax;
        this.overallDropChance = overallDropChance;
        this.allowDuplicates = allowDuplicates;
        this.loot = loot;
    }

    public String getId() { return id; }
    public int getRollsMin() { return rollsMin; }
    public int getRollsMax() { return rollsMax; }
    public double getOverallDropChance() { return overallDropChance; }
    public boolean isAllowDuplicates() { return allowDuplicates; }
    public LootEntry[] getLoot() { return loot; }
}
