package mod.emt.wildcaves4.util;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Random;

public class WCUtils {
    private static final Random WC_RANDOM = new Random();
    private static final IdentityHashMap<Block, Block> SAND_EQUIVALENT = new IdentityHashMap<>(8);
    private static final AxisAlignedBB HIGH_AABB = new AxisAlignedBB(0.25F, 0.5F, 0.25F, 0.75F, 1F, 0.75F);
    private static final AxisAlignedBB DEFAULT_AABB = new AxisAlignedBB(0.25F, 0.0F, 0.25F, 0.75F, 1F, 0.75F);
    public static Block frozen = Blocks.ICE;
    public static List<Block> freezable = Arrays.asList(Blocks.STONE, Blocks.DIRT, Blocks.GRAVEL, Blocks.GRASS);

    static {
        SAND_EQUIVALENT.put(Blocks.STONE, Blocks.SANDSTONE);
        SAND_EQUIVALENT.put(Blocks.DIRT, Blocks.SAND);
        SAND_EQUIVALENT.put(Blocks.GRAVEL, Blocks.SAND);
    }

    // transforms an area into snow and ice
    public static void convertToFrozenType(World world, Random random, BlockPos pos) {
        int height = random.nextInt(5) + 3;
        int length = random.nextInt(5) + 3;
        int width = random.nextInt(5) + 3;
        int newX = pos.getX() - length / 2;
        int newY = pos.getY() + height / 2;
        int newZ = pos.getZ() - width / 2;
        Block aux;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                for (int k = 0; k < width; k++) {
                    // basically transform or not
                    if (weightedChoice(0.8f, 0.2f, 0, 0, 0, 0) == 1) {
                        BlockPos newPos = new BlockPos(newX + j, newY - i, newZ + k);
                        if (newPos.getY() >= world.getSeaLevel()) continue;
                        aux = world.getBlockState(newPos).getBlock();
                        if (freezable.contains(aux))// stone -> ice
                            world.setBlockState(newPos, frozen.getDefaultState(), 2);
                    }
                }
            }
        }
    }

    // transforms an area into sand and sandstone
    public static void convertToSandType(World world, Random random, BlockPos pos) {
        int height = random.nextInt(5) + 3;
        int length = random.nextInt(5) + 3;
        int width = random.nextInt(5) + 3;
        int newX = pos.getX() - length / 2;
        int newY = pos.getY() + height / 2;
        int newZ = pos.getZ() - width / 2;
        Block aux;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                for (int k = 0; k < width; k++) {
                    // basically transform or not
                    if (weightedChoice(0.7f, 0.3f, 0, 0, 0, 0) == 1) {
                        BlockPos newPos = new BlockPos(newX + j, newY - i, newZ + k);
                        if (newPos.getY() >= world.getSeaLevel()) continue;
                        aux = SAND_EQUIVALENT.get(world.getBlockState(newPos).getBlock());
                        if (aux != null)// stone -> sandstone // dirt/gravel -> sand
                            world.setBlockState(newPos, aux.getDefaultState(), 2);
                    }
                }
            }
        }
    }

    // gets the number of empty blocks between the current one and the closest one below
    public static int getNumEmptyBlocks(World world, BlockPos pos) {
        int dist = 0;
        while (pos.getY() > 5 && !world.isBlockNormalCube(pos, true) && world.isAirBlock(pos)) {
            pos = pos.down();
            dist++;
        }
        return dist;
    }

    // chooses one of the given ints at random
    public static int randomChoice(int... val) {
        return val[WC_RANDOM.nextInt(val.length)];
    }

    // returns the order number of the probability that was chosen (1-6)
    // all parameters are probabilities
    // probabilities can be 0
    public static int weightedChoice(double par1, double par2, double par3, double par4, double par5, double par6) {
        double total = par1 + par2 + par3 + par4 + par5 + par6;
        double val = WC_RANDOM.nextDouble();
        double previous;
        par1 = par1 / total;
        par2 = par2 / total;
        par3 = par3 / total;
        par4 = par4 / total;
        par5 = par5 / total;
        //par6 is the remaining probability
        if (val < par1) return 1;
        else previous = par1;
        if (val < par2 + previous) return 2;
        else previous += par2;
        if (val < par3 + previous) return 3;
        else previous += par3;
        if (val < par4 + previous) return 4;
        else previous += par4;
        if (val < par5 + previous) return 5;
        else return 6;
    }

    public static AxisAlignedBB getBox(int state) {
        switch (state) {
            case 1:
                return HIGH_AABB.expand(0, -0.3F, 0);
            case 2:
                return HIGH_AABB;
            case 9:
                return DEFAULT_AABB.setMaxY(0.8F);
            case 10:
                return DEFAULT_AABB.setMaxY(0.4F);
            default:
                return DEFAULT_AABB;
        }
    }
}
