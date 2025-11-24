package net.julessteele.bananaclient

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.julessteele.bananaclient.command.CommandManager
import net.julessteele.bananaclient.commands.BindCommand
import net.julessteele.bananaclient.commands.HelpCommand
import net.julessteele.bananaclient.commands.ListModulesCommand
import net.julessteele.bananaclient.commands.SayCommand
import net.julessteele.bananaclient.commands.SetModuleSettingCommand
import net.julessteele.bananaclient.commands.ToggleCommand
import net.julessteele.bananaclient.modules.module.ModuleManager
import net.julessteele.bananaclient.modules.hud.ClickGui
import net.julessteele.bananaclient.modules.hud.HudList
import net.julessteele.bananaclient.modules.movement.AirJump
import net.julessteele.bananaclient.modules.movement.Blink
import net.julessteele.bananaclient.modules.movement.Jesus
import net.julessteele.bananaclient.modules.render.Fullbright
import net.minecraft.client.MinecraftClient

object BananaClient: ClientModInitializer {

    private var modulesActivated = false

    override fun onInitializeClient() {

        Banana.logger.info("Hello from the ${Banana.NAME} Client Mod Initializer!")

        // Register modules
        ModuleManager.register(Fullbright())
        ModuleManager.register(Jesus())
        ModuleManager.register(HudList())
        ModuleManager.register(ClickGui())
        ModuleManager.register(AirJump())
        ModuleManager.register(Blink())

        // Register commands
        CommandManager.register(HelpCommand())
        CommandManager.register(ToggleCommand())
        CommandManager.register(SayCommand())
        CommandManager.register(ListModulesCommand())
        CommandManager.register(BindCommand())

        // Register SetModuleSettingCommand for all modules
        ModuleManager.getModules().forEach {
            CommandManager.register(SetModuleSettingCommand(it.name.replace(" ", "").lowercase()))
        }

        // Load module enabled states from last session
        ModuleManager.loadModules()

        // Hook into ticks and check if enabled on startup
        ClientTickEvents.END_CLIENT_TICK.register {
            checkForFirstEnable(it)
            ModuleManager.onTick()
        }

        // Hook into entity rendering in net/julessteele/bananaclient/mixin/client/EntityRendererMixin.java

        // Hook into HUD rendering in net/julessteele/bananaclient/mixin/client/InGameHudMixin.java

        // Print that initializing is finished
        Banana.logger.info("${Banana.NAME} has finished registering modules/commands and has attached hooks... Client INIT Finished!")
    }

    private fun checkForFirstEnable(client: MinecraftClient) {
        if (client.world != null && !modulesActivated) {
            modulesActivated = true
            ModuleManager.getModules().forEach { if (it.enabled) it.onEnable() }
            Banana.logger.info("fun checkForFirstEnable -> Activated saved modules after world load.")
        }
    }
}
