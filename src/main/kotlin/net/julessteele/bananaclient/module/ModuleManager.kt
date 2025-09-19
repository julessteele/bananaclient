package net.julessteele.bananaclient.module

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

        modules.forEach { module ->
            module.keybind?.let { key ->
                while (key.wasPressed()) {
                    module.toggle()
                    println("Toggled ${module.name} -> ${module.enabled}")
                }
            }
        }

        modules.filter { it.enabled }.forEach { it.onTick() }
    }

    fun onRenderEntity(matrices: MatrixStack) {
        modules.filter { it.enabled }.forEach { it.onRenderEntity(matrices) }
    }

    fun onRenderHUD(mc: MinecraftClient, matrices: MatrixStack) {
        modules.filter { it.enabled }.forEach { it.onRenderHUD(mc, matrices) }
    }
}