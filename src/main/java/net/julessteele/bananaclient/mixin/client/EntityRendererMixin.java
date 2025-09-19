package net.julessteele.bananaclient.mixin.client;

import net.julessteele.bananaclient.module.ModuleManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {

    @Inject(method = "getBlockLight", at = @At("HEAD"), cancellable = true)
    private void onGetBlockLight(T entity, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (ModuleManager.INSTANCE.getModuleByName("Fullbright").getEnabled()) {
            cir.setReturnValue(15);
        }
    }

    @Inject(method = "getSkyLight", at = @At("HEAD"), cancellable = true)
    private void onGetSkyLight(T entity, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (ModuleManager.INSTANCE.getModuleByName("Fullbright").getEnabled()) {
            cir.setReturnValue(15);
        }
    }
}
