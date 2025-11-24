package net.julessteele.bananaclient.clickgui

import com.google.gson.reflect.TypeToken
import net.julessteele.bananaclient.util.FileUtil

object ClickGuiConfig {

    private var panelPositions: MutableMap<String, Pair<Double, Double>> = mutableMapOf()

    fun load() {
        if (!FileUtil.clickGuiCfgFile.exists()) return
        val type = object: TypeToken<Map<String, Pair<Double, Double>>>() {}.type
        panelPositions = FileUtil.gson.fromJson(FileUtil.clickGuiCfgFile.readText(), type) ?: mutableMapOf()
    }

    fun save() {
        FileUtil.clickGuiCfgFile.parentFile.mkdirs()
        FileUtil.clickGuiCfgFile.writeText(FileUtil.gson.toJson(panelPositions))
    }

    fun getPosition(categoryName: String): Pair<Double, Double>? {
        return panelPositions[categoryName]
    }

    fun setPosition(categoryName: String, x: Double, y: Double) {
        panelPositions[categoryName] = Pair(x, y)
        save()
    }
}