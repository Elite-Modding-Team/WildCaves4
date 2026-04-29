package mod.emt.wildcaves4.config;

import mod.emt.wildcaves4.WildCaves;
import net.minecraftforge.common.config.Config;

@Config(modid = WildCaves.ID, name = WildCaves.NAME)
public class WCConfig {
    @Config.LangKey("config.wildcaves.general")
    @Config.Comment("Settings for general mechanics")
    public static final General GENERAL = new General();

    @Config.LangKey("config.wildcaves.worldgen")
    @Config.Comment("Settings for world generation")
    public static final WorldGen WORLD_GEN = new WorldGen();

    public static class General {
        @Config.Name("Flora Light Level")
        @Config.Comment("The amount of light emitted by glowing mushrooms")
        @Config.RangeInt(min = 0, max = 15)
        @Config.RequiresMcRestart
        public int floraLightLevel = 5;

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
    }
}
