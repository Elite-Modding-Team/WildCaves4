package mod.emt.wildcaves4.client;  // ← better package for client-only stuff

import mod.emt.wildcaves4.Tags;
import mod.emt.wildcaves4.init.WCBlocks;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Tags.MOD_ID)
public class WCModelHandler {
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        registerMultiItemModels(Item.getItemFromBlock(WCBlocks.stalactite_stone), WCBlocks.STALACS.size());
        registerMultiItemModels(Item.getItemFromBlock(WCBlocks.stalactite_sandstone), WCBlocks.SAND_STALACS.size());
        registerMultiItemModels(Item.getItemFromBlock(WCBlocks.icicle), WCBlocks.ICICLES.size());
        registerMultiItemModels(Item.getItemFromBlock(WCBlocks.cap), WCBlocks.CAPS.size());
        registerMultiItemModels(Item.getItemFromBlock(WCBlocks.fossil), WCBlocks.FOSSILS.size());
    }

    private static void registerMultiItemModels(Item item, int variantCount) {
        if (item == null || variantCount <= 0) return;
        String basePath = item.getRegistryName().getPath();
        for (int meta = 0; meta < variantCount; meta++) {
            String variantName = basePath + "_" + meta;
            ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Tags.MOD_ID + ":" + variantName, "inventory"));
        }
    }
}
