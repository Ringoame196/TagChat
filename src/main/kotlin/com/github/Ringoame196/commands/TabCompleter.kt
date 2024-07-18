package com.github.Ringoame196.commands

import com.github.Ringoame196.PlayerManager
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class TabCompleter : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        if (sender !is Player) { // プレイヤー以外は動かないように
            return mutableListOf("※このコマンドはプレイヤー以外使用禁止です")
        }

        return when (args.size) {
            1 -> putChatTag(sender) // タグの候補を出す
            else -> mutableListOf("[メッセージ]")
        }
    }
    private fun putChatTag(player: Player): MutableList<String> {
        // 候補のタグを出す
        val playerManager = PlayerManager()
        val tagList = playerManager.acquisitionChatTag(player) // プレイヤーが指定可能なタグ一覧
        if (tagList.size == 0) { // ない場合
            tagList.add("[送信できるタグがありません]")
        } else { // ある場合
            tagList.add("[タグ]")
            tagList.add("chat:all")
        }
        return tagList
    }
}
