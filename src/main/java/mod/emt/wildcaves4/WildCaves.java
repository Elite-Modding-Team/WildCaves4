package mod.emt.wildcaves4;

import mod.emt.wildcaves4.client.WCColorHandler;
import mod.emt.wildcaves4.config.WCConfig;
import mod.emt.wildcaves4.event.WCEventLootTableLoad;
import mod.emt.wildcaves4.event.WCEventOreGen;
import mod.emt.wildcaves4.gen.WCWorldGen;
import mod.emt.wildcaves4.init.WCBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// TODO: All this should be in a config class
@Mod(modid = Tags.MOD_ID, name = Tags.NAME, version = Tags.VERSION)
public class WildCaves {

    public static final String ID = Tags.MOD_ID;
    public static final String NAME = Tags.NAME;
    public static final String VERSION = Tags.VERSION;

    public static Configuration config;

    public static CreativeTabs tabWildCaves = new CreativeTabs("WildCaves4") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(WCBlocks.fossil);
        }
    };

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        WCWorldGen gen = new WCWorldGen(config);
        if (WCWorldGen.maxLength > 0) MinecraftForge.EVENT_BUS.register(gen);
        if (WCConfig.WORLD_GEN.fossilChance > 0) MinecraftForge.ORE_GEN_BUS.register(new WCEventOreGen(WCConfig.WORLD_GEN.fossilChance));
        if (WCConfig.WORLD_GEN.skullChestChance > 0) MinecraftForge.EVENT_BUS.register(new WCEventLootTableLoad());
    }

    @SideOnly(Side.CLIENT)
    @EventHandler
    public void initClient(FMLInitializationEvent event) {
        WCColorHandler.registerRenders();
    }
}
