package com.github.Ringoame196

import org.bukkit.entity.Player

class PlayerManager {
    fun acquisitionChatTag(player: Player): MutableList<String> {
        val chatTagMark = "chat"
        val chatTagList = mutableListOf<String>()

        for (tag in player.scoreboardTags) { // プレイヤーのタグ取得
            if (tag.startsWith(chatTagMark)) { // chatから始まるタグをchatTagListに追加する
                chatTagList.add(tag)
            }
        }
        return chatTagList
    }
}
