package net.julessteele.bananaclient

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.julessteele.bananaclient.command.CommandManager
import net.julessteele.bananaclient.commands.HelpCommand
import net.julessteele.bananaclient.commands.ListModulesCommand
import net.julessteele.bananaclient.commands.SayCommand
import net.julessteele.bananaclient.commands.ToggleCommand
import net.julessteele.bananaclient.module.ModuleManager
import net.julessteele.bananaclient.modules.hud.ClickGui
import net.julessteele.bananaclient.modules.hud.ModuleHudList
import net.julessteele.bananaclient.modules.movement.Jesus
import net.julessteele.bananaclient.modules.render.Fullbright

object BananaClient : ClientModInitializer {

    override fun onInitializeClient() {

        Banana.logger.info("Hello from the ${Banana.NAME} Client Mod Initializer!")

        // Register modules
        ModuleManager.register(Fullbright())
        ModuleManager.register(Jesus())
        ModuleManager.register(ModuleHudList())
        ModuleManager.register(ClickGui())

        // Register commands
        CommandManager.register(HelpCommand())
        CommandManager.register(ToggleCommand())
        CommandManager.register(SayCommand())
        CommandManager.register(ListModulesCommand())

        // Hook into ticks
        ClientTickEvents.END_CLIENT_TICK.register {
            ModuleManager.onTick()
        }

        // Hook into entity rendering in net/julessteele/bananaclient/mixin/client/EntityRendererMixin.java

        // Hook into HUD rendering in net/julessteele/bananaclient/mixin/client/InGameHudMixin.java

        // Print that initializing is finished
        Banana.logger.info("${Banana.NAME} has finished setting up and has attached hooks...")
    }
}