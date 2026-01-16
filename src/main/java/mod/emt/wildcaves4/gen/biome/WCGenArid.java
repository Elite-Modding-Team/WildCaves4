package mod.emt.wildcaves4.gen.biome;

import mod.emt.wildcaves4.gen.WCWorldGen;
import mod.emt.wildcaves4.gen.structure.WCDecorationHelper;
import mod.emt.wildcaves4.gen.structure.WCGenStalactiteSandstone;
import mod.emt.wildcaves4.gen.structure.WCGenStalactiteStone;
import mod.emt.wildcaves4.util.WCUtils;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WCGenArid extends WorldGenerator {
    public WCGenArid() {
    }

    @Override
    public boolean generate(World world, Random random, BlockPos pos) {
        switch (WCUtils.weightedChoice(WCWorldGen.probabilitySandStalactites, WCWorldGen.probabilitySpiderWeb, WCWorldGen.probabilityDry, WCWorldGen.probabilitySkulls, WCWorldGen.probabilityStalactite, 0)) {
            case 1:
                new WCGenStalactiteSandstone().generate(world, random, pos, WCUtils.getNumEmptyBlocks(world, pos), WCWorldGen.maxLength);
                return true;
            case 2:
                world.setBlockState(pos, Blocks.WEB.getDefaultState(), 2);
                return true;
            case 3:
                return false;
            case 4:
                WCDecorationHelper.generateSkulls(world, random, pos, WCUtils.getNumEmptyBlocks(world, pos));
                return true;
            default:
                new WCGenStalactiteStone().generate(world, random, pos, WCUtils.getNumEmptyBlocks(world, pos), WCWorldGen.maxLength);
                return true;
        }
    }
}
