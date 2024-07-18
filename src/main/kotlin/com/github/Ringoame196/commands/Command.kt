
package com.github.Ringoame196

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Command : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val playerManager = PlayerManager()
        val sendChatTags = mutableListOf<String>()

        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}プレイヤーのみ実行可能です")
            return true
        }
        if (args.size < 2) { // 情報が少ない場合
            return false
        }

        // タグ
        val tag = args[0]
        val senderPlayerChatTag = playerManager.acquisitionChatTag(sender)
        if (senderPlayerChatTag.contains(tag)) {
            sendChatTags.add(tag)
        } else if (tag == "chat:all") {
            sendChatTags.addAll(senderPlayerChatTag)
        } else {
            sender.sendMessage("${ChatColor.RED}あなたは${tag}タグを持っていません")
            return true
        }

        var receivingCount = 0
        // メッセージ
        val message = "${ChatColor.AQUA}[TagChat] ${ChatColor.WHITE}<${sender.displayName}> ${args.drop(1).joinToString(" ").replace("&","§")}"

        // 指定タグを持っているプレイヤーのみメッセージを送る
        for (onlinePlayer in Bukkit.getOnlinePlayers()) {
            if (onlinePlayer == sender) { // 送ったプレイヤー自身はスキップする
                continue
            }
            for (sendChatTag in sendChatTags) {
                if (onlinePlayer.scoreboardTags.contains(sendChatTag)) {
                    onlinePlayer.sendMessage(message)
                    receivingCount ++
                    break
                }
            }
        }
        // 送信者にメッセージ送信
        sender.sendMessage(message + "${ChatColor.GREEN}\n(送信人数：${receivingCount}人)")

        return true
    }
}
