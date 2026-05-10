package mod.emt.wildcaves4.block;

import mod.emt.wildcaves4.init.WCBlocks;
import mod.emt.wildcaves4.util.WCUtils;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class WCBlockIcicle extends Block {
    private PropertyInteger ALL_TYPE;

    public WCBlockIcicle() {
        super(Material.PACKED_ICE);
        this.setResistance(0.6F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ALL_TYPE, 0));
        this.setSoundType(SoundType.GLASS);
    }

    public int getNumOfStructures() {
        return WCBlocks.ICICLES.size();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        if (ALL_TYPE == null) {
            ALL_TYPE = PropertyInteger.create("type", 0, getNumOfStructures() - 1);
        }
        return new BlockStateContainer(this, ALL_TYPE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(ALL_TYPE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(ALL_TYPE, meta);
    }

    public boolean canBlockStay(World world, BlockPos pos) {
        return world.getBlockState(pos.up()).isNormalCube() || world.getBlockState(pos.up()).getMapColor(world, pos) == MapColor.ICE;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return canBlockStay(world, pos) && super.canPlaceBlockAt(world, pos);
    }

    @Override
    protected boolean canSilkHarvest() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs par2CreativeTabs, NonNullList<ItemStack> par3List) {
        for (int i = 0; i < getNumOfStructures(); ++i) {
            par3List.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public Item getItemDropped(IBlockState metadata, Random random, int par3) {
        return Item.getItemFromBlock(Blocks.PACKED_ICE);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!this.canBlockStay(world, pos)) {
            world.setBlockToAir(pos);
        }
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean causesSuffocation(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess par1IBlockAccess, BlockPos pos) {
        return WCUtils.getBox(getMetaFromState(state));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isTranslucent(IBlockState state) {
        return true;
    }

    @Override
    public boolean isPassable(IBlockAccess access, BlockPos pos) {
        return true;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
    }
}
