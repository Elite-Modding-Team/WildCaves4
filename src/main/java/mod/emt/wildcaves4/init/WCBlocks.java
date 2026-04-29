package mod.emt.wildcaves4.init;

import mod.emt.wildcaves4.Tags;
import mod.emt.wildcaves4.WildCaves;
import mod.emt.wildcaves4.block.*;
import mod.emt.wildcaves4.config.WCConfig;
import mod.emt.wildcaves4.item.WCItemStalactite;
import mod.emt.wildcaves4.item.WCMultiItemBlock;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public class WCBlocks {
    public static final ArrayList<String> STALACS = new ArrayList<>(Arrays.asList("stalactite_1", "stalactite_2", "stalactite_3", "stalactite_4", "stalactite_connection_1", "stalactite_connection_2", "stalactite_connection_3", "stalactite_connection_4", "stalactite_end", "stalagmite_end", "stalagmite_1", "stalagmite_2", "stalagmite_3"));
    public static final ArrayList<String> SAND_STALACS = new ArrayList<>(Arrays.asList("stalactite_sandstone_1", "stalactite_sandstone_2", "stalactite_sandstone_3", "stalactite_sandstone_4", "stalactite_sandstone_connection_1", "stalactite_sandstone_connection_2", "stalactite_sandstone_connection_3", "stalactite_sandstone_connection_4", "stalactite_sandstone_end", "stalagmite_sandstone_end", "stalagmite_sandstone_1", "stalagmite_sandstone_2", "stalagmite_sandstone_3"));
    public static final ArrayList<String> ICICLES = new ArrayList<>(Arrays.asList("icicle_1", "icicle_2", "icicle_3"));
    public static final ArrayList<String> CAPS = new ArrayList<>(Arrays.asList("glowcap_1", "glowcap_2", "glowcap_3", "gloweed_1", "glowcap_4_top", "glowcap_4_bottom", "iceshroom_1", "iceshroom_2", "iceshroom_3", "iceshroom_4"));
    public static final ArrayList<String> FOSSILS = new ArrayList<>(Collections.singletonList("fossil_1"));

    public static Block stalactite_stone;
    public static Block stalactite_sandstone;
    public static Block icicle;
    public static Block cap;
    public static Block fossil;

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        stalactite_stone = new WCBlockStalactiteStone().setRegistryName("stalactite_stone").setTranslationKey(Tags.MOD_ID + ".stalactite_stone").setCreativeTab(WildCaves.tabWildCaves);
        stalactite_sandstone = new WCBlockStalactiteSandstone(Item.getItemFromBlock(Blocks.SANDSTONE)).setRegistryName("stalactite_sandstone").setTranslationKey(Tags.MOD_ID + ".stalactite_sandstone").setCreativeTab(WildCaves.tabWildCaves);
        icicle = new WCBlockIcicle().setRegistryName("icicle").setTranslationKey(Tags.MOD_ID + ".icicle").setCreativeTab(WildCaves.tabWildCaves);
        cap = new WCBlockCap().setLightLevel(WCConfig.GENERAL.floraLightLevel).setRegistryName("cap").setTranslationKey(Tags.MOD_ID + ".cap").setCreativeTab(WildCaves.tabWildCaves);
        fossil = new WCBlockFossil().setRegistryName("fossil").setTranslationKey(Tags.MOD_ID + ".fossil").setCreativeTab(WildCaves.tabWildCaves);

        event.getRegistry().registerAll(stalactite_stone, stalactite_sandstone, icicle, cap, fossil);
    }

    @SubscribeEvent
    public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new WCItemStalactite(stalactite_stone, STALACS).setRegistryName(stalactite_stone.getRegistryName()),
                new WCItemStalactite(stalactite_sandstone, SAND_STALACS).setRegistryName(stalactite_sandstone.getRegistryName()),
                new WCMultiItemBlock(icicle, ICICLES).setRegistryName(icicle.getRegistryName()),
                new WCMultiItemBlock(cap, CAPS).setRegistryName(cap.getRegistryName()),
                new WCMultiItemBlock(fossil, FOSSILS).setRegistryName(fossil.getRegistryName()));
    }
}
