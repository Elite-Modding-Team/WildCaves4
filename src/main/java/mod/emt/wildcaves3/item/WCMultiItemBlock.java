package mod.emt.wildcaves3.item;

import mod.emt.wildcaves3.Tags;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class WCMultiItemBlock extends ItemBlock {
    private final ArrayList<String> subNames;

    public WCMultiItemBlock(Block block, ArrayList<String> names) {
        super(block);
        this.subNames = names;
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        if (damage >= subNames.size()) damage = 0;
        return damage;
    }

    @Override
    public String getTranslationKey(ItemStack itemstack) {
        return "tile." + Tags.MOD_ID + "." + subNames.get(itemstack.getMetadata());
    }
}
