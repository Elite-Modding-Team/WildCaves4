package mod.emt.wildcaves3.client;

import mod.emt.wildcaves3.init.WCBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WCColorHandler {
    public static void registerRenders() {
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos, int i) {
                if (WCBlocks.cap.getMetaFromState(iBlockState) < 6)
                    return iBlockAccess != null ? BiomeColorHelper.getFoliageColorAtPos(iBlockAccess, blockPos) : ColorizerFoliage.getFoliageColorBasic();
                return -1;
            }
        }, WCBlocks.cap);
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
            @Override
            public int colorMultiplier(ItemStack itemStack, int i) {
                if (itemStack.getMetadata() < 6) return ColorizerFoliage.getFoliageColorBasic();
                return -1;
            }
        }, WCBlocks.cap);
    }
}
