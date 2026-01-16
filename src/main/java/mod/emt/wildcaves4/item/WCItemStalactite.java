package mod.emt.wildcaves4.item;

import mod.emt.wildcaves4.init.WCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class WCItemStalactite extends WCMultiItemBlock {
    public WCItemStalactite(Block block, ArrayList<String> names) {
        super(block, names);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float par8, float par9, float par10) {
        ItemStack itemStack = player.getHeldItem(hand);
        IBlockState state = world.getBlockState(pos);
        Block blockId = state.getBlock();
        if (blockId == Blocks.SNOW && (state.getValue(BlockSnow.LAYERS)) < 1) {
            side = EnumFacing.UP;
        } else if (blockId != Blocks.TALLGRASS && blockId != Blocks.DEADBUSH && (blockId == Blocks.AIR || !blockId.isReplaceable(world, pos))) {
            pos = pos.offset(side);
        }
        if (itemStack.getCount() > 0 && player.canPlayerEdit(pos, side, itemStack) && canPlace(itemStack, world, pos)) {
            int j1 = this.getMetadata(itemStack.getMetadata());
            IBlockState k1 = block.getStateForPlacement(world, pos, side, par8, par9, par10, j1, player);
            if (placeBlockAt(itemStack, player, world, pos, side, par8, par9, par10, k1)) {
                world.playSound(player, pos, block.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (block.getSoundType().getVolume() + 1.0F) / 2.0F, block.getSoundType().getPitch() * 0.8F);
                itemStack.shrink(1);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }

    private boolean canPlace(ItemStack itemStack, World world, BlockPos pos) {
        boolean result = false;
        int metadata = getMetadata(itemStack.getMetadata());
        boolean upNormal = world.isBlockNormalCube(pos.up(), false);
        boolean downNormal = world.isBlockNormalCube(pos.down(), false);
        boolean upStalactite = isStalactite(world.getBlockState(pos.up()));
        boolean downStalactite = isStalactite(world.getBlockState(pos.down()));
        if ((metadata == 0 || metadata == 4 || metadata == 5) && (upNormal || downNormal || upStalactite || downStalactite))
            result = true;
        else if ((metadata < 4 || metadata == 7 || metadata == 11) && (upNormal || upStalactite))
            result = true;
        else if ((metadata == 6 || (metadata > 7 && metadata < 11) || metadata == 12) && (downNormal || downStalactite))
            result = true;
        return result;
    }

    private boolean isStalactite(IBlockState state) {
        return state.getBlock() == WCBlocks.stalactite_stone || state.getBlock() == WCBlocks.stalactite_sandstone;
    }
}
