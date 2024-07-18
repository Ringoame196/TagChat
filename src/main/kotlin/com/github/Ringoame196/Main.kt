package com.github.Ringoame196

import com.github.Ringoame196.commands.TabCompleter
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        super.onEnable()
        // コマンド関係
        val tagChatCommand = getCommand("tagchat")
        tagChatCommand!!.setExecutor(Command())
        tagChatCommand.tabCompleter = TabCompleter()
    }
}
