package net.julessteele.bananaclient.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File

object FileUtil {

    val gson: Gson = GsonBuilder().create()
    val clickGuiCfgFile = File("config/bananaclient/banana_clickgui.json")
    val moduleCfgFile = File("config/bananaclient/banana_module_config.json")
}