
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

        if (sender !is Player) { // プレイヤー以外実行不可
            sender.sendMessage("${ChatColor.RED}プレイヤーのみ実行可能です")
            return true
        }
        if (args.size < 2) { // 情報が少ない場合
            return false
        }

        // タグ
        val tag = args[0]
        val senderPlayerChatTag = playerManager.acquisitionChatTag(sender)
        if (senderPlayerChatTag.contains(tag)) { // 指定したタグをプレイヤーが持っていた場合
            sendChatTags.add(tag)
        } else if (tag == "chat:all") { // chat:allを指定した場合 指定可能のタグを全て追加
            sendChatTags.addAll(senderPlayerChatTag)
        } else { // 持っていない または chatから始まったタグを持っていない場合
            sender.sendMessage("${ChatColor.RED}あなたは${tag}タグを指定することはできません")
            return true
        }

        var receivingCount = 0 // 送信人数
        // メッセージ
        val message = "${ChatColor.AQUA}[TagChat] ${ChatColor.WHITE}<${sender.displayName}> ${args.drop(1).joinToString(" ").replace("&","§")}"

        // 指定タグを持っているプレイヤーのみメッセージを送る
        for (onlinePlayer in Bukkit.getOnlinePlayers()) {
            if (onlinePlayer == sender) { // 送ったプレイヤー自身はスキップする
                continue
            }
            for (sendChatTag in sendChatTags) { // 指定されているタグをonlinePlayerが持っているか
                if (onlinePlayer.scoreboardTags.contains(sendChatTag)) {
                    onlinePlayer.sendMessage(message)
                    receivingCount ++
                    break
                }
            }
        }
        // 送信者にメッセージ送信(送信人数情報付き)
        sender.sendMessage(message + "${ChatColor.GREEN}\n(送信人数：${receivingCount}人)")

        return true
    }
}
