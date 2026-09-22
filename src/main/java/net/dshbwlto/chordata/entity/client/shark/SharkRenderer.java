package net.dshbwlto.chordata.entity.client.shark;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dshbwlto.chordata.ChorData;
import net.dshbwlto.chordata.entity.client.ChorDataModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class SharkRenderer extends MobRenderer {

    public SharkRenderer(EntityRendererProvider.Context context) {
        super (context, new SharkModel(context.bakeLayer(ChorDataModelLayers.SHARK)), 4);
    }

    @Override
    public ResourceLocation getTextureLocation(Entity entity) {
        return ResourceLocation.fromNamespaceAndPath(ChorData.MOD_ID, "textures/entity/shark/shark.png");
    }

    @Override
    public void render(LivingEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public boolean shouldRender(Entity livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }
}