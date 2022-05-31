package com.bangkit.capstone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.R
import com.bangkit.capstone.model.ChatModel

class ChatAdapter constructor(private val listViewType: List<Int>, private val listChat: List<ChatModel>): RecyclerView.Adapter<ChatAdapter.ViewHolder>(){

    open class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class ViewHolderChatItemBot constructor(itemView: View) : ViewHolder(itemView) {

        val textViewDateTime: TextView = itemView.findViewById(R.id.text_view_date_time_item_layout_chat_bot)
        val textViewMessage: TextView = itemView.findViewById(R.id.text_view_message_item_layout_chat_bot)

    }

    inner class ViewHolderChatItemSelf constructor(itemView: View) : ViewHolder(itemView) {

        val textViewDateTime: TextView = itemView.findViewById(R.id.text_view_date_time_item_layout_chat_self)
        val textViewMessage: TextView = itemView.findViewById(R.id.text_view_message_item_layout_chat_self)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return when (viewType) {
            1 -> {
                val view = layoutInflater.inflate(R.layout.item_layout_chat_my_self, null)
                ViewHolderChatItemSelf(view)
            }
            else -> {
                val view = layoutInflater.inflate(R.layout.item_layout_chat_bot, null)
                ViewHolderChatItemBot(view)
            }
        }
    }

    override fun getItemCount(): Int = listChat.size
    override fun getItemViewType(position: Int): Int = listViewType[position]

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = listChat[position]
        listViewType[position].let {
            when (it) {
                1 -> {
                    val viewHolderChatItemSelf = holder as ViewHolderChatItemSelf
                    viewHolderChatItemSelf.textViewDateTime.text = chat.sent
                    viewHolderChatItemSelf.textViewMessage.text = chat.text
                }
                else -> {
                    val viewHolderChatBot = holder as ViewHolderChatItemBot
                    viewHolderChatBot.textViewDateTime.text = chat.sent
                    viewHolderChatBot.textViewMessage.text = chat.text
                }
            }
        }
    }
}