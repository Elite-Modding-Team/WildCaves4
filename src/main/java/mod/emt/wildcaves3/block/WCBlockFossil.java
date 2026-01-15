package mod.emt.wildcaves3.block;

import mod.emt.wildcaves3.util.WCUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

public class WCBlockFossil extends Block {
    public WCBlockFossil() {
        super(Material.ROCK);
        this.setHardness(1F);
    }

    @Override
    public Item getItemDropped(IBlockState metadata, Random random, int par3) {
        int choice = WCUtils.weightedChoice(0.5f, 0.15f, 0.05f, 0.5f, 0, 0);
        Item result;
        switch (choice) {
            case 1:
                result = Items.BONE;
                break;
            case 2:
                result = Items.ARROW;
                break;
            case 3:
                result = Items.SKULL;
                break;
            default:
                result = Item.getItemFromBlock(Blocks.COBBLESTONE);
        }
        return result;
    }

}
