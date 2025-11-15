package net.julessteele.bananaclient.settings.setting

import net.julessteele.bananaclient.settings.Setting
import net.julessteele.bananaclient.settings.SettingType

class ModeSetting(name: String, value: String): Setting(name, SettingType.MODE, value) {

    val modeList = mutableListOf<String>()
}