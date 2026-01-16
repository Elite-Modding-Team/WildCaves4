package mod.emt.wildcaves4.block;

import mod.emt.wildcaves4.WildCaves;
import mod.emt.wildcaves4.init.WCBlocks;
import mod.emt.wildcaves4.util.WCUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
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

public class WCBlockStalactiteSandstone extends Block {
    private final Item droppedItem;
    private PropertyInteger ALL_TYPE;

    public WCBlockStalactiteSandstone(Item drop) {
        super(Material.ROCK);
        this.droppedItem = drop;
        this.setHardness(0.8F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ALL_TYPE, 0));
    }

    public int getNumOfStructures() {
        return WCBlocks.SAND_STALACS.size();
    }

    public boolean isUp(IBlockState state) {
        return getMetaFromState(state) < 4;
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

    @Override
    public Item getItemDropped(IBlockState metadata, Random random, int par3) {
        return droppedItem;
    }

    @Override
    public int quantityDropped(Random rand) {
        return rand.nextInt(3) - 1;
    }

    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        boolean result = false;
        int metadata = getMetaFromState(state);
        if ((metadata != 0 && metadata < 4) || metadata == 7 || metadata == 11)
            result = connected(world, pos, true);
        else if (metadata == 6 || (metadata > 7 && metadata < 11) || metadata == 12)
            result = connected(world, pos, false);
        else if (metadata == 0 || metadata == 4 || metadata == 5)
            result = connected(world, pos, true) || connected(world, pos, false);
        return result;
    }

    @Override
    protected boolean canSilkHarvest() {
        return true;
    }

    //aux funtion for canblockStay
    public boolean connected(World world, BlockPos pos, boolean searchUp) {
        int increment;
        int i;
        if (searchUp)
            increment = 1;
        else
            increment = -1;
        i = increment;
        while (world.getBlockState(pos.up(i)).getBlock() == WCBlocks.stalactite_stone || world.getBlockState(pos.up(i)).getBlock() == WCBlocks.stalactite_sandstone)
            i = i + increment;
        return world.getBlockState(pos.up(i)).isNormalCube();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs par2CreativeTabs, NonNullList<ItemStack> par3List) {
        for (int i = 0; i < getNumOfStructures(); ++i) {
            par3List.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
        this.updateTick(world, pos, state, null);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        if (!this.canBlockStay(world, pos, state)) {
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
        }
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode && ((EntityPlayer) entity).capabilities.isFlying) {
            return;
        }
        entity.motionX *= 0.7D;
        entity.motionZ *= 0.7D;
    }

    @Override
    public void onFallenUpon(World world, BlockPos pos, Entity entity, float par6) {
        if (WildCaves.damageWhenFallenOn && entity.isEntityAlive()) {
            entity.attackEntityFrom(DamageSource.GENERIC, 5);
        }
    }

    @Override
    public void onLanded(World world, Entity entity) {
        if (WildCaves.solidStalactites)
            super.onLanded(world, entity);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!world.isRemote && !this.canBlockStay(world, pos, state)) {
            world.destroyBlock(pos, true);
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
    public boolean isPassable(IBlockAccess access, BlockPos pos) {
        return !WildCaves.solidStalactites || super.isPassable(access, pos);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        if (WildCaves.solidStalactites)
            super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
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
}
