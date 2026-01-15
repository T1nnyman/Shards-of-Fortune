package com.tinnyman.shardsoffortune.services;

import com.tinnyman.shardsoffortune.config.LootEntry;
import com.tinnyman.shardsoffortune.config.LootPool;
import com.tinnyman.shardsoffortune.config.SofConfig;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class LootService {
    public record Drop(String itemId, int count) {}

    public List<Drop> rollDrops(SofConfig cfg, LootPool pool) {
        List<Drop> out = new ArrayList<>();
        if (cfg == null || !cfg.isEnabled()) return out;
        if (pool == null) return out;

        LootEntry[] entries = pool.getLoot();
        if (entries == null || entries.length == 0) return out;

        ThreadLocalRandom rng = ThreadLocalRandom.current();

        // overall chance gate (per-pool)
        if (rng.nextDouble() > clamp01(pool.getOverallDropChance())) return out;

        int minRolls = Math.max(0, pool.getRollsMin());
        int maxRolls = Math.max(minRolls, pool.getRollsMax());
        int rolls = rng.nextInt(minRolls, maxRolls + 1);
        if (rolls <= 0) return out;

        Set<Integer> used = pool.isAllowDuplicates() ? null : new HashSet<>();

        int safety = 0;
        while(out.size() < rolls && safety++ < 1000) {
            Integer idx = pickWeightedIndex(entries, used, rng);
            if (idx == null) break;

            LootEntry e = entries[idx];
            if (e == null) {
                if (used != null) used.add(idx);
                continue;
            }

            // entry chance gate
            if (rng.nextDouble() > clamp01(e.getRollChance())) {
                continue; // doesn't consume roll
            }

            int cmin = Math.max(0, e.getCountMin());
            int cmax = Math.max(cmin, e.getCountMax());
            int count = rng.nextInt(cmin, cmax + 1);

            String itemId = e.getItemId();
            if (count > 0 && itemId != null && !itemId.isBlank()) {
                out.add(new Drop(itemId, count));
                if (used != null) used.add(idx);
            } else {
                if (used != null) used.add(idx);
            }
        }

        return out;
    }

    private static Integer pickWeightedIndex(LootEntry[] entries, Set<Integer> used, ThreadLocalRandom rng) {
        int total = 0;

        for (int i = 0; i < entries.length; i++) {
            if (used != null && used.contains(i)) continue;
            LootEntry e = entries[i];
            if (e == null) continue;
            int w = Math.max(0, e.getWeight());
            total += w;
        }

        if (total <= 0) return null;

        int roll = rng.nextInt(total);
        int running = 0;

        for (int i = 0; i < entries.length; i++) {
            if (used != null && used.contains(i)) continue;
            LootEntry e = entries[i];
            if (e == null) continue;
            int w = Math.max(0, e.getWeight());
            running +=w;
            if (roll < running) return i;
        }

        return null;
    }

    private static double clamp01(double v) {
        if (v < 0) return 0;
        if (v > 1) return 1;
        return v;
    }
}
