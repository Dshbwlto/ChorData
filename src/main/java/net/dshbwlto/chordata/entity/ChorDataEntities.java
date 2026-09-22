
package net.dshbwlto.chordata.entity;

import net.dshbwlto.chordata.ChorData;
import net.dshbwlto.chordata.entity.custom.SharkEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ChorDataEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, ChorData.MOD_ID);

    public static final Supplier<EntityType<SharkEntity>> SHARK =
            ENTITIES.register("shark", () -> EntityType.Builder.of(SharkEntity::new, MobCategory.CREATURE)
                    .sized(3f, 3f).build("shark"));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
