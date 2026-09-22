package net.dshbwlto.chordata.entity.api;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.checkerframework.checker.units.qual.C;

public class AbstractTailHandler {

    public Entity entity;
    public int length;

    public boolean turning_y;
    public float yRot0 = 0;
    public float yRot = 0;
    public float yDir0 = 0;
    public float yMomentum = 0;

    public boolean turning_x;
    public float xRot0 = 0;
    public float xRot = 0;
    public float xDir0 = 0;
    public float xMomentum = 0;

    public AbstractTailHandler(Entity entity, int length, float tailWaveSpeed, float tailWaveAmount) {
        this.entity = entity;
        this.length = length;
    }

    public float[] tailYRots = new float[100];
    public float[] tailXRots = new float[100];

    public float getTailSwing(int i) {
        return 0;
    }

    /// Call this in your entity's tick method
    public void tailTick(float yBodyRot, float xBodyRot) {
        turning_y = yRot0 != yBodyRot;
        yDir0 = yBodyRot - yRot0 > 0 ? 1 : -1;
        yRot0 = yBodyRot;

        turning_x = xRot0 != xBodyRot;
        xDir0 = xBodyRot - xRot0 > 0 ? 1 : -1;
        xRot0 = xBodyRot;
    }

    /// Set as your entity model's Y rot in the setupAnim method
    public float newBodyYRotRadians() {
        return newBodyYRot() * ((float)Math.PI / 180F);
    }
    /// Returns degrees, use in the render method for posestack/item rendering/Create partial models
    public float newBodyYRot() {
        float x = -entity.getPreciseBodyRotation(ChorDataMathUtil.getPartialTicks());
        if (entity.level().isClientSide && !ChorDataMathUtil.isGamePaused()) {
            int i = turning_y ? (yDir0 > 1 ? 1 : -1) : 0;
            if (this.yMomentum <= 0.02 && this.yMomentum >= -0.02) {
                this.yMomentum += i * 0.001f;
            }
            yRot = Mth.rotLerp(yMomentum * i, yRot, entity.getPreciseBodyRotation(ChorDataMathUtil.getPartialTicks()));
        }
        return x + yRot;
    }

    /// Set as your entity model's X rot in the setupAnim method
    public float newBodyXRotRadians() {
        return newBodyXRot() * ((float)Math.PI / 180F);
    }
    /// Returns degrees
    public float newBodyXRot() {
        if (entity.level().isClientSide && !ChorDataMathUtil.isGamePaused()) {
            int i = turning_x ? (xDir0 > 1 ? 1 : -1) : 0;
            if (this.xMomentum <= 0.01 && this.xMomentum >= -0.01) {
                this.xMomentum += i * 0.001f;
            }
            xRot = Mth.rotLerp(0.01f, xRot, entity.getXRot());
        }
        return xRot;
    }

    /// Set as your entity model's tail segment Y rotation in the setupAnim method, where each progressive segment has a higher index.
    /// Tail segment one has an index of 1, segment 2 has an index of 2, etc.
    public float getTailYRotRadians(int index) {
        return getTailYRot(index) * ((float)Math.PI / 180F);
    }
    /// Returns degrees
    public float getTailYRot(int index) {
        if (entity.level().isClientSide() && !ChorDataMathUtil.isGamePaused()) {
            tailYRots[index] = Mth.rotLerp(((float) index) * 0.06f, tailYRots[index], index == 1 ? yRot : tailYRots[index - 1]);
        }
        return (-yRot + tailYRots[index]);
    }

    /// Same principle but for X rotation
    public float getTailXRotRadians(int index) {
        return getTailXRot(index) * ((float)Math.PI / 180F);
    }
    /// Returns degrees
    public float getTailXRot(int index) {
        if (entity.level().isClientSide() && !ChorDataMathUtil.isGamePaused()) {
            tailXRots[index] = Mth.rotLerp(((float) index) * 0.05f, tailXRots[index], index == 1 ? xRot : tailXRots[index - 1]);
        }
        return (-xRot + tailXRots[index]);
    }
}
