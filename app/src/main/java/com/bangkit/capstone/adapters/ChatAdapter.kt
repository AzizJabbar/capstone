package com.bangkit.capstone.adapters

import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.R
import com.bangkit.capstone.model.ChatModel

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ViewHolder>(){

    private val listChat = mutableListOf<ChatModel>()
    private val listViewType = mutableListOf<Int>()

    fun submit(list:List<ChatModel>){
        listChat.clear()
        listViewType.clear()
        listChat.addAll(list)
        listChat.forEach {
            listViewType.add(it.type)
        }
        notifyDataSetChanged()
    }


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
                    viewHolderChatItemSelf.textViewDateTime.text = DateUtils.getRelativeTimeSpanString(chat.timestamp)
                    viewHolderChatItemSelf.textViewMessage.text = chat.text
                }
                else -> {
                    val viewHolderChatBot = holder as ViewHolderChatItemBot
                    viewHolderChatBot.textViewDateTime.text = DateUtils.getRelativeTimeSpanString(chat.timestamp)
                    viewHolderChatBot.textViewMessage.text = chat.text
                }
            }
        }
    }
}