package net.dshbwlto.chordata.entity.api;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class VerticalSwingingTailHandler extends AbstractTailHandler {

    float speed;
    float amount;

    public VerticalSwingingTailHandler(Entity entity, int length, float tailWaveSpeed, float tailWaveAmount) {
        super(entity, length, tailWaveSpeed, tailWaveAmount);
        this.speed = tailWaveSpeed;
        this.amount = tailWaveAmount;
    }

    /// Used for whale-like body types where the tail moves up and down.
    @Override
    public float getTailSwing(int i) {
        return Mth.sin((ChorDataMathUtil.getTicksAndPartialTicks() / 8 - i) * speed) * amount;
    }

    @Override
    public float getTailXRot(int index) {
        return super.getTailXRot(index) + getTailSwing(index);
    }
}
