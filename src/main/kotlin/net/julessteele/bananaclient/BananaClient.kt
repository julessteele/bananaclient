package net.julessteele.bananaclient

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents
import net.julessteele.bananaclient.command.CommandManager
import net.julessteele.bananaclient.commands.HelpCommand
import net.julessteele.bananaclient.commands.ListModulesCommand
import net.julessteele.bananaclient.commands.SayCommand
import net.julessteele.bananaclient.commands.ToggleCommand
import net.julessteele.bananaclient.module.ModuleManager
import net.julessteele.bananaclient.modules.hud.ModuleHudList
import net.julessteele.bananaclient.modules.movement.Jesus
import net.julessteele.bananaclient.modules.render.Fullbright
import net.minecraft.client.MinecraftClient

object BananaClient : ClientModInitializer {

    override fun onInitializeClient() {

        Banana.logger.info("Hello from the ${Banana.NAME} Client Mod Initializer!")

        // Register modules
        ModuleManager.register(Fullbright())
        ModuleManager.register(Jesus())
        ModuleManager.register(ModuleHudList())

        // Register commands
        CommandManager.register(HelpCommand())
        CommandManager.register(ToggleCommand())
        CommandManager.register(SayCommand())
        CommandManager.register(ListModulesCommand())

        // Hook into ticks
        ClientTickEvents.END_CLIENT_TICK.register {
            ModuleManager.onTick()
        }

        // Hook into entity rendering
        WorldRenderEvents.AFTER_ENTITIES.register(WorldRenderEvents.AfterEntities { context ->
            val matrices = context.matrixStack()

            if (matrices != null)
                ModuleManager.onRenderEntity(matrices, context)
            else
                Banana.logger.error("ENTITY RENDERING HOOK: Matrices were empty")
        })

        // Hook into HUD rendering
        HudRenderCallback.EVENT.register { context, tickCounter ->
            ModuleManager.onRenderHUD(context)
        }

        // Print that initializing is finished
        Banana.logger.info("${Banana.NAME} has finished setting up and has attached hooks...")
    }
}