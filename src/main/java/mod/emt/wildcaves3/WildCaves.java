package mod.emt.wildcaves3;

import mod.emt.wildcaves3.client.WCColorHandler;
import mod.emt.wildcaves3.event.WCEventLootTableLoad;
import mod.emt.wildcaves3.event.WCEventOreGen;
import mod.emt.wildcaves3.gen.WCWorldGen;
import mod.emt.wildcaves3.init.WCBlocks;
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

@Mod(modid = Tags.MOD_ID, name = Tags.NAME, version = Tags.VERSION)
public class WildCaves {

    public static Configuration config;
    public static boolean solidStalactites;
    public static boolean damageWhenFallenOn;
    public static int floraLightLevel;
    public static int fossilChance;
    public static int chestSkull;

    public static CreativeTabs tabWildCaves = new CreativeTabs("WildCaves3") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(WCBlocks.fossil);
        }
    };

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        solidStalactites = config.getBoolean("Solid stalactites/stalagmites", Configuration.CATEGORY_GENERAL, false, "Whether stalactites can be collided with.");
        damageWhenFallenOn = config.getBoolean("Stalagmites damage entities when fallen on", Configuration.CATEGORY_GENERAL, false, "Whether living beings would be damaged when falling on the block.");
        floraLightLevel = config.getInt("Flora light level", Configuration.CATEGORY_GENERAL, 5, 0, 15, "How much light is emitted by the mushrooms.");
        fossilChance = config.get(Configuration.CATEGORY_GENERAL, "Chance for a fossil node to generate", 5).getInt();
        chestSkull = config.get(Configuration.CATEGORY_GENERAL, "Chance for a skull to be added in chests", 50).getInt();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        WCWorldGen gen = new WCWorldGen(config);
        if (WCWorldGen.maxLength > 0) MinecraftForge.EVENT_BUS.register(gen);
        if (fossilChance > 0) MinecraftForge.ORE_GEN_BUS.register(new WCEventOreGen(fossilChance));
        if (chestSkull > 0) MinecraftForge.EVENT_BUS.register(new WCEventLootTableLoad());
    }

    @SideOnly(Side.CLIENT)
    @EventHandler
    public void initClient(FMLInitializationEvent event) {
        WCColorHandler.registerRenders();
    }
}
