package net.julessteele.bananaclient.commands

import net.julessteele.bananaclient.command.Command
import net.julessteele.bananaclient.util.ChatUtil
import net.julessteele.bananaclient.util.ChatUtil.sendUseCaseClientMsg

class SayCommand: Command("say", "Says anything in chat.", ".say <message>") {

    override fun execute(args: List<String>) {

        if (args.isEmpty()) {
            sendUseCaseClientMsg(usage)
        } else {
            ChatUtil.sendSafeServerMsg(args.joinToString(" "))
        }
    }
}