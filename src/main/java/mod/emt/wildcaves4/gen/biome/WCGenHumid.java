package mod.emt.wildcaves4.gen.biome;

import mod.emt.wildcaves4.config.WCConfig;
import mod.emt.wildcaves4.gen.structure.WCDecorationHelper;
import mod.emt.wildcaves4.gen.structure.WCGenStalactiteStone;
import mod.emt.wildcaves4.util.WCUtils;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WCGenHumid extends WorldGenerator {
    public WCGenHumid() {
    }

    @Override
    public boolean generate(World world, Random random, BlockPos pos) {
        switch (WCUtils.weightedChoice(WCConfig.WORLD_GEN.probabilityGlowcapsHumid, WCConfig.WORLD_GEN.probabilityWet, WCConfig.WORLD_GEN.probabilityVines, WCConfig.WORLD_GEN.probabilitySpiderWeb, WCConfig.WORLD_GEN.probabilitySkulls, WCConfig.WORLD_GEN.probabilityStalactite)) {
            case 1:
                WCDecorationHelper.generateGlowcaps(world, random, pos);
                return true;
            case 2:
                WCDecorationHelper.generateFloodedCaves(world, random, pos);
                return true;
            case 3:
                WCDecorationHelper.generateVines(world, random, pos);
                return true;
            case 4:
                world.setBlockState(pos.down(WCUtils.getNumEmptyBlocks(world, pos) - 1), Blocks.WEB.getDefaultState(), 2);
                return true;
            case 5:
                WCDecorationHelper.generateSkulls(world, random, pos, WCUtils.getNumEmptyBlocks(world, pos));
                return true;
            default:
                new WCGenStalactiteStone().generate(world, random, pos, WCUtils.getNumEmptyBlocks(world, pos), WCConfig.WORLD_GEN.maxLength);
                return true;
        }
    }
}
