package net.julessteele.bananaclient.clickgui

import com.google.gson.reflect.TypeToken
import net.julessteele.bananaclient.util.FileUtil

object ClickGuiConfig {

    private var panel_positions: MutableMap<String, Pair<Double, Double>> = mutableMapOf()

    fun load() {
        if (!FileUtil.banana_clickgui_config_file.exists()) return
        val type = object: TypeToken<Map<String, Pair<Double, Double>>>() {}.type
        panel_positions = FileUtil.gson.fromJson(FileUtil.banana_clickgui_config_file.readText(), type) ?: mutableMapOf()
    }

    fun save() {
        FileUtil.banana_clickgui_config_file.parentFile.mkdirs()
        FileUtil.banana_clickgui_config_file.writeText(FileUtil.gson.toJson(panel_positions))
    }

    fun getPosition(categoryName: String): Pair<Double, Double>? {
        return panel_positions[categoryName]
    }

    fun setPosition(categoryName: String, x: Double, y: Double) {
        panel_positions[categoryName] = Pair(x, y)
        save()
    }
}