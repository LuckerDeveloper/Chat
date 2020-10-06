package study.kotlin.anonmoscowchat.adapter

import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.message_item.view.*
import study.kotlin.anonmoscowchat.messages.Message
import study.kotlin.anonmoscowchat.R
import study.kotlin.anonmoscowchat.commons.constants.ViewTypeConstants
import java.util.*

class MessageAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var delegateAdapters =
        SparseArrayCompat<ViewTypeDelegateAdapter>()

    private var items: MutableList<Message> = mutableListOf()

    init {
        delegateAdapters.put(ViewTypeConstants.MY_MESSAGE, MyMessageDelegateAdapter())
        delegateAdapters.put(ViewTypeConstants.OTHER_MESSAGE, OtherMessageDelegateAdapter())
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MessageViewHolder(viewGroup)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(items[position].getViewTypes())?.bind(holder)
        holder as MessageViewHolder
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getViewTypes()
    }

    fun updateMessageList(list: MutableList<Message>){
        items = list
        notifyDataSetChanged()
    }

    fun addMessage(message: Message){
        items.add(message)
        notifyDataSetChanged()
    }

    fun clearData(){
        items.clear()
    }

    class MessageViewHolder(p0: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater
        .from(p0.context)
        .inflate(R.layout.message_item, p0, false)){

        fun bind(item: Message) = with(itemView){
            message_text.text = item.text
            message_time.text = item.time?.let { Date(it).toString() }
        }
    }
}