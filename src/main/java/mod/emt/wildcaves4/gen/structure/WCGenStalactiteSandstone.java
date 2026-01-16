package mod.emt.wildcaves4.gen.structure;

import mod.emt.wildcaves4.init.WCBlocks;
import mod.emt.wildcaves4.util.WCUtils;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WCGenStalactiteSandstone extends WCGenStalactiteStone {
    public WCGenStalactiteSandstone() {
        super(WCBlocks.stalactite_sandstone);
    }

    @Override
    protected void generateStalactiteBase(World world, Random random, BlockPos topY) {
        super.generateStalactiteBase(world, random, topY);
        WCUtils.convertToSandType(world, random, topY);
    }

    @Override
    protected void generateStalagmiteBase(World world, Random random, BlockPos botY, int aux) {
        if (world.getBlockState(botY.down()) == Blocks.STONE)
            world.setBlockState(botY.down(), Blocks.SANDSTONE.getDefaultState(), 2);
        super.generateStalagmiteBase(world, random, botY, aux);
        WCUtils.convertToSandType(world, random, botY);
    }
}
