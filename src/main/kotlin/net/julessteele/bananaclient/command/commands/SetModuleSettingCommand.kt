package net.julessteele.bananaclient.command.commands

import net.julessteele.bananaclient.Banana
import net.julessteele.bananaclient.command.Command
import net.julessteele.bananaclient.module.ModuleManager
import net.julessteele.bananaclient.settings.SettingType
import net.julessteele.bananaclient.settings.setting.NumberSetting
import net.julessteele.bananaclient.util.ChatUtil

class SetModuleSettingCommand(name: String): Command(name, "Provide a module, setting without any spaces, and value in order to adjust a setting on a module.", ".<module> <setting> <value>") {

    override fun execute(args: List<String>) {

        if (args.size != 2) {
            ChatUtil.sendClientMsg("Invalid number of arguments. Please use the following template as an example: $usage")
            return
        }

        val s1 = args[0]
        val v1 = args[1]

        // Find module, cancel execute and send error message if module is nonexistent
        val module = ModuleManager.getModule(name, shouldSendErrMsgInChat = true) ?: return

        // Find setting, cancel and send err msg if nonexistent
        val setting = module.findSetting(s1, shouldSendErrorMsgInChat = true) ?: return

        var shouldSetVal = true

        // Check if the setting is a number setting and is out of bounds
        if (setting.settingType == SettingType.NUMBER) {

            setting as NumberSetting

            if (v1.toDouble() > setting.max) {
                ChatUtil.sendClientMsg("The value \"$v1\" is over the allowed max for setting \"$s1\"")
                shouldSetVal = false
            }

            if (v1.toDouble() < setting.min) {
                ChatUtil.sendClientMsg("The value \"$v1\" is under the allowed min for setting \"$s1\"")
                shouldSetVal = false
            }
        }

        // Set value if it's valid
        if (shouldSetVal) {

            val pv = parseValue(v1)
            if (pv is Boolean || pv is Double)
                setting.value = pv
            else if (pv is String) {
                if (module.findSetting(pv) != null)
                    setting.value = pv
                else {
                    ChatUtil.sendClientMsg("Unable to find mode \"$pv\"")
                    return
                }
            }
            else {
                val s2 = "Unable to parse value $v1"
                ChatUtil.sendClientMsg(s2)
                Banana.logger.error(s2)
                return
            }

            // Provide feedback
            ChatUtil.sendClientMsg("You just changed the setting \"$s1\", on module \"$name\", to value \"$v1\"")
        }
    }

    private fun parseValue(value: String): Any {
        // Handles if value is a boolean
        if (value.equals("true", ignoreCase = true) || value.equals("false", ignoreCase = true))
            return value.toBoolean()
        // Handles if value is a number
        else if (Character.isDigit(value[0])) {
            return value.chars().filter { Character.isDigit(it) }.mapToObj { it.toChar() }.toString()
        }
        // Else return value -> modes are strings
        return value.chars().filter { Character.isLetterOrDigit(it) }.mapToObj { it.toChar() }.toString()
    }
}