package net.dshbwlto.chordata.entity.api;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

public class TentacleTailHandler extends AbstractTailHandler {

    float speed;
    float amount;
    float randomX;
    float randomY;

    public TentacleTailHandler(Entity entity, int length, float tailWaveSpeed, float tailWaveAmount, float randomX, float randomY) {
        super(entity, length, tailWaveSpeed, tailWaveAmount);
        this.speed = tailWaveSpeed;
        this.amount = tailWaveAmount;
        this.randomX = randomX;
        this.randomY = randomY;
    }

    /// Used for serpent or worm-like creatures, or add multiple and randomize for squid tentacles.
    public float getXTailSwing(int i) {
        return Mth.sin((ChorDataMathUtil.getTicksAndPartialTicks() / 8 - i + randomX * randomY) * speed) * amount;
    }

    public float getYTailSwing(int i) {
        return Mth.sin((ChorDataMathUtil.getTicksAndPartialTicks() / 8 - i + Mth.PI / 2 + randomY * randomX) * speed) * amount;
    }

    @Override
    public float getTailXRot(int index) {
        return super.getTailXRot(index) + getXTailSwing(index);
    }

    @Override
    public float getTailYRot(int index) {
        return super.getTailYRot(index) + getYTailSwing(index);
    }
}
