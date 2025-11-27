package net.julessteele.bananaclient.module

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import net.julessteele.bananaclient.Banana
import net.julessteele.bananaclient.util.ChatUtil
import net.julessteele.bananaclient.util.FileUtil
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.util.math.MatrixStack
import java.io.FileOutputStream

object ModuleManager {

    private val modules = mutableListOf<Module>()

    fun register(module: Module) {
        modules.add(module)
    }

    fun getModule(name: String, shouldSendErrMsgInChat: Boolean = false): Module? {
        val m = modules.find {
            it.name.equals(name, ignoreCase = true) || it.name.equals(
                name.replace(" ", "").lowercase(),
                ignoreCase = true
            )
        }

        if (m == null) {
            val s = "Module $name not found..."
            Banana.logger.error(s)

            if (shouldSendErrMsgInChat) ChatUtil.sendClientMsg(s)
        }

        return m
    }

    fun getModules(category: Category? = null): List<Module> {
        return if (category == null) {
            this.modules
        } else {
            this.modules.filter { it.category == category }
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

    fun onRenderEntity(matrices: MatrixStack) = modules.filter { it.enabled }.forEach { it.onRenderEntity(matrices) }

    fun onRenderHUD(context: DrawContext) = modules.filter { it.enabled }.forEach { it.onRenderHUD(context) }

    fun loadModules() {
        if (!FileUtil.moduleCfgFile.exists()) return

        try {

            val fileContent = FileUtil.moduleCfgFile.readText()
            val jsonObject: JsonObject = JsonParser.parseString(fileContent).asJsonObject

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
                val element = jsonObject.get(it.name)
                val elementBool = coerceBoolean(element)
                if (elementBool != null) {
                    it.enabled = elementBool
                } else {
                    Banana.logger.error("Module config: couldn't parse value for '${it.name}' (raw=${element}); keeping default=${it.enabled}")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun saveModules() {
        try {
            if (!FileUtil.moduleCfgFile.parentFile.exists()) FileUtil.moduleCfgFile.parentFile.mkdirs()
            val json = FileUtil.gson.toJson(modules.associate { it.name to it.enabled })
            FileOutputStream(FileUtil.moduleCfgFile).use {
                it.write(json.toByteArray())
                it.fd.sync() // Flush to disk immediately
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}