package net.julessteele.bananaclient.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File

object FileUtil {

    val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    val banana_clickgui_config_file = File("config/banana_clickgui.json")
    val banana_module_config_file = File("config/banana_module_config.json")
}