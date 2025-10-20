package net.julessteele.bananaclient.module

import net.julessteele.bananaclient.Banana
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.util.math.MatrixStack

object ModuleManager {

    private val modules = mutableListOf<Module>()

    fun register(module: Module) {
        modules.add(module)
    }

    /**
     * Gets all modules, module by name, or module by category. Does NOT accept both.
     */
    fun getModules(moduleName: String? = null, category: Module.Category? = null): List<Module> {
        return if (moduleName == null && category == null) {
            modules
        } else if (moduleName == null && category != null) {
            modules.filter { it.category == category }
        } else if (category == null) {
            modules.filter { it.name.equals(moduleName, ignoreCase = true) }
        } else {
            Banana.logger.error("Cannot get module from both name and category, please provide one.")
            return emptyList()
        }
    }

    fun getEnabledModules() = modules.filter { it.enabled }

    fun onTick() {

        // Check for keypress to toggle each module
        modules.forEach { module ->
            module.keybind.let { key ->
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

    fun onRenderHUD(context: DrawContext) {
        modules.filter { it.enabled }.forEach { it.onRenderHUD(context) }
    }
}