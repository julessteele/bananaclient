package net.julessteele.bananaclient.module

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import net.julessteele.bananaclient.Banana
import net.julessteele.bananaclient.util.FileUtil
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
    fun getModules(moduleName: String? = null, category: Category? = null): List<Module> {
        return if (moduleName == null && category == null) {
            modules
        } else if (moduleName == null && category != null) {
            modules.filter { it.category == category }
        } else if (category == null) {
            modules.filter { it.name.equals(moduleName, ignoreCase = true) }
        } else {
            Banana.logger.warn("Cannot get module from both name and category, please provide one.")
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

    fun loadModules() {
        if (!FileUtil.banana_module_config_file.exists()) return

        try {

            val content = FileUtil.banana_module_config_file.readText()
            val obj: JsonObject = JsonParser.parseString(content).asJsonObject

            fun coerceBoolean(jsonElement: JsonElement?): Boolean? {
                if (jsonElement == null || jsonElement.isJsonNull) return null
                return try {
                    when {
                        jsonElement.isJsonPrimitive -> {
                            val jsonPrimitive = jsonElement.asJsonPrimitive
                            when {
                                jsonPrimitive.isBoolean -> jsonPrimitive.asBoolean
                                jsonPrimitive.isNumber -> {
                                    val n = jsonPrimitive.asNumber.toDouble()
                                    n != 0.0
                                }
                                jsonPrimitive.isString -> {
                                    val s = jsonPrimitive.asString.trim().lowercase()
                                    when (s) {
                                        "true", "yes", "1", "on"  -> true
                                        "false", "no", "0", "off" -> false
                                        else -> null
                                    }
                                }
                                else -> null
                            }
                        }
                        else -> null
                    }
                } catch (_: Exception) {
                    null
                }
            }

            modules.forEach {
                val element = obj.get(it.name)
                val elementBool = coerceBoolean(element)
                if (elementBool != null) {
                    it.enabled = elementBool
                    if (it.enabled) it.onEnable() else it.onDisable()
                } else {
                    // value missing or not coercible -> leave default or log
                    println("Module config: couldn't parse value for '${it.name}' (raw=${element}); keeping default=${it.enabled}")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun saveModules() {
        try {
            if (!FileUtil.banana_module_config_file.parentFile.exists()) FileUtil.banana_module_config_file.parentFile.mkdirs()
            val map = modules.associate { it.name to it.enabled }
            FileUtil.banana_module_config_file.writeText(FileUtil.gson.toJson(map))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}