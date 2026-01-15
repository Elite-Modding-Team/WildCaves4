package mod.emt.wildcaves3.gen;

import mod.emt.wildcaves3.gen.biome.*;
import mod.emt.wildcaves3.util.WCUtils;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WCWorldGen {
    public static final List<Integer> DIMENSION_BLACKLIST = new ArrayList<>();
    private static final WCGenJungle JUNGLE_GEN = new WCGenJungle();
    private static final WCGenHumid HUMID_GEN = new WCGenHumid();
    private static final WCGenArid ARID_GEN = new WCGenArid();
    private static final WCGenNormal NORMAL_GEN = new WCGenNormal();
    private static final WCGenFrozen FROZEN_GEN = new WCGenFrozen();
    private static final List<Block> BLOCK_WHITELIST = new ArrayList<>();
    public static float probabilityVinesJungle;
    public static float probabilityVines;
    public static float probabilityIcicle;
    public static float probabilityWet;
    public static float probabilityDry;
    public static float probabilityGlowcapsHumid;
    public static float probabilityGlowcaps;
    public static float probabilityIceshrooms;
    public static float probabilityStalactite;
    public static float probabilitySpiderWeb;
    public static float probabilitySandStalactites;
    public static float probabilitySkulls;
    public static int maxGenHeightGlowcapNormal;
    public static int timesPerChunk;
    public static int maxGenHeight;
    public static int maxLength;

    public WCWorldGen(Configuration config) {
        setConfig(config);
    }

    public static boolean isWhiteListed(Block block) {
        return BLOCK_WHITELIST.contains(block);
    }

    private static void setConfig(Configuration config) {
        // --generation permissions------
        String category = "Permissions";
        boolean sandstoneStalactites = config.get(category, "Generate sandstone stalactites in arid biomes", true).getBoolean(true);
        boolean flora = config.get(category, "Generate flora in caves", true).getBoolean(true);
        boolean stalactites = config.get(category, "Generate stalactites in caves", true).getBoolean(true);
        String[] list = config.get(category, "Dimension Blacklist", "-1,1", "Worlds where generation won't occur (by dimension IDs), use [id1;id2] to add a range of IDs").getString().split(",");
        for (String text : list) {
            if (text != null && !text.isEmpty()) {
                boolean done = false;
                if (text.contains("[") && text.contains("]")) {
                    String[] results = text.substring(text.indexOf("[") + 1, text.indexOf("]")).split(";");
                    if (results.length == 2) {
                        try {
                            int a = Integer.parseInt(results[0]);
                            int b = Integer.parseInt(results[1]);
                            for (int x = a; x <= b; x++) {
                                DIMENSION_BLACKLIST.add(x);
                            }
                            done = true;
                        } catch (Exception ignored) {
                        }
                    }
                }
                if (!done) {
                    try {
                        DIMENSION_BLACKLIST.add(Integer.parseInt(text.trim()));
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        list = config.get(category, "Block Whitelist", "stone,grass,dirt,cobblestone,gravel,gold_ore,iron_ore,coal_ore,lapis_ore,sandstone,diamond_ore,redstone_ore,lit_redstone_ore,ice,snow,clay,monster_egg,emerald_ore").getString().split(",");
        Block block;
        for (String txt : list) {
            try {
                block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(txt.trim()));
                if (block != null) {
                    BLOCK_WHITELIST.add(block);
                }
            } catch (Throwable ignored) {
            }
        }
        // --Biome specific ratios------
        category = "Biome-specific";
        probabilityVinesJungle = (float) config.get(category, "Probability of vines in jungle caves", 0.5).getDouble(0.5);
        probabilityIcicle = (float) config.get(category, "Probability of icicles in frozen caves", 0.6).getDouble(0.6);
        block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(config.get(category, "Block to generate in frozen caves", "ice").getString().trim()));
        if (block != null) {
            WCUtils.frozen = block;
        }
        probabilityWet = (float) config.get(category, "Probability of more water fountains in wet caves", 0.1).getDouble(0.1);
        probabilityDry = (float) config.get(category, "Probability of less generation in arid caves", 0.5).getDouble(0.5);
        probabilityGlowcapsHumid = (float) config.get(category, "Probability of glowing mushrooms in humid/jungle caves", 0.3).getDouble(0.3);
        probabilityIceshrooms = (float) config.get(category, "Probability of glowing ice mushrooms in frozen caves", 0.3).getDouble(0.3);
        probabilitySandStalactites = (float) config.get(category, "Probability of sandstone stalactites in arid caves", 0.5).getDouble(0.5);
        // --General ratios------
        category = "Non-biome-specific";
        probabilityVines = (float) config.get(category, "Probability of vines in caves", 0.1).getDouble(0.1);
        probabilityGlowcaps = (float) config.get(category, "Probability of glowing mushrooms in caves", 0.1).getDouble(0.1);
        probabilityStalactite = (float) config.get(category, "Probability of stalactites/stalagmites", 0.5).getDouble(0.5);
        probabilitySpiderWeb = (float) config.get(category, "Probability of spider webs", 0.15).getDouble(0.15);
        maxGenHeightGlowcapNormal = config.get(category, "Max height at which to generate glowcaps in normal biomes", 30).getInt();
        probabilitySkulls = (float) config.get(category, "Probability of skulls", 0.0001).getDouble(0.0001);
        if (!sandstoneStalactites) {
            probabilitySandStalactites = 0;
        }
        if (!flora) {
            probabilityGlowcaps = 0;
            probabilityVinesJungle = 0;
            probabilityGlowcapsHumid = 0;
            probabilityIceshrooms = 0;
            probabilityVines = 0;
            probabilityGlowcaps = 0;
        }
        if (!stalactites) {
            probabilityStalactite = 0;
            probabilitySandStalactites = 0;
        }
        // --other------
        category = Configuration.CATEGORY_GENERAL;
        timesPerChunk = config.get(category, "Times to attempt generating per chunk", 10).getInt();
        maxGenHeight = config.get(category, "Max height of structure generation", 80).getInt();
        maxLength = config.get(category, "Max length of structure generation", 8).getInt();
        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public void decorate(DecorateBiomeEvent.Post decorationEvent) {
        generate(decorationEvent.getRand(), decorationEvent.getPos(), decorationEvent.getWorld());
    }

    public void generate(Random random, BlockPos pos, World world) {
        if (DIMENSION_BLACKLIST.contains(world.provider.getDimension())) {
            return;
        }
        final int chunkX = pos.getX();
        final int chunkZ = pos.getZ();
        for (int attempt = 0; attempt < timesPerChunk; attempt++) {
            int x = chunkX + random.nextInt(16) + 8;
            int z = chunkZ + random.nextInt(16) + 8;
            BlockPos surface = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
            if (surface.getY() <= 10) {
                continue;
            }
            int startY = Math.min(surface.getY() - 1, maxGenHeight);
            BlockPos coord = new BlockPos(x, startY, z);
            while (coord.getY() > 10) {
                if (BLOCK_WHITELIST.contains(world.getBlockState(coord.up()).getBlock()) && world.isAirBlock(coord)) {
                    break;
                }
                coord = coord.down();
            }
            if (coord.getY() <= 10 || !world.isAirBlock(coord)) {
                continue;
            }
            Biome biome = world.getBiome(coord);
            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.COLD)) {
                FROZEN_GEN.generate(world, random, coord);
            } else if (biome.getTemperature(coord) > 1.5f && biome.getRainfall() < 0.1f) {
                ARID_GEN.generate(world, random, coord);
            } else if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE)) {
                JUNGLE_GEN.generate(world, random, coord);
            } else if (biome.isHighHumidity() || BiomeDictionary.hasType(biome, BiomeDictionary.Type.WATER)) {
                HUMID_GEN.generate(world, random, coord);
            } else {
                NORMAL_GEN.generate(world, random, coord);
            }
        }
    }
}
