package mod.emt.wildcaves3.gen.biome;

import mod.emt.wildcaves3.gen.WCWorldGen;
import mod.emt.wildcaves3.gen.structure.WCDecorationHelper;
import mod.emt.wildcaves3.gen.structure.WCGenStalactiteStone;
import mod.emt.wildcaves3.util.WCUtils;
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
        switch (WCUtils.weightedChoice(WCWorldGen.probabilityVines, WCWorldGen.probabilitySpiderWeb, WCWorldGen.probabilityStalactite, WCWorldGen.probabilityGlowcaps, WCWorldGen.probabilitySkulls, 0)) {
            case 1:
                WCDecorationHelper.generateVines(world, random, pos);
                return true;
            case 2:
                world.setBlockState(pos, Blocks.WEB.getDefaultState(), 2);
                return true;
            case 3:
                new WCGenStalactiteStone().generate(world, random, pos, WCUtils.getNumEmptyBlocks(world, pos), WCWorldGen.maxLength);
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
