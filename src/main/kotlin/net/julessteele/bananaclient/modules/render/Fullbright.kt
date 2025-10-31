package net.julessteele.bananaclient.modules.render

import net.julessteele.bananaclient.modules.module.Category
import net.julessteele.bananaclient.modules.module.Module
import net.minecraft.client.texture.NativeImage
import net.minecraft.client.texture.NativeImageBackedTexture
import net.minecraft.util.Colors
import net.minecraft.util.Identifier

class Fullbright: Module("Fullbright", "Makes everything fully bright", Category.RENDER) {

    companion object {
        @JvmField
        var whiteTex: NativeImageBackedTexture? = null
        val WHITE_TEX_ID: Identifier = Identifier.of("bananaclient", "fullbright_white")
    }

    override fun onEnable() {
        if (whiteTex == null) {
            // Create a 16x16 white image
            val image = NativeImage(16, 16, false)
            for (x in 0 until 16) {
                for (y in 0 until 16) {
                    image.setColor(x, y, Colors.WHITE)
                }
            }

            // Wrap in a NativeImageBackedTexture
            whiteTex = NativeImageBackedTexture({ "fullbright_white" }, image)

            // Register with the TextureManager
            client.textureManager.registerTexture(WHITE_TEX_ID, whiteTex)
        }
    }

    override fun onDisable() {
        client.worldRenderer.reload()
    }
}