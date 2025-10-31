package net.julessteele.bananaclient.settings.setting

import net.julessteele.bananaclient.settings.Setting
import net.julessteele.bananaclient.settings.SettingType

class NumberSetting(name: String, value: Double, val min: Double, val max: Double, shouldDisplay: Boolean = false): Setting(name, value, shouldDisplay) {

    init {
        settingType = SettingType.NUMBER
    }
}