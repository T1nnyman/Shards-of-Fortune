package com.tinnyman.shardsoffortune;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;
import com.tinnyman.shardsoffortune.config.SofConfig;
import com.tinnyman.shardsoffortune.services.LootService;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

public class ShardsOfFortunePlugin extends JavaPlugin {
    private final Config<SofConfig> config;

    public ShardsOfFortunePlugin(@Nonnull JavaPluginInit init) {
        super(init);
        this.config = this.withConfig("ShardsOfFortune", SofConfig.CODEC);
    }

    @Override
    protected void setup() {
        this.config.load().join();

        Path configPath = getConfigPath(this.config);
        boolean existed = configPath != null && Files.exists(configPath);

        if (!existed) {
            this.config.save().join();
            System.out.println("[ShardsOfFortune] Wrote default config: " + (configPath != null ? configPath : "(unknown path)"));
        } else {
            System.out.println("[ShardsOfFortune] Config already exists, not overwriting: " + configPath);
        }

        SofConfig cfg = this.config.get();

        // Register ECS system
        this.getEntityStoreRegistry().registerSystem(new PotBreakSystem(cfg, new LootService()));
        System.out.println("[ShardsOfFortune] setup complete!");
    }

    private static Path getConfigPath(Config<SofConfig> cfg) {
        try {
            Field f = Config.class.getDeclaredField("path");
            f.setAccessible(true);
            return (Path) f.get(cfg);
        } catch (Throwable t) {
            System.out.println("[ShardsOfFortune] WARNING: Could not access config path; skipping overwrite protection. " + t);
            return null;
        }
    }
}
