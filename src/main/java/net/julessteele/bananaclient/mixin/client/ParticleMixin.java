package net.julessteele.bananaclient.mixin.client;

import net.julessteele.bananaclient.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Particle.class)
public abstract class ParticleMixin {
    @Inject(method = "getBrightness", at = @At("RETURN"), cancellable = true)
    private void forceFullbright(float tint, CallbackInfoReturnable<Integer> cir) {
        if (ModuleManager.INSTANCE.getModule("fullbright").getEnabled()) {
            cir.setReturnValue(0xF000F0); // max lightmap
        }
    }
}
