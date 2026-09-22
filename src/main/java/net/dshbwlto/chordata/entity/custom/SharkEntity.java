
package net.dshbwlto.chordata.entity.custom;

import net.dshbwlto.chordata.entity.api.ChorDataMathUtil;
import net.dshbwlto.chordata.entity.api.HorizontalSwingingTailHandler;
import net.dshbwlto.chordata.entity.api.TentacleTailHandler;
import net.dshbwlto.chordata.entity.api.VerticalSwingingTailHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;

public class SharkEntity extends Animal {

    public TentacleTailHandler tailHandler;
    public SharkEntity(EntityType entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, getTarget() == null ? 85 : 150, 2, 0.02F, 0.1F, false);
        this.lookControl = new SmoothSwimmingLookControl(this, getTarget() == null ? 2 : 5);
        this.tailHandler = new TentacleTailHandler(this, 6, 1, 10, 5.6f, 4.3f);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        return false;
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2F, true));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, (double)1, 100){
            @Override
            public boolean canUse() {
                if (this.mob.hasControllingPassenger()) {
                    return false;
                } else {
                    if (random.nextFloat() < 0.1) {
                        Vec3 vec3 = this.getPosition();
                        if (vec3 == null) {
                            return false;
                        } else {
                            this.wantedX = vec3.x;
                            this.wantedY = vec3.y;
                            this.wantedZ = vec3.z;
                            this.forceTrigger = false;
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
            }
        });
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, PathfinderMob.class, false));
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isControlledByLocalInstance() && this.isInWater()) {
            this.moveRelative(0.1F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
        } else {
            super.travel(travelVector);
        }
    }

    protected PathNavigation createNavigation(Level level) {
        return new WaterBoundPathNavigation(this, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.25d)
                .add(Attributes.FOLLOW_RANGE, 30)
                .add(Attributes.KNOCKBACK_RESISTANCE, 100d)
                .add(Attributes.ATTACK_DAMAGE, 30)
                .add(Attributes.ENTITY_INTERACTION_RANGE, 40);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    @Override
    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
    }

    @Override
    public @Nullable ItemStack getPickResult() {
        return null;
    }

    /* ANIMATIONS */

    @Override
    public void tick() {
        super.tick();
        this.tailHandler.tailTick(this.getYRot(), this.getXRot());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }

    /* INTERACT */

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return super.mobInteract(player, hand);
    }
}
