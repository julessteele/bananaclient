package net.julessteele.bananaclient

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents
import net.julessteele.bananaclient.module.ModuleManager
import net.julessteele.bananaclient.modules.Fullbright
import net.minecraft.client.MinecraftClient

object BananaClient : ClientModInitializer {

    override fun onInitializeClient() {
        Banana.logger.info("Hello from the {} Client Mod Initializer!", Banana.name)

        // Register modules
        ModuleManager.register(Fullbright())

        // Hook into ticks
        ClientTickEvents.END_CLIENT_TICK.register {
            ModuleManager.onTick()
        }

        // Hook into entity rendering
        WorldRenderEvents.AFTER_ENTITIES.register(WorldRenderEvents.AfterEntities { context ->
            val matrices = context.matrixStack()

            if (matrices != null)
                ModuleManager.onRenderEntity(matrices)
            else
                Banana.logger.error("ENTITY RENDERING HOOK: Matrices were empty")
        })

        // Hook into HUD rendering
        WorldRenderEvents.LAST.register(WorldRenderEvents.Last { context ->
            val mc = MinecraftClient.getInstance()
            val matrices = context.matrixStack()

            if (matrices != null)
                ModuleManager.onRenderHUD(mc, matrices)
            else
                Banana.logger.error("HUD RENDERING HOOK: Matrices were empty")
        })
    }
}