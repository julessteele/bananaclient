package net.julessteele.bananaclient.clickgui

import com.google.gson.reflect.TypeToken
import net.julessteele.bananaclient.module.Category
import net.julessteele.bananaclient.util.FileUtil

object ClickGuiConfig {

    private var panelPositions: MutableMap<Category, Pair<Double, Double>> = mutableMapOf()

    fun load() {
        if (!FileUtil.clickGuiCfgFile.exists()) return
        val type = object: TypeToken<Map<Category, Pair<Double, Double>>>() {}.type
        panelPositions = FileUtil.gson.fromJson(FileUtil.clickGuiCfgFile.readText(), type) ?: mutableMapOf()
    }

    fun save() {
        FileUtil.clickGuiCfgFile.parentFile.mkdirs()
        FileUtil.clickGuiCfgFile.writeText(FileUtil.gson.toJson(panelPositions))
    }

    fun getPosition(category: Category): Pair<Double, Double>? {
        return panelPositions[category]
    }

    fun setPosition(category: Category, x: Double, y: Double) {
        panelPositions[category] = Pair(x, y)
        save()
    }
}