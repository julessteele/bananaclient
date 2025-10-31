package net.julessteele.bananaclient.settings.setting

import net.julessteele.bananaclient.settings.Setting
import net.julessteele.bananaclient.settings.SettingType

class BooleanSetting(name: String, value: Boolean, shouldDisplay: Boolean = false): Setting(name, value, shouldDisplay) {

    init {
        settingType = SettingType.BOOLEAN
    }
}