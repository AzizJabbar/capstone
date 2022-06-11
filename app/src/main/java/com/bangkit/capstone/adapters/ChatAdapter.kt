package com.bangkit.capstone.adapters

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.capstone.R
import com.bangkit.capstone.model.ChatModel
import com.bangkit.capstone.ui.ChatActivity
import me.relex.circleindicator.CircleIndicator3

class ChatAdapter(context: Context) : RecyclerView.Adapter<ChatAdapter.ViewHolder>(){
    private var context = context
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
        val form: LinearLayout = itemView.findViewById(R.id.form)
        val submitBtn: Button = itemView.findViewById(R.id.submitBtn)
        // Instantiate a ViewPager2 and a PagerAdapter.
        val viewPager: ViewPager2 = itemView.findViewById(R.id.pager)
        val pagerContainer: ConstraintLayout = itemView.findViewById(R.id.pager_container)
        val indicator: CircleIndicator3 = itemView.findViewById(R.id.indicator)

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
//                    viewHolderChatItemSelf.textViewDateTime.text = DateUtils.get
//                    viewHolderChatItemSelf.textViewDateTime.text = DateUtils.getRelativeTimeSpanString(chat.timestamp)
                    viewHolderChatItemSelf.textViewMessage.text = chat.text
                }
                2 -> {
                    val viewHolderChatBot = holder as ViewHolderChatItemBot
//                    viewHolderChatBot.textViewDateTime.text = DateUtils.getRelativeTimeSpanString(chat.timestamp)
                    viewHolderChatBot.textViewMessage.text = Html.fromHtml(chat.text)
                }
                3 -> {
                    val viewHolderChatBot = holder as ViewHolderChatItemBot
//                    viewHolderChatBot.textViewDateTime.text = DateUtils.getRelativeTimeSpanString(chat.timestamp)
                    viewHolderChatBot.textViewMessage.text = chat.text
                    viewHolderChatBot.form.visibility = View.VISIBLE
                    viewHolderChatBot.submitBtn.visibility = View.VISIBLE
                    val sarapan = holder.itemView.findViewById<CheckBox>(R.id.sarapan)
                    val makanSiang = holder.itemView.findViewById<CheckBox>(R.id.makanSiang)
                    val makanMalam = holder.itemView.findViewById<CheckBox>(R.id.makanMalam)
                    val snack = holder.itemView.findViewById<CheckBox>(R.id.snack)
//                    if(chat. isSubmitted){
//                        sarapan.isEnabled = false
//                        makanSiang.isEnabled = false
//                        makanMalam.isEnabled = false
//                        snack.isEnabled = false
//                        viewHolderChatBot.submitBtn.visibility = View.GONE
//                    }
                    holder.itemView.findViewById<Button>(R.id.submitBtn).setOnClickListener {

//                        viewHolderChatBot.form.visibility = View.GONE
//                        viewHolderChatBot.submitBtn.visibility = View.GONE
//                        sarapan.visibility = View.GONE
//                        makanSiang.visibility = View.GONE
//                        makanMalam.visibility = View.GONE
//                        snack.visibility = View.GONE

                        (context as ChatActivity).submitTime(sarapan.isChecked, makanSiang.isChecked, makanMalam.isChecked, snack.isChecked)

                    }
                }
                4 -> {
                    val viewHolderChatBot = holder as ViewHolderChatItemBot
//                    viewHolderChatBot.textViewDateTime.text = DateUtils.getRelativeTimeSpanString(chat.timestamp)
                    viewHolderChatBot.textViewMessage.text = Html.fromHtml(chat.text)

                    viewHolderChatBot.pagerContainer.visibility = View.VISIBLE
                    // The pager adapter, which provides the pages to the view pager widget.
                    val pagerAdapter = ScreenSlidePagerAdapter(context as FragmentActivity, chat.data)
                    viewHolderChatBot.viewPager.adapter = pagerAdapter

                    viewHolderChatBot.indicator.setViewPager(viewHolderChatBot.viewPager)


                }
            }
        }
    }
}