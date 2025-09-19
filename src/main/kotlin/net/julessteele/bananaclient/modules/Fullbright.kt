package net.julessteele.bananaclient.modules

import com.mojang.blaze3d.vertex.VertexFormat
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents
import net.julessteele.bananaclient.module.Module
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gl.ShaderProgram
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.render.Tessellator
import net.minecraft.client.render.VertexFormats
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier
import org.lwjgl.glfw.GLFW.GLFW_KEY_B

class Fullbright : Module(name = "Fullbright", description = "See in the dark", category = Category.RENDER, keyCode = GLFW_KEY_B) {

    private var shader: ShaderProgram? = null

    override fun onEnable() {

        WorldRenderEvents.END.register { context ->
            val mc = MinecraftClient.getInstance()
            if (mc.player == null || mc.world == null) return@register

            if (shader == null) {
                shader = GameRenderer.loadShader(
                    Identifier.of("bananaclient", "fullbright")
                )
            }

            shader?.let { renderFullScreenQuad(it, context.matrixStack()) }

        }
    }

    override fun onDisable() {
    }

    private fun renderFullScreenQuad(shader: ShaderProgram, matrices: MatrixStack) {
        shader.bind()

        val tessellator = Tessellator.getInstance()
        val buffer = tessellator.buffer
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION)
        buffer.vertex(-1.0, -1.0, 0.0).next()
        buffer.vertex(1.0, -1.0, 0.0).next()
        buffer.vertex(1.0, 1.0, 0.0).next()
        buffer.vertex(-1.0, 1.0, 0.0).next()
        tessellator.draw()

        shader.unbind()
    }
}
