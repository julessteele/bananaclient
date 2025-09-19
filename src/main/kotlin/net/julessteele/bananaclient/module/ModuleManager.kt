package net.julessteele.bananaclient.module

import net.julessteele.bananaclient.Banana
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.math.MatrixStack

object ModuleManager {

    private val modules = mutableListOf<Module>()

    fun register(module: Module) {
        modules.add(module)
    }

    fun getModules(): List<Module> = modules

    fun getModuleByName(moduleName: String): Module? = modules.find { it.name.equals(moduleName, ignoreCase = true) }

    fun onTick() {

        // Check for keypress to toggle each module
        modules.forEach { module ->
            module.keybind?.let { key ->
                while (key.wasPressed()) {
                    module.toggle()
                    Banana.logger.info("Toggled ${module.name} -> ${module.enabled}")
                }
            }
        }

        // Run onTick function for each module
        modules.filter { it.enabled }.forEach { it.onTick() }
    }

    fun onRenderEntity(matrices: MatrixStack) {
        modules.filter { it.enabled }.forEach { it.onRenderEntity(matrices) }
    }

    fun onRenderHUD(mc: MinecraftClient, matrices: MatrixStack) {
        modules.filter { it.enabled }.forEach { it.onRenderHUD(mc, matrices) }
    }
}