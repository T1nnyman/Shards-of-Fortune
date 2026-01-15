# 🏺 Shards of Fortune

**Shards of Fortune** brings classic adventure-style loot to Hytale by making breakable pots, containers and any block actually drop items — fully configurable, flexible, and future-proof.

Instead of overriding vanilla loot or hardcoding drops, this mod introduces a **loot pool system** that lets you decide *what* drops, *where*, and *how often* — all through a simple JSON config.

Think *Zelda-style pots*, but configurable.

---

## ✨ Features

- 🧩 **Loot Pools**  
  Define multiple loot pools with weighted items, roll counts, and drop chances.

- 🎯 **Target-Based Drops**  
  Assign specific blocks (pots, vases, containers, etc.) to specific loot pools.

- ⚙️ **Fully Data-Driven**  
  No hardcoded logic — everything is controlled via config.

- 🔮 **Future-Proof Design**  
  Easily expand with new blocks, loot pools, or items as Hytale evolves.

- 🚫 **Non-Intrusive**  
  Only affects blocks you explicitly target — normal blocks remain untouched.

---

## 📦 How It Works

1. **Loot Pools** define *what* can drop
2. **Target Pools** define *which blocks* use which loot pool
3. When a targeted block is broken, it rolls loot from its assigned pool
4. Blocks not listed in any target pool are ignored

No default loot pool is applied globally — only explicitly targeted blocks are affected.

---

## 🛠 Example Configuration

```json
{
  "Enabled": true,

  "TargetPools": [
    {
      "Name": "Pots",
      "PoolId": "Ores",
      "Targets": [
        "Furniture_Frozen_Castle_Pot",
        "Furniture_Jungle_Pot",
        "Furniture_Ancient_Pot"
      ]
    }
  ],

  "LootPools": [
    {
      "Id": "Ores",
      "RollsMin": 1,
      "RollsMax": 3,
      "OverallDropChance": 1.0,
      "AllowDuplicates": true,
      "Loot": [
        {
          "ItemId": "Ore_Copper",
          "Weight": 10,
          "CountMin": 1,
          "CountMax": 2,
          "RollChance": 1.0
        },
        {
          "ItemId": "Ore_Iron",
          "Weight": 6,
          "CountMin": 1,
          "CountMax": 1,
          "RollChance": 0.8
        }
      ]
    }
  ]
}
```

## 📁 Config Location

The config file is generated automatically on first launch:

mods/Tinnyman_Shards-of-Fortune/ShardsOfFortune.json


Once created, the file **will not be overwritten**, allowing you to safely customize it without losing changes.

---

## 🧠 Design Philosophy

- No surprise global behavior
- No vanilla loot overrides
- Explicit > implicit
- Easy to read, easy to extend

If a block isn’t explicitly listed in a target pool, **it won’t drop anything extra** — simple and predictable.

---

## 🔧 Installation

1. Drop the mod `.jar` into your servers `mods/` folder
2. Start the server once to generate the config
3. Edit the config to your liking
4. Restart the server

---

## 📜 License

MIT — feel free to use, modify, and extend.

---

## ❤️ Credits

Created by **Tinnyman**  
Inspired by classic adventure games and a love for breaking pots.
