package com.tinnyman.shardsoffortune.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class LootEntry {
    public static final BuilderCodec<LootEntry> CODEC =
            BuilderCodec.builder(LootEntry.class, LootEntry::new)
                    .append(new KeyedCodec<>("ItemId", Codec.STRING),
                            (o, v, i) -> o.ItemId = v,
                            (o, i) -> o.ItemId)
                    .add()
                    .append(new KeyedCodec<>("Weight", Codec.INTEGER),
                            (o, v, i) -> o.Weight = v,
                            (o, i) -> o.Weight)
                    .add()
                    .append(new KeyedCodec<>("CountMin", Codec.INTEGER),
                            (o, v, i) -> o.CountMin = v,
                            (o, i) -> o.CountMin)
                    .add()
                    .append(new KeyedCodec<>("CountMax", Codec.INTEGER),
                            (o, v, i) -> o.CountMax = v,
                            (o, i) -> o.CountMax)
                    .add()
                    .append(new KeyedCodec<>("RollChance", Codec.DOUBLE),
                            (o, v, i) -> o.RollChance = v,
                            (o, i) -> o.RollChance)
                    .add()
                    .build();

    // Defaults (only used if JSON omits fields)
    private String ItemId = "Ore_Copper";
    private int Weight = 10;
    private int CountMin = 1;
    private int CountMax = 1;
    private double RollChance = 1.0;

    public LootEntry() {}

    public LootEntry(String ItemId, int Weight, double RollChance, int CountMin, int CountMax) {
        this.ItemId = ItemId;
        this.Weight = Weight;
        this.CountMin = CountMin;
        this.CountMax = CountMax;
        this.RollChance = RollChance;
    }

    public String getItemId() { return ItemId; }
    public int getWeight() { return Weight; }
    public int getCountMin() { return CountMin; }
    public int getCountMax() { return CountMax; }
    public double getRollChance() { return RollChance; }
}
