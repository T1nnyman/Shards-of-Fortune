package com.tinnyman.shardsoffortune.config;

import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.array.ArrayCodec;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SofConfig {
    private static final ArrayCodec<LootPool> LOOT_POOL_ARRAY_CODEC = new ArrayCodec<>(LootPool.CODEC, LootPool[]::new);
    private static final ArrayCodec<TargetPool> TARGET_POOL_ARRAY_CODEC = new ArrayCodec<>(TargetPool.CODEC, TargetPool[]::new);

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
                    .append(new KeyedCodec<>("TargetPools", TARGET_POOL_ARRAY_CODEC),
                            (c, v, info) -> { c.TargetPools = v; },
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
            "- When a block is broken, we look for matching TargetPools rules.",
            "- Only blocks listed in Targets[] are affected (no global behavior).",
            "- Each TargetPool rule can route a block to one or more LootPools via LootPoolIds[].",
            "",
            "TargetPool Modes:",
            "- All: roll ALL LootPoolIds for matching blocks.",
            "- PickOne: pick ONE LootPoolId and roll only that pool.",
            "",
            "Notes:",
            "- LootPoolIds must match a LootPools[].Id (case-sensitive).",
            "- ItemId values must be valid Hytale item asset ids.",
            "- Target values must be valid Hytale block asset ids.",
            "- RollsMin/RollsMax control how many successful loot picks are attempted per pool roll.",
            "- OverallDropChance gates the entire pool (0.0 - 1.0).",
            "- Each Loot entry also has RollChance (0.0 - 1.0)."
    };

    private boolean Enabled = true;

    private TargetPool[] TargetPools = new TargetPool[] {
            new TargetPool(
                    "Pots",
                    "PickOne",
                    new String[] { "Gems", "Ores", "MetalBars", "Essences", "LeathersAndHides", "Fabrics", "CreatureMats", "Potions_Health", "Potions_Mana", "Potions_Stamina",
                            "Potions_Utility", "Weapons_Melee", "Weapons_Ranged", "Weapons_Magic", "Weapons_Explosives", "Tools", "Armor", "Special" },
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

            // ----------------------------
            // GEMS (rare)
            // ----------------------------
            new LootPool(
                    "Gems",
                    1,      // RollsMin
                    2,      // RollsMax
                    0.35,   // OverallDropChance
                    true,   // AllowDuplicates
                    new LootEntry[] {
                            new LootEntry("Rock_Gem_Emerald",    3, 0.70, 1, 1),
                            new LootEntry("Rock_Gem_Diamond",    1, 0.35, 1, 1),
                            new LootEntry("Rock_Gem_Sapphire",   2, 0.55, 1, 1),
                            new LootEntry("Rock_Gem_Ruby",       2, 0.55, 1, 1),
                            new LootEntry("Rock_Gem_Topaz",      3, 0.65, 1, 1),
                            new LootEntry("Rock_Gem_Zephyr",     1, 0.35, 1, 1),
                            new LootEntry("Rock_Gem_Voidstone",  1, 0.25, 1, 1),
                    }
            ),

            // ----------------------------
            // ORES (common)
            // ----------------------------
            new LootPool(
                    "Ores",
                    1,
                    3,
                    1.0,
                    true,
                    new LootEntry[] {
                            new LootEntry("Ore_Copper",      10, 1.00, 1, 2),
                            new LootEntry("Ore_Iron",         8, 0.95, 1, 2),
                            new LootEntry("Ore_Silver",       5, 0.80, 1, 1),
                            new LootEntry("Ore_Gold",         4, 0.70, 1, 1),
                            new LootEntry("Ore_Cobalt",       4, 0.70, 1, 1),
                            new LootEntry("Ore_Mithril",      3, 0.60, 1, 1),
                            new LootEntry("Ore_Thorium",      3, 0.55, 1, 1),
                            new LootEntry("Ore_Adamantite",   2, 0.45, 1, 1),
                            new LootEntry("Ore_Onyxium",      2, 0.45, 1, 1),
                            new LootEntry("Ore_Prisma",       2, 0.40, 1, 1),
                    }
            ),

            // ----------------------------
            // METAL BARS (uncommon)
            // ----------------------------
            new LootPool(
                    "MetalBars",
                    1,
                    2,
                    0.50,
                    true,
                    new LootEntry[] {
                            new LootEntry("Ingredient_Bar_Copper",     10, 0.95, 1, 2),
                            new LootEntry("Ingredient_Bar_Iron",        8, 0.85, 1, 2),
                            new LootEntry("Ingredient_Bar_Bronze",      7, 0.80, 1, 2),
                            new LootEntry("Ingredient_Bar_Silver",      5, 0.70, 1, 1),
                            new LootEntry("Ingredient_Bar_Gold",        4, 0.65, 1, 1),
                            new LootEntry("Ingredient_Bar_Cobalt",      4, 0.60, 1, 1),
                            new LootEntry("Ingredient_Bar_Thorium",     3, 0.55, 1, 1),
                            new LootEntry("Ingredient_Bar_Adamantite",  2, 0.45, 1, 1),
                            new LootEntry("Ingredient_Bar_Onyxium",     2, 0.45, 1, 1),
                            new LootEntry("Ingredient_Bar_Prisma",      2, 0.40, 1, 1),
                    }
            ),

            // ----------------------------
            // ESSENCES / RARE MYSTIC MATS
            // ----------------------------
            new LootPool(
                    "Essences",
                    1,
                    2,
                    0.40,
                    true,
                    new LootEntry[] {
                            new LootEntry("Ingredient_Life_Essence",      6, 0.75, 1, 2),
                            new LootEntry("Ingredient_Water_Essence",     6, 0.75, 1, 2),
                            new LootEntry("Ingredient_Fire_Essence",      5, 0.70, 1, 2),
                            new LootEntry("Ingredient_Ice_Essence",       5, 0.70, 1, 2),
                            new LootEntry("Ingredient_Lightning_Essence", 4, 0.60, 1, 1),
                            new LootEntry("Ingredient_Void_Essence",      2, 0.45, 1, 1),
                            new LootEntry("Ingredient_Voidheart",         1, 0.25, 1, 1),
                    }
            ),

            // ----------------------------
            // LEATHERS / HIDES
            // ----------------------------
            new LootPool(
                    "LeathersAndHides",
                    1,
                    3,
                    0.70,
                    true,
                    new LootEntry[] {
                            new LootEntry("Ingredient_Leather_Soft",    10, 0.95, 1, 2),
                            new LootEntry("Ingredient_Leather_Light",    8, 0.90, 1, 2),
                            new LootEntry("Ingredient_Leather_Heavy",    6, 0.80, 1, 1),
                            new LootEntry("Ingredient_Leather_Prismic",  3, 0.55, 1, 1),

                            new LootEntry("Ingredient_Hide_Light",       9,  0.90, 1, 2),
                            new LootEntry("Ingredient_Hide_Medium",      7,  0.85, 1, 2),
                            new LootEntry("Ingredient_Hide_Heavy",       5,  0.75, 1, 1),
                            new LootEntry("Ingredient_Hide_Storm",       3,  0.55, 1, 1),
                            new LootEntry("Ingredient_Hide_Prismic",     2,  0.45, 1, 1),
                    }
            ),

            // ----------------------------
            // FABRICS (scraps / cloth)
            // ----------------------------
            new LootPool(
                    "Fabrics",
                    1,
                    3,
                    0.75,
                    true,
                    new LootEntry[] {
                            new LootEntry("Ingredient_Fabric_Scrap_Cotton",     10, 0.95, 1, 3),
                            new LootEntry("Ingredient_Fabric_Scrap_Linen",       9, 0.90, 1, 3),
                            new LootEntry("Ingredient_Fabric_Scrap_Silk",        6, 0.80, 1, 2),
                            new LootEntry("Ingredient_Fabric_Scrap_Stormsilk",   4, 0.65, 1, 2),
                            new LootEntry("Ingredient_Fabric_Scrap_Cindercloth", 4, 0.65, 1, 2),
                            new LootEntry("Ingredient_Fabric_Scrap_Prismaloom",  2, 0.45, 1, 1),
                            new LootEntry("Ingredient_Fabric_Scrap_Shadoweave",  2, 0.45, 1, 1),
                    }
            ),

            // ----------------------------
            // CREATURE MATERIALS
            // ----------------------------
            new LootPool(
                    "CreatureMats",
                    1,
                    2,
                    0.55,
                    true,
                    new LootEntry[] {
                            new LootEntry("Ingredient_Chitin_Sturdy",  6, 0.75, 1, 2),
                            new LootEntry("Ingredient_Sac_Venom",      4, 0.60, 1, 1),
                            new LootEntry("Ingredient_Feathers_Dark",  5, 0.70, 1, 2),
                    }
            ),

            // ----------------------------
            // POTIONS: HEALTH
            // ----------------------------
            new LootPool(
                    "Potions_Health",
                    1,
                    2,
                    0.65,
                    true,
                    new LootEntry[] {
                            new LootEntry("Bandage_Crude",            12, 1.00, 1, 2),
                            new LootEntry("Potion_Health_Lesser",     10, 0.95, 1, 2),
                            new LootEntry("Potion_Health_Small",       8, 0.90, 1, 1),
                            new LootEntry("Potion_Health",             6, 0.80, 1, 1),
                            new LootEntry("Potion_Health_Large",        3, 0.55, 1, 1),
                            new LootEntry("Potion_Health_Greater",      2, 0.45, 1, 1),
                            new LootEntry("Potion_Regen_Health_Small",  4, 0.60, 1, 1),
                            new LootEntry("Potion_Regen_Health",        3, 0.55, 1, 1),
                            new LootEntry("Potion_Regen_Health_Large",  2, 0.45, 1, 1),
                    }
            ),

            // ----------------------------
            // POTIONS: MANA
            // ----------------------------
            new LootPool(
                    "Potions_Mana",
                    1,
                    2,
                    0.55,
                    true,
                    new LootEntry[] {
                            new LootEntry("Potion_Mana_Small",       10, 0.95, 1, 2),
                            new LootEntry("Potion_Mana",              7, 0.85, 1, 1),
                            new LootEntry("Potion_Mana_Large",         3, 0.55, 1, 1),
                            new LootEntry("Potion_Regen_Mana_Small",   5, 0.70, 1, 1),
                            new LootEntry("Potion_Regen_Mana",         4, 0.65, 1, 1),
                            new LootEntry("Potion_Regen_Mana_Large",   2, 0.45, 1, 1),
                    }
            ),

            // ----------------------------
            // POTIONS: STAMINA
            // ----------------------------
            new LootPool(
                    "Potions_Stamina",
                    1,
                    2,
                    0.55,
                    true,
                    new LootEntry[] {
                            new LootEntry("Potion_Stamina_Lesser",      10, 0.95, 1, 2),
                            new LootEntry("Potion_Stamina_Small",        8, 0.90, 1, 1),
                            new LootEntry("Potion_Stamina",              6, 0.80, 1, 1),
                            new LootEntry("Potion_Stamina_Large",         3, 0.55, 1, 1),
                            new LootEntry("Potion_Stamina_Greater",       2, 0.45, 1, 1),
                            new LootEntry("Potion_Regen_Stamina_Small",   4, 0.60, 1, 1),
                            new LootEntry("Potion_Regen_Stamina",         3, 0.55, 1, 1),
                            new LootEntry("Potion_Regen_Stamina_Large",   2, 0.45, 1, 1),
                    }
            ),

            // ----------------------------
            // POTIONS: UTILITY / DEBUFF / MORPH
            // ----------------------------
            new LootPool(
                    "Potions_Utility",
                    1,
                    2,
                    0.40,
                    true,
                    new LootEntry[] {
                            new LootEntry("Potion_Antidote",       8, 0.85, 1, 1),
                            new LootEntry("Potion_Poison_Minor",   8, 0.85, 1, 1),
                            new LootEntry("Potion_Poison",         5, 0.70, 1, 1),
                            new LootEntry("Potion_Poison_Large",   2, 0.45, 1, 1),
                            new LootEntry("Potion_Morph_Mouse",    2, 0.35, 1, 1),
                            new LootEntry("Potion_Morph_Pigeon",   2, 0.35, 1, 1),
                            new LootEntry("Potion_Morph_Dog",      2, 0.35, 1, 1),
                            new LootEntry("Potion_Morph_Frog",     2, 0.35, 1, 1),
                            new LootEntry("Weapon_Bomb_Potion_Poison", 1, 0.25, 1, 1),
                    }
            ),

            // ----------------------------
            // WEAPONS: MELEE (rare, no duplicates)
            // ----------------------------
            new LootPool(
                    "Weapons_Melee",
                    1,
                    1,
                    0.18,
                    false,
                    new LootEntry[] {
                            new LootEntry("Weapon_Sword_Bone",               8, 0.80, 1, 1),
                            new LootEntry("Weapon_Sword_Bronze",             8, 0.80, 1, 1),
                            new LootEntry("Weapon_Sword_Steel",              6, 0.70, 1, 1),
                            new LootEntry("Weapon_Sword_Steel_Rusty",        7, 0.75, 1, 1),
                            new LootEntry("Weapon_Sword_Scrap",              8, 0.80, 1, 1),
                            new LootEntry("Weapon_Sword_Runic",              3, 0.45, 1, 1),
                            new LootEntry("Weapon_Sword_Nexus",              2, 0.35, 1, 1),
                            new LootEntry("Weapon_Sword_Onyxium",            2, 0.35, 1, 1),
                            new LootEntry("Weapon_Sword_Frost",              3, 0.45, 1, 1),
                            new LootEntry("Weapon_Sword_Doomed",             2, 0.35, 1, 1),
                            new LootEntry("Weapon_Sword_Silversteel",        1, 0.25, 1, 1),
                            new LootEntry("Weapon_Longsword_Katana",         2, 0.35, 1, 1),
                            new LootEntry("Weapon_Longsword_Void",           1, 0.25, 1, 1),
                            new LootEntry("Weapon_Longsword_Mithril",        2, 0.35, 1, 1),
                            new LootEntry("Weapon_Longsword_Onyxium",        1, 0.25, 1, 1),
                            new LootEntry("Weapon_Longsword_Spectral",       1, 0.25, 1, 1),
                            new LootEntry("Weapon_Spear_Crude",              9, 0.85, 1, 1),
                            new LootEntry("Weapon_Spear_Iron",               7, 0.75, 1, 1),
                            new LootEntry("Weapon_Spear_Cobalt",             5, 0.65, 1, 1),
                            new LootEntry("Weapon_Spear_Thorium",            3, 0.45, 1, 1),
                            new LootEntry("Weapon_Spear_Onyxium",            2, 0.35, 1, 1),
                            new LootEntry("Weapon_Spear_Adamantite",         2, 0.35, 1, 1),
                            new LootEntry("Weapon_Mace_Scrap",               8, 0.80, 1, 1),
                            new LootEntry("Weapon_Mace_Onyxium",             2, 0.35, 1, 1),
                            new LootEntry("Weapon_Battleaxe_Tribal",         6, 0.70, 1, 1),
                            new LootEntry("Weapon_Battleaxe_Onyxium",        2, 0.35, 1, 1),
                    }
            ),

            // ----------------------------
            // WEAPONS: RANGED (rare)
            // ----------------------------
            new LootPool(
                    "Weapons_Ranged",
                    1,
                    1,
                    0.16,
                    false,
                    new LootEntry[] {
                            new LootEntry("Weapon_Shortbow_Bronze",      8, 0.80, 1, 1),
                            new LootEntry("Weapon_Shortbow_Combat",      6, 0.70, 1, 1),
                            new LootEntry("Weapon_Shortbow_Frost",       4, 0.60, 1, 1),
                            new LootEntry("Weapon_Shortbow_Flame",       4, 0.60, 1, 1),
                            new LootEntry("Weapon_Shortbow_Doomed",      2, 0.35, 1, 1),
                            new LootEntry("Weapon_Shortbow_Vampire",     1, 0.25, 1, 1),
                            new LootEntry("Weapon_Blowgun_Tribal",       6, 0.70, 1, 1),
                            new LootEntry("Weapon_Gun_Blunderbuss",      2, 0.35, 1, 1),
                            new LootEntry("Weapon_Gun_Blunderbuss_Rusty",3, 0.45, 1, 1),
                            new LootEntry("Weapon_Handgun",             2, 0.35, 1, 1),
                            new LootEntry("Weapon_Assault_Rifle",       1, 0.25, 1, 1),
                            new LootEntry("Weapon_Arrow_Clearshot",      8, 0.80, 2, 6),
                            new LootEntry("Weapon_Arrow_Deadeye",        5, 0.65, 2, 6),
                            new LootEntry("Weapon_Arrow_Trueshot",       3, 0.55, 1, 4),
                    }
            ),

            // ----------------------------
            // WEAPONS: MAGIC (rare)
            // ----------------------------
            new LootPool(
                    "Weapons_Magic",
                    1,
                    1,
                    0.14,
                    false,
                    new LootEntry[] {
                            new LootEntry("Weapon_Wand_Wood",           9, 0.85, 1, 1),
                            new LootEntry("Weapon_Wand_Wood_Rotten",    9, 0.85, 1, 1),
                            new LootEntry("Weapon_Wand_Root",           6, 0.70, 1, 1),
                            new LootEntry("Weapon_Wand_Tribal",         5, 0.65, 1, 1),
                            new LootEntry("Weapon_Wand_Stoneskin",      3, 0.55, 1, 1),
                            new LootEntry("Weapon_Staff_Wood",          9, 0.85, 1, 1),
                            new LootEntry("Weapon_Staff_Wood_Rotten",   9, 0.85, 1, 1),
                            new LootEntry("Weapon_Staff_Bone",          6, 0.70, 1, 1),
                            new LootEntry("Weapon_Staff_Frost",         4, 0.60, 1, 1),
                            new LootEntry("Weapon_Staff_Thorium",       2, 0.35, 1, 1),
                            new LootEntry("Weapon_Staff_Mithril",       2, 0.35, 1, 1),
                            new LootEntry("Weapon_Staff_Onyxium",       1, 0.25, 1, 1),
                            new LootEntry("Weapon_Spellbook_Fire",      4, 0.60, 1, 1),
                            new LootEntry("Weapon_Spellbook_Frost",     4, 0.60, 1, 1),
                            new LootEntry("Weapon_Spellbook_Demon",     2, 0.35, 1, 1),
                            new LootEntry("Weapon_Spellbook_Grimoire_Purple", 1, 0.25, 1, 1),
                            new LootEntry("Weapon_Spellbook_Grimoire_Brown",  1, 0.25, 1, 1),
                    }
            ),

            // ----------------------------
            // WEAPONS: EXPLOSIVES / DEPLOYABLES (very rare)
            // ----------------------------
            new LootPool(
                    "Weapons_Explosives",
                    1,
                    1,
                    0.10,
                    false,
                    new LootEntry[] {
                            new LootEntry("Weapon_Bomb",              6, 0.70, 1, 1),
                            new LootEntry("Weapon_Bomb_Fire",         4, 0.60, 1, 1),
                            new LootEntry("Weapon_Bomb_Large_Fire",   2, 0.35, 1, 1),
                            new LootEntry("Weapon_Bomb_Stun",         3, 0.45, 1, 1),
                            new LootEntry("Weapon_Bomb_Continuous",   1, 0.25, 1, 1),
                            new LootEntry("Weapon_Grenade_Frag",      3, 0.45, 1, 1),
                            new LootEntry("Weapon_Deployable_Turret", 1, 0.20, 1, 1),
                    }
            ),

            // ----------------------------
            // TOOLS (uncommon)
            // ----------------------------
            new LootPool(
                    "Tools",
                    1,
                    1,
                    0.28,
                    false,
                    new LootEntry[] {
                            new LootEntry("Tool_Pickaxe_Wood",     8, 0.80, 1, 1),
                            new LootEntry("Tool_Pickaxe_Onyxium",  1, 0.25, 1, 1),
                            new LootEntry("Tool_Shovel_Crude",     8, 0.80, 1, 1),
                            new LootEntry("Tool_Shovel_Copper",    6, 0.70, 1, 1),
                            new LootEntry("Tool_Shovel_Cobalt",    3, 0.45, 1, 1),
                            new LootEntry("Tool_Shovel_Thorium",   2, 0.35, 1, 1),
                            new LootEntry("Tool_Hatchet_Wood",     8, 0.80, 1, 1),
                            new LootEntry("Tool_Hatchet_Onyxium",  1, 0.25, 1, 1),
                            new LootEntry("Tool_Bark_Scraper",     7, 0.75, 1, 1),
                            new LootEntry("Tool_Sickle_Steel_Rusty",3, 0.45, 1, 1),
                    }
            ),

            // ----------------------------
            // ARMOR (very rare, no duplicates)
            // ----------------------------
            new LootPool(
                    "Armor",
                    1,
                    1,
                    0.08,
                    false,
                    new LootEntry[] {

                            // ----------------------------
                            // BASIC / COMMON ARMOR (higher weights)
                            // ----------------------------

                            new LootEntry("Armor_Wood_Head",   10, 0.85, 1, 1),
                            new LootEntry("Armor_Wood_Chest",  10, 0.85, 1, 1),
                            new LootEntry("Armor_Wood_Hands",  10, 0.85, 1, 1),
                            new LootEntry("Armor_Wood_Legs",   10, 0.85, 1, 1),

                            new LootEntry("Armor_Cloth_Cotton_Head",   10, 0.85, 1, 1),
                            new LootEntry("Armor_Cloth_Cotton_Chest",  10, 0.85, 1, 1),
                            new LootEntry("Armor_Cloth_Cotton_Hands",  10, 0.85, 1, 1),
                            new LootEntry("Armor_Cloth_Cotton_Legs",   10, 0.85, 1, 1),

                            new LootEntry("Armor_Cloth_Linen_Head",     9, 0.80, 1, 1),
                            new LootEntry("Armor_Cloth_Linen_Chest",    9, 0.80, 1, 1),
                            new LootEntry("Armor_Cloth_Linen_Hands",    9, 0.80, 1, 1),
                            new LootEntry("Armor_Cloth_Linen_Legs",     9, 0.80, 1, 1),

                            new LootEntry("Armor_Cloth_Wool_Head",      9, 0.80, 1, 1),
                            new LootEntry("Armor_Cloth_Wool_Chest",     9, 0.80, 1, 1),
                            new LootEntry("Armor_Cloth_Wool_Hands",     9, 0.80, 1, 1),
                            new LootEntry("Armor_Cloth_Wool_Legs",      9, 0.80, 1, 1),

                            new LootEntry("Armor_Cloth_Silk_Head",      7, 0.70, 1, 1),
                            new LootEntry("Armor_Cloth_Silk_Chest",     7, 0.70, 1, 1),
                            new LootEntry("Armor_Cloth_Silk_Hands",     7, 0.70, 1, 1),
                            new LootEntry("Armor_Cloth_Silk_Legs",      7, 0.70, 1, 1),

                            new LootEntry("Armor_Cloth_Cindercloth_Head",   6, 0.65, 1, 1),
                            new LootEntry("Armor_Cloth_Cindercloth_Chest",  6, 0.65, 1, 1),
                            new LootEntry("Armor_Cloth_Cindercloth_Hands",  6, 0.65, 1, 1),
                            new LootEntry("Armor_Cloth_Cindercloth_Legs",   6, 0.65, 1, 1),

                            new LootEntry("Armor_Leather_Medium_Head",   9, 0.80, 1, 1),
                            new LootEntry("Armor_Leather_Medium_Chest",  9, 0.80, 1, 1),
                            new LootEntry("Armor_Leather_Medium_Hands",  9, 0.80, 1, 1),
                            new LootEntry("Armor_Leather_Medium_Legs",   9, 0.80, 1, 1),

                            new LootEntry("Armor_Leather_Heavy_Head",    7, 0.70, 1, 1),
                            new LootEntry("Armor_Leather_Heavy_Chest",   7, 0.70, 1, 1),
                            new LootEntry("Armor_Leather_Heavy_Hands",   7, 0.70, 1, 1),
                            new LootEntry("Armor_Leather_Heavy_Legs",    7, 0.70, 1, 1),

                            new LootEntry("Armor_Diving_Crude_Head",     6, 0.65, 1, 1),
                            new LootEntry("Armor_Diving_Crude_Chest",    6, 0.65, 1, 1),
                            new LootEntry("Armor_Diving_Crude_Hands",    6, 0.65, 1, 1),
                            new LootEntry("Armor_Diving_Crude_Legs",     6, 0.65, 1, 1),

                            new LootEntry("Armor_Bronze_Head",              6, 0.70, 1, 1),
                            new LootEntry("Armor_Bronze_Chest",             6, 0.70, 1, 1),
                            new LootEntry("Armor_Bronze_Hands",             6, 0.70, 1, 1),
                            new LootEntry("Armor_Bronze_Legs",              6, 0.70, 1, 1),

                            new LootEntry("Armor_Steel_Head",               4, 0.60, 1, 1),
                            new LootEntry("Armor_Steel_Chest",              4, 0.60, 1, 1),
                            new LootEntry("Armor_Steel_Hands",              4, 0.60, 1, 1),
                            new LootEntry("Armor_Steel_Legs",               4, 0.60, 1, 1),

                            // ----------------------------
                            // SPECIAL / RARER SETS
                            // ----------------------------

                            new LootEntry("Armor_Bronze_Ornate_Head",        3, 0.50, 1, 1),
                            new LootEntry("Armor_Bronze_Ornate_Chest",       3, 0.50, 1, 1),
                            new LootEntry("Armor_Bronze_Ornate_Hands",       3, 0.50, 1, 1),
                            new LootEntry("Armor_Bronze_Ornate_Legs",        3, 0.50, 1, 1),

                            new LootEntry("Armor_Steel_Ancient_Head",        2, 0.40, 1, 1),
                            new LootEntry("Armor_Steel_Ancient_Chest",       2, 0.40, 1, 1),
                            new LootEntry("Armor_Steel_Ancient_Hands",       2, 0.40, 1, 1),
                            new LootEntry("Armor_Steel_Ancient_Legs",        2, 0.40, 1, 1),

                            new LootEntry("Armor_Trooper_Head",              2, 0.40, 1, 1),
                            new LootEntry("Armor_Trooper_Chest",             2, 0.40, 1, 1),
                            new LootEntry("Armor_Trooper_Hands",             2, 0.40, 1, 1),
                            new LootEntry("Armor_Trooper_Legs",              2, 0.40, 1, 1),

                            new LootEntry("Armor_Trork_Head",                2, 0.40, 1, 1),
                            new LootEntry("Armor_Trork_Chest",               2, 0.40, 1, 1),
                            new LootEntry("Armor_Trork_Hands",               2, 0.40, 1, 1),
                            new LootEntry("Armor_Trork_Legs",                2, 0.40, 1, 1),

                            new LootEntry("Armor_Leather_Raven_Head",         2, 0.40, 1, 1),
                            new LootEntry("Armor_Leather_Raven_Chest",        2, 0.40, 1, 1),
                            new LootEntry("Armor_Leather_Raven_Hands",        2, 0.40, 1, 1),
                            new LootEntry("Armor_Leather_Raven_Legs",         2, 0.40, 1, 1),

                            new LootEntry("Armor_Kweebec_Head",               2, 0.40, 1, 1),
                            new LootEntry("Armor_Kweebec_Chest",              2, 0.40, 1, 1),
                            new LootEntry("Armor_Kweebec_Hands",              2, 0.40, 1, 1),
                            new LootEntry("Armor_Kweebec_Legs",               2, 0.40, 1, 1),

                            // ----------------------------
                            // HIGH TIER (your original tiers)
                            // ----------------------------
                            new LootEntry("Armor_Onyxium_Head",             2, 0.35, 1, 1),
                            new LootEntry("Armor_Onyxium_Chest",            2, 0.35, 1, 1),
                            new LootEntry("Armor_Onyxium_Hands",            2, 0.35, 1, 1),
                            new LootEntry("Armor_Onyxium_Legs",             2, 0.35, 1, 1),

                            new LootEntry("Armor_Prisma_Head",              1, 0.25, 1, 1),
                            new LootEntry("Armor_Prisma_Chest",             1, 0.25, 1, 1),
                            new LootEntry("Armor_Prisma_Hands",             1, 0.25, 1, 1),
                            new LootEntry("Armor_Prisma_Legs",              1, 0.25, 1, 1),
                    }
            ),

            // ----------------------------
            // SPECIAL (ultra rare)
            // ----------------------------
            new LootPool(
                    "Special",
                    1,
                    1,
                    0.03,
                    false,
                    new LootEntry[] {
                            new LootEntry("Glider",                 2, 0.35, 1, 1),
                            new LootEntry("PortalKey_Windsurf_Valley", 1, 0.20, 1, 1),
                            new LootEntry("PortalKey_Henges",         1, 0.20, 1, 1),
                            new LootEntry("PortalKey_Hederas_Lair",   1, 0.20, 1, 1),
                            new LootEntry("PortalKey_Jungles",        1, 0.20, 1, 1),
                            new LootEntry("PortalKey_Taiga",          1, 0.20, 1, 1),
                    }
            ),
    };

    // Caches
    private transient Map<String, LootPool> CachedPoolsById;

    public SofConfig() {}

    public boolean isEnabled() { return Enabled; }

    public List<LootPool> resolvePoolsForTarget(String blockId) {
        if (blockId == null || blockId.isBlank()) return Collections.emptyList();

        TargetPool[] rules = TargetPools;
        if (rules == null || rules.length == 0) return Collections.emptyList();

        Map<String, LootPool> poolsById = getPoolsById();
        if (poolsById.isEmpty()) return Collections.emptyList();

        List<LootPool> accumulated = new ArrayList<>();

        for (TargetPool rule : rules) {
            if (rule == null) continue;
            if (!ruleTargetsContain(rule, blockId)) continue;

            String mode = normalizeMode(rule.getMode());

            List<LootPool> resolvedForRule = resolvePoolsFromIds(rule.getLootPoolIds(), poolsById);
            if (resolvedForRule.isEmpty()) {
                continue;
            }

            if ("PickOne".equals(mode)) {
                LootPool picked = pickOne(resolvedForRule);
                return picked != null ? Collections.singletonList(picked) : Collections.emptyList();
            }

            accumulated.addAll(resolvedForRule);
        }

        if (accumulated.isEmpty()) return Collections.emptyList();
        return dedupeById(accumulated);
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

    private static boolean ruleTargetsContain(TargetPool rule, String blockId) {
        String[] targets = rule.getTargets();
        if (targets == null) return false;
        for (String t : targets) {
            if (blockId.equals(t)) return true;
        }
        return false;
    }

    private static List<LootPool> resolvePoolsFromIds(String[] ids, Map<String, LootPool> poolsById) {
        if (ids == null || ids.length == 0) return Collections.emptyList();
        List<LootPool> out = new ArrayList<>();
        for (String pid : ids) {
            if (pid == null || pid.isBlank()) continue;
            LootPool p = poolsById.get(pid);
            if (p != null) out.add(p);
        }
        return out;
    }

    private static String normalizeMode(String mode) {
        if (mode == null) return "All";
        String m = mode.trim();
        if (m.equalsIgnoreCase("pickone")) return "PickOne";
        return "All";
    }

    private static LootPool pickOne(List<LootPool> pools) {
        if (pools == null || pools.isEmpty()) return null;
        int idx = ThreadLocalRandom.current().nextInt(pools.size());
        return pools.get(idx);
    }

    private static List<LootPool> dedupeById(List<LootPool> pools) {
        if (pools == null || pools.isEmpty()) return Collections.emptyList();
        LinkedHashMap<String, LootPool> map = new LinkedHashMap<>();
        for (LootPool p : pools) {
            if (p == null) continue;
            String id = p.getId();
            if (id == null || id.isBlank()) continue;
            map.putIfAbsent(id, p);
        }
        return new ArrayList<>(map.values());
    }
}
