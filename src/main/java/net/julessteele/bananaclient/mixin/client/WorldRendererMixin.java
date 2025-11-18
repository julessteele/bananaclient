package net.julessteele.bananaclient.mixin.client;

import net.julessteele.bananaclient.modules.module.ModuleManager;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Inject(method = "getLightmapCoordinates(Lnet/minecraft/client/render/WorldRenderer$BrightnessGetter;Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)I", at = @At("HEAD"), cancellable = true)
    private static void onGetLightmapCoordinates(WorldRenderer.BrightnessGetter brightnessGetter, BlockRenderView world, BlockState state, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (ModuleManager.INSTANCE.getModule("fullbright", false) == null) return;
        if (ModuleManager.INSTANCE.getModule("fullbright", false).getEnabled()) {
            cir.setReturnValue(0xF000F0);
        }
    }
}
