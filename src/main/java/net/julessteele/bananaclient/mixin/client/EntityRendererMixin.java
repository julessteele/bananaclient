package net.julessteele.bananaclient.mixin.client;

import net.julessteele.bananaclient.module.ModuleManager;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {

    @Inject(method = "getBlockLight", at = @At("HEAD"), cancellable = true)
    private void onGetBlockLight(T entity, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (ModuleManager.INSTANCE.getModule("fullbright").getEnabled()) {
            cir.setReturnValue(15);
        }
    }

    @Inject(method = "getSkyLight", at = @At("HEAD"), cancellable = true)
    private void onGetSkyLight(T entity, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (ModuleManager.INSTANCE.getModule("fullbright").getEnabled()) {
            cir.setReturnValue(15);
        }
    }

    // Hook onRenderEntity()
    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(S renderState, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState, CallbackInfo ci) {
        ModuleManager.INSTANCE.onRenderEntity(matrices);
    }
}
