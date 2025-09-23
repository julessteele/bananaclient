package net.julessteele.bananaclient.modules

import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.platform.DepthTestFunction
import com.mojang.blaze3d.vertex.VertexFormat
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext
import net.julessteele.bananaclient.Banana
import net.julessteele.bananaclient.module.Module
import net.julessteele.bananaclient.module.ModuleManager
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.RenderPhase
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.VertexFormats
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3d
import org.joml.Matrix4f
import java.util.*


class ESP : Module("ESP", "Shows entities through walls.", Category.RENDER) {

    override fun onRenderEntity(matrices: MatrixStack, context: WorldRenderContext) {
        super.onRenderEntity(matrices, context)

        ModuleManager.getModuleByName("fullbright")?.enabled?.let { if (!it) return }

        val client = MinecraftClient.getInstance()
        val world = client.world ?: return
        val matrices: MatrixStack = context.matrixStack() ?: return
        val camPos: Vec3d = context.camera().pos
        val consumers: VertexConsumerProvider = context.consumers() ?: return
        val consumer = consumers.getBuffer(ESPRenderLayer.LINES_NO_DEPTH)

        for (entity in world.entities) {
            //if (entity is PlayerEntity && entity != client.player) {
                val box: Box = entity.boundingBox.offset(-camPos.x, -camPos.y, -camPos.z)
                drawBox(matrices, consumer, box, 0f, 0f, 1f, 1f) // blue
            //}
        }
    }

    fun drawBox(matrices: MatrixStack, consumer: VertexConsumer, box: Box, r: Float, g: Float, b: Float, a: Float) {

        val entry = matrices.peek()
        val pose: Matrix4f = entry.positionMatrix

        fun vertex(x: Double, y: Double, z: Double, nx: Float, ny: Float, nz: Float) {
            consumer.vertex(pose, x.toFloat(), y.toFloat(), z.toFloat())
                .color(r, g, b, a)
                .overlay(0, 0)
                .light(0, 0)
                .normal(entry, nx, ny, nz)
        }

        // Bottom square
        vertex(box.minX, box.minY, box.minZ, 0f, -1f, 0f)
        vertex(box.maxX, box.minY, box.minZ, 0f, -1f, 0f)

        vertex(box.maxX, box.minY, box.minZ, 0f, -1f, 0f)
        vertex(box.maxX, box.minY, box.maxZ, 0f, -1f, 0f)

        vertex(box.maxX, box.minY, box.maxZ, 0f, -1f, 0f)
        vertex(box.minX, box.minY, box.maxZ, 0f, -1f, 0f)

        vertex(box.minX, box.minY, box.maxZ, 0f, -1f, 0f)
        vertex(box.minX, box.minY, box.minZ, 0f, -1f, 0f)

        // Top square
        vertex(box.minX, box.maxY, box.minZ, 0f, 1f, 0f)
        vertex(box.maxX, box.maxY, box.minZ, 0f, 1f, 0f)

        vertex(box.maxX, box.maxY, box.minZ, 0f, 1f, 0f)
        vertex(box.maxX, box.maxY, box.maxZ, 0f, 1f, 0f)

        vertex(box.maxX, box.maxY, box.maxZ, 0f, 1f, 0f)
        vertex(box.minX, box.maxY, box.maxZ, 0f, 1f, 0f)

        vertex(box.minX, box.maxY, box.maxZ, 0f, 1f, 0f)
        vertex(box.minX, box.maxY, box.minZ, 0f, 1f, 0f)

        // Vertical edges
        vertex(box.minX, box.minY, box.minZ, -1f, 0f, 0f)
        vertex(box.minX, box.maxY, box.minZ, -1f, 0f, 0f)

        vertex(box.maxX, box.minY, box.minZ, 1f, 0f, 0f)
        vertex(box.maxX, box.maxY, box.minZ, 1f, 0f, 0f)

        vertex(box.maxX, box.minY, box.maxZ, 1f, 0f, 0f)
        vertex(box.maxX, box.maxY, box.maxZ, 1f, 0f, 0f)

        vertex(box.minX, box.minY, box.maxZ, -1f, 0f, 0f)
        vertex(box.minX, box.maxY, box.maxZ, -1f, 0f, 0f)
    }

    object ESPRenderLayer {
        val LINES_NO_DEPTH: RenderLayer = RenderLayer.of(
            "lines_no_depth",
            256,
            false,
            true,
            RenderPipeline.builder()
                .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
                .withCull(false)
                .withLocation(Identifier.of(Banana.MOD_ID, "render_pipelines/esp_lines_pipeline.json"))
                .withVertexShader(Identifier.of("minecraft", "core/entity"))
                .withFragmentShader(Identifier.of("minecraft", "core/entity"))
                .withVertexFormat(VertexFormats.POSITION_COLOR, VertexFormat.DrawMode.LINES)
                .build(),
            RenderLayer.MultiPhaseParameters.builder()
                .lineWidth(RenderPhase.LineWidth(OptionalDouble.of(2.0)))
                .build(false)
        )
    }
}