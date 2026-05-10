package mod.emt.wildcaves4.gen;

import mod.emt.wildcaves4.config.WCConfig;
import mod.emt.wildcaves4.gen.biome.*;
import mod.emt.wildcaves4.util.WCUtils;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WCWorldGen {
    private static final WCGenJungle JUNGLE_GEN = new WCGenJungle();
    private static final WCGenHumid HUMID_GEN = new WCGenHumid();
    private static final WCGenArid ARID_GEN = new WCGenArid();
    private static final WCGenNormal NORMAL_GEN = new WCGenNormal();
    private static final WCGenFrozen FROZEN_GEN = new WCGenFrozen();

    public static boolean isWhiteListed(Block block) {
        List<Block> whitelist = Arrays.stream(WCConfig.WORLD_GEN.blockWhitelist).map(s -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation(s.trim()))).filter(b -> b != null).collect(Collectors.toList());
        return whitelist.contains(block);
    }

    @SubscribeEvent
    public void decorate(DecorateBiomeEvent.Post decorationEvent) {
        generate(decorationEvent.getRand(), decorationEvent.getPos(), decorationEvent.getWorld());
    }

    public void generate(Random random, BlockPos pos, World world) {
        List<Integer> dimensionBlacklist = Arrays.stream(WCConfig.WORLD_GEN.dimensionBlacklist).boxed().collect(Collectors.toList());
        if (dimensionBlacklist.contains(world.provider.getDimension())) {
            return;
        }

        Block frozenBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(WCConfig.WORLD_GEN.frozenCaveBlock.trim()));
        if (frozenBlock != null) {
            WCUtils.frozen = frozenBlock;
        }

        final int chunkX = pos.getX();
        final int chunkZ = pos.getZ();
        for (int attempt = 0; attempt < WCConfig.WORLD_GEN.timesPerChunk; attempt++) {
            int x = chunkX + random.nextInt(16) + 8;
            int z = chunkZ + random.nextInt(16) + 8;
            BlockPos surface = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
            if (surface.getY() <= 10) {
                continue;
            }
            int startY = Math.min(surface.getY() - 1, WCConfig.WORLD_GEN.maxGenHeight);
            BlockPos coord = new BlockPos(x, startY, z);
            while (coord.getY() > 10) {
                if (isWhiteListed(world.getBlockState(coord.up()).getBlock()) && world.isAirBlock(coord)) {
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
