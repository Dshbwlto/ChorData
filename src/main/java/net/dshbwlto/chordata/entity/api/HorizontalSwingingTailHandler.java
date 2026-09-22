package net.dshbwlto.chordata.entity.api;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class HorizontalSwingingTailHandler extends AbstractTailHandler {

    float speed;
    float amount;

    public HorizontalSwingingTailHandler(Entity entity, int length, float tailWaveSpeed, float tailWaveAmount) {
        super(entity, length, tailWaveSpeed, tailWaveAmount);
        this.speed = tailWaveSpeed;
        this.amount = tailWaveAmount;
    }

    /// Used for fish, sharks, or anything else with a tail that moves from side to side.
    @Override
    public float getTailSwing(int i) {
        return Mth.sin((ChorDataMathUtil.getTicksAndPartialTicks() * speed / 10) - i) * amount;
    }

    @Override
    public float getTailYRot(int index) {
        return super.getTailYRot(index) + getTailSwing(index);
    }
}
