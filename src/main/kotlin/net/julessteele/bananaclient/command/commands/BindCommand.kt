package net.julessteele.bananaclient.command.commands

import net.julessteele.bananaclient.command.Command
import net.julessteele.bananaclient.module.ModuleManager
import net.julessteele.bananaclient.util.ChatUtil
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil

class BindCommand: Command("bind", "Allows the user to bind a key to a module with a command.", ".bind <module> <key>") {

    override fun execute(args: List<String>) {

        if (args.size != 2) {
            ChatUtil.sendClientMsg("Invalid number of arguments, please try again.")
            return
        }

        // Get name of module and keybind
        val moduleName = args[0]
        val keyBindName = args[1]

        // Get module
        val module = ModuleManager.getModule(moduleName, shouldSendErrMsgInChat = true) ?: return

        // Get keybind and reassign
        getKeyAsInt(keyBindName)?.let {
            // Change keybind
            module.keybind.setBoundKey(InputUtil.Type.KEYSYM.createFromCode(it))
            // Rebuild binding map
            KeyBinding.updateKeysByCode()
            client.options.write()
            // Send client chat feedback
            ChatUtil.sendClientMsg("Successfully bound key \"$keyBindName\" to module \"${module.name}\"")
            return
        }

        // If no key found (I.E. getKeyInput returns null and let is skipped)
        ChatUtil.sendClientMsg("No valid key found for keybind \"$keyBindName\"")
    }

    private fun getKeyAsInt(k: String): Int? {
        when (k.uppercase()) {
            "0" -> return InputUtil.GLFW_KEY_0
            "1" -> return InputUtil.GLFW_KEY_1
            "2" -> return InputUtil.GLFW_KEY_2
            "3" -> return InputUtil.GLFW_KEY_3
            "4" -> return InputUtil.GLFW_KEY_4
            "5" -> return InputUtil.GLFW_KEY_5
            "6" -> return InputUtil.GLFW_KEY_6
            "7" -> return InputUtil.GLFW_KEY_7
            "8" -> return InputUtil.GLFW_KEY_8
            "9" -> return InputUtil.GLFW_KEY_9
            "A" -> return InputUtil.GLFW_KEY_A
            "B" -> return InputUtil.GLFW_KEY_B
            "C" -> return InputUtil.GLFW_KEY_C
            "D" -> return InputUtil.GLFW_KEY_D
            "E" -> return InputUtil.GLFW_KEY_E
            "F" -> return InputUtil.GLFW_KEY_F
            "G" -> return InputUtil.GLFW_KEY_G
            "H" -> return InputUtil.GLFW_KEY_H
            "I" -> return InputUtil.GLFW_KEY_I
            "J" -> return InputUtil.GLFW_KEY_J
            "K" -> return InputUtil.GLFW_KEY_K
            "L" -> return InputUtil.GLFW_KEY_L
            "M" -> return InputUtil.GLFW_KEY_M
            "N" -> return InputUtil.GLFW_KEY_N
            "O" -> return InputUtil.GLFW_KEY_O
            "P" -> return InputUtil.GLFW_KEY_P
            "Q" -> return InputUtil.GLFW_KEY_Q
            "R" -> return InputUtil.GLFW_KEY_R
            "S" -> return InputUtil.GLFW_KEY_S
            "T" -> return InputUtil.GLFW_KEY_T
            "U" -> return InputUtil.GLFW_KEY_U
            "V" -> return InputUtil.GLFW_KEY_V
            "W" -> return InputUtil.GLFW_KEY_W
            "X" -> return InputUtil.GLFW_KEY_X
            "Y" -> return InputUtil.GLFW_KEY_Y
            "Z" -> return InputUtil.GLFW_KEY_Z
            "F1" -> return InputUtil.GLFW_KEY_F1
            "F2" -> return InputUtil.GLFW_KEY_F2
            "F3" -> return InputUtil.GLFW_KEY_F3
            "F4" -> return InputUtil.GLFW_KEY_F4
            "F5" -> return InputUtil.GLFW_KEY_F5
            "F6" -> return InputUtil.GLFW_KEY_F6
            "F7" -> return InputUtil.GLFW_KEY_F7
            "F8" -> return InputUtil.GLFW_KEY_F8
            "F9" -> return InputUtil.GLFW_KEY_F9
            "F10" -> return InputUtil.GLFW_KEY_F10
            "F11" -> return InputUtil.GLFW_KEY_F11
            "F12" -> return InputUtil.GLFW_KEY_F12
            "TAB" -> return InputUtil.GLFW_KEY_TAB
            "CAPSLOCK" -> return InputUtil.GLFW_KEY_CAPS_LOCK
            "SHIFT" -> return InputUtil.GLFW_KEY_LEFT_SHIFT
            "CONTROL" -> return InputUtil.GLFW_KEY_LEFT_CONTROL
            "MINUS" -> return InputUtil.GLFW_KEY_MINUS
            "EQUAL" -> return InputUtil.GLFW_KEY_EQUAL
            "LBRACKET" -> return InputUtil.GLFW_KEY_LEFT_BRACKET
            "RBRACKET" -> return InputUtil.GLFW_KEY_RIGHT_SHIFT
            "BACKSLASH" -> return InputUtil.GLFW_KEY_BACKSLASH
            "SEMICOLON" -> return InputUtil.GLFW_KEY_SEMICOLON
            "APOSTROPHE" -> return InputUtil.GLFW_KEY_APOSTROPHE
            "COMMA" -> return InputUtil.GLFW_KEY_COMMA
            "PERIOD" -> return InputUtil.GLFW_KEY_PERIOD
            "SLASH" -> return InputUtil.GLFW_KEY_SLASH
            "RETURN" -> return InputUtil.GLFW_KEY_ENTER
            "RSHIFT" -> return InputUtil.GLFW_KEY_RIGHT_SHIFT
        }
        return null
    }
}