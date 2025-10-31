package net.julessteele.bananaclient.settings.setting

import net.julessteele.bananaclient.settings.Setting
import net.julessteele.bananaclient.settings.SettingType

class ModeSetting(name: String, value: String, shouldDisplay: Boolean = false): Setting(name, value, shouldDisplay) {

    val modeList = mutableListOf<String>()

    init {
        settingType = SettingType.MODE
    }
}