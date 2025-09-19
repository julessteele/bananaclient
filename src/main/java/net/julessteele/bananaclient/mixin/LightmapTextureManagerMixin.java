package net.julessteele.bananaclient.mixin;

import com.mojang.blaze3d.textures.GpuTextureView;
import net.julessteele.bananaclient.module.Module;
import net.julessteele.bananaclient.module.ModuleManager;
import net.julessteele.bananaclient.modules.Fullbright;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin {

    @Inject(method = "getGlTextureView", at = @At("HEAD"), cancellable = true)
    private void onGetGlTextureView(CallbackInfoReturnable<GpuTextureView> cir) {
        Module fullbrightModule = ModuleManager.INSTANCE.getModuleByName("Fullbright");
        if (fullbrightModule != null && fullbrightModule.getEnabled() && Fullbright.whiteTex != null) {
            // Return the white texture's GlTextureView
            cir.setReturnValue(Fullbright.whiteTex.getGlTextureView());
        }
    }
}
