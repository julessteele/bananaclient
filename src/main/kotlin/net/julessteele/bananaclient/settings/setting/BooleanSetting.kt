package net.julessteele.bananaclient.settings.setting

import net.julessteele.bananaclient.settings.Setting
import net.julessteele.bananaclient.settings.SettingType

class BooleanSetting(name: String, value: Boolean): Setting(name, value) {

    init {
        settingType = SettingType.BOOLEAN
    }
}