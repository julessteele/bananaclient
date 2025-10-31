package net.julessteele.bananaclient.settings

open class Setting(val name: String, var value: Any, var shouldDisplay: Boolean) {

    open var settingType: SettingType? = null

    open fun render() { }
}