package mod.emt.wildcaves4.event;

import mod.emt.wildcaves4.WildCaves;
import mod.emt.wildcaves4.config.WCConfig;
import net.minecraft.init.Items;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WCEventLootTableLoad {
    @SubscribeEvent
    public void onLootLoad(LootTableLoadEvent loading) {
        if (loading.getName() == LootTableList.CHESTS_SIMPLE_DUNGEON || loading.getName() == LootTableList.CHESTS_ABANDONED_MINESHAFT || loading.getName() == LootTableList.CHESTS_STRONGHOLD_CORRIDOR) {
            loading.getTable().addPool(new LootPool(new LootEntry[]{new LootEntryItem(Items.SKULL, 1, 0, new LootFunction[]{new SetMetadata(null, new RandomValueRange(0, 4))}, new LootCondition[0], "skull")}, new LootCondition[]{new RandomChance(1 / (float) WCConfig.WORLD_GEN.skullChestChance)}, new RandomValueRange(1, 1), new RandomValueRange(0, 0), "skulls"));
        }
    }
}
