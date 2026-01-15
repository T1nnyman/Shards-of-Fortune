package com.tinnyman.shardsoffortune.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class TargetPoolRule {
    public static final BuilderCodec<TargetPoolRule> CODEC =
            BuilderCodec.builder(TargetPoolRule.class, TargetPoolRule::new)
                    .append(new KeyedCodec<>("Name", Codec.STRING),
                            (r, v, info) -> r.name = v,
                            (r, info) -> r.name)
                    .add()
                    .append(new KeyedCodec<>("LootPoolId", Codec.STRING),
                            (r, v, info) -> r.lootPoolId = v,
                            (r, info) -> r.lootPoolId)
                    .add()
                    .append(new KeyedCodec<>("Targets", Codec.STRING_ARRAY),
                            (r, v, info) -> { r.targets = v; },
                            (r, info) -> r.targets)
                    .add()
                    .build();

    public String name = "Pots";
    public String lootPoolId = "default";
    private String[] targets = new String[0];

    public TargetPoolRule() {}

    public TargetPoolRule(String name, String poolId, String[] targets) {
        this.name = name;
        this.lootPoolId = poolId;
        this.targets = targets;
    }

    public String getLootPoolId() { return lootPoolId; }
    public String[] getTargets() { return targets; }
}
