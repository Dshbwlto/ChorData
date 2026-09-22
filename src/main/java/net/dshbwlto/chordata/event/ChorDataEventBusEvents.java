package net.dshbwlto.chordata.event;

import net.dshbwlto.chordata.ChorData;
import net.dshbwlto.chordata.entity.ChorDataEntities;
import net.dshbwlto.chordata.entity.api.ChorDataMathUtil;
import net.dshbwlto.chordata.entity.client.ChorDataModelLayers;
import net.dshbwlto.chordata.entity.client.shark.SharkModel;
import net.dshbwlto.chordata.entity.custom.SharkEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = ChorData.MOD_ID)
public class ChorDataEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ChorDataModelLayers.SHARK, SharkModel::createBodyLayer);
    }


    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ChorDataEntities.SHARK.get(), SharkEntity.createAttributes().build());
    }
}
