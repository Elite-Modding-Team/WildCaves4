package mod.emt.wildcaves4.config;

import mod.emt.wildcaves4.WildCaves;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = WildCaves.ID, name = "WildCaves4")
public class WCConfig {
    @Config.LangKey("config.wildcaves.general")
    @Config.Comment("Settings for general mechanics")
    public static final General GENERAL = new General();

    @Config.LangKey("config.wildcaves.worldgen")
    @Config.Comment("Settings for world generation")
    public static final WorldGen WORLD_GEN = new WorldGen();

    public static class General {
        @Config.Name("Glowing Mushroom Light Level")
        @Config.Comment("The amount of light emitted by glowing mushrooms")
        @Config.RangeInt(min = 0, max = 15)
        @Config.RequiresMcRestart
        public int mushroomLightLevel = 5;

        @Config.Name("Solid Stalactites/Stalagmites")
        @Config.Comment("Whether stalactites/stalagmites can be collided with")
        @Config.RequiresMcRestart
        public boolean solidStalactites = false;

        @Config.Name("Stalactites/Stalagmite Damage")
        @Config.Comment("Makes stalactites/stalagmites damage entities when fallen on")
        @Config.RequiresMcRestart
        public boolean stalactiteDamage = false;
    }

    public static class WorldGen {
        @Config.Name("Bone Pile Chance")
        @Config.Comment("The Chance for Bone Pile blocks to generate")
        @Config.RequiresMcRestart
        public int fossilChance = 5;

        @Config.Name("Skull Chest Chance")
        @Config.Comment("The chance for a skull to be found in dungeon chests")
        @Config.RequiresMcRestart
        public int skullChestChance = 50;

        @Config.Name("Dimension Blacklist")
        @Config.Comment("Worlds where generation won't occur (by dimension IDs)")
        @Config.RequiresMcRestart
        public int[] dimensionBlacklist = {-1, 1};

        @Config.Name("Block Whitelist")
        @Config.Comment("Blocks that stalactites/stalagmites and other structures can hang from or generate on")
        @Config.RequiresMcRestart
        public String[] blockWhitelist = {
                "stone",
                "cobblestone",
                "sandstone",
                "coal_ore",
                "iron_ore",
                "gold_ore",
                "diamond_ore",
                "lapis_ore",
                "redstone_ore",
                "lit_redstone_ore",
                "emerald_ore",
                "ice",
                "snow",
                "monster_egg"
        };

        @Config.Name("Frozen Cave Block")
        @Config.Comment("The block to generate in frozen caves")
        @Config.RequiresMcRestart
        public String frozenCaveBlock = "packed_ice";

        @Config.Name("Probability of Vines in Jungle Caves")
        @Config.Comment("Chance for vines to generate in jungle caves")
        @Config.RangeDouble(min = 0.0, max = 1.0)
        @Config.RequiresMcRestart
        public double probabilityVinesJungle = 0.5;

        @Config.Name("Probability of Icicles in Frozen Caves")
        @Config.Comment("Chance for icicles to generate in frozen caves")
        @Config.RangeDouble(min = 0.0, max = 1.0)
        @Config.RequiresMcRestart
        public double probabilityIcicle = 0.6;

        @Config.Name("Probability of Water Fountains in Wet Caves")
        @Config.Comment("Chance for more water fountains to generate in wet caves")
        @Config.RangeDouble(min = 0.0, max = 1.0)
        @Config.RequiresMcRestart
        public double probabilityWet = 0.1;

        @Config.Name("Probability of Less Generation in Arid Caves")
        @Config.Comment("Chance to reduce generation in arid caves")
        @Config.RangeDouble(min = 0.0, max = 1.0)
        @Config.RequiresMcRestart
        public double probabilityDry = 0.5;

        @Config.Name("Probability of Glowing Mushrooms in Humid/Jungle Caves")
        @Config.Comment("Chance for glowing mushrooms to generate in humid and jungle caves")
        @Config.RangeDouble(min = 0.0, max = 1.0)
        @Config.RequiresMcRestart
        public double probabilityGlowcapsHumid = 0.3;

        @Config.Name("Probability of Ice Mushrooms in Frozen Caves")
        @Config.Comment("Chance for glowing ice mushrooms to generate in frozen caves")
        @Config.RangeDouble(min = 0.0, max = 1.0)
        @Config.RequiresMcRestart
        public double probabilityIceshrooms = 0.3;

        @Config.Name("Probability of Sandstone Stalactites in Arid Caves")
        @Config.Comment("Chance for sandstone stalactites to generate in arid caves")
        @Config.RangeDouble(min = 0.0, max = 1.0)
        @Config.RequiresMcRestart
        public double probabilitySandStalactites = 0.5;

        @Config.Name("Probability of Vines in Caves")
        @Config.Comment("Chance for vines to generate in caves")
        @Config.RangeDouble(min = 0.0, max = 1.0)
        @Config.RequiresMcRestart
        public double probabilityVines = 0.1;

        @Config.Name("Probability of Glowing Mushrooms in Caves")
        @Config.Comment("Chance for glowing mushrooms to generate in caves")
        @Config.RangeDouble(min = 0.0, max = 1.0)
        @Config.RequiresMcRestart
        public double probabilityGlowcaps = 0.1;

        @Config.Name("Probability of Stalactites/Stalagmites")
        @Config.Comment("Chance for stalactites and stalagmites to generate")
        @Config.RangeDouble(min = 0.0, max = 1.0)
        @Config.RequiresMcRestart
        public double probabilityStalactite = 0.5;

        @Config.Name("Probability of Spider Webs")
        @Config.Comment("Chance for spider webs to generate in caves")
        @Config.RangeDouble(min = 0.0, max = 1.0)
        @Config.RequiresMcRestart
        public double probabilitySpiderWeb = 0.15;

        @Config.Name("Probability of Skulls")
        @Config.Comment("Chance for skulls to generate in caves")
        @Config.RangeDouble(min = 0.0, max = 1.0)
        @Config.RequiresMcRestart
        public double probabilitySkulls = 0.0001;

        @Config.Name("Times to Attempt Generating Per Chunk")
        @Config.Comment("How many times generation is attempted per chunk")
        @Config.RangeInt(min = 1, max = 100)
        @Config.RequiresMcRestart
        public int timesPerChunk = 10;

        @Config.Name("Max Height of Structure Generation")
        @Config.Comment("Maximum Y-level at which structures generate")
        @Config.RangeInt(min = 1, max = 255)
        @Config.RequiresMcRestart
        public int maxGenHeight = 63;

        @Config.Name("Max Length of Structure Generation")
        @Config.Comment("Maximum length of generated structures such as stalactites")
        @Config.RangeInt(min = 1, max = 64)
        @Config.RequiresMcRestart
        public int maxLength = 8;
    }

    @Mod.EventBusSubscriber(modid = WildCaves.ID)
    public static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(WildCaves.ID)) {
                ConfigManager.sync(WildCaves.ID, Config.Type.INSTANCE);
            }
        }
    }
}
