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

public class WCGenNormal extends WorldGenerator {
    public WCGenNormal() {
    }

    @Override
    public boolean generate(World world, Random random, BlockPos pos) {
        switch (WCUtils.weightedChoice(WCConfig.WORLD_GEN.probabilityVines, WCConfig.WORLD_GEN.probabilitySpiderWeb, WCConfig.WORLD_GEN.probabilityStalactite, WCConfig.WORLD_GEN.probabilityGlowcaps, WCConfig.WORLD_GEN.probabilitySkulls, 0)) {
            case 1:
                WCDecorationHelper.generateVines(world, random, pos);
                return true;
            case 2:
                world.setBlockState(pos, Blocks.WEB.getDefaultState(), 2);
                return true;
            case 3:
                new WCGenStalactiteStone().generate(world, random, pos, WCUtils.getNumEmptyBlocks(world, pos), WCConfig.WORLD_GEN.maxLength);
                return true;
            case 4:
                WCDecorationHelper.generateGlowcaps(world, random, pos);
                return true;
            case 5:
                WCDecorationHelper.generateSkulls(world, random, pos, WCUtils.getNumEmptyBlocks(world, pos));
                return true;
        }
        return false;
    }
}
