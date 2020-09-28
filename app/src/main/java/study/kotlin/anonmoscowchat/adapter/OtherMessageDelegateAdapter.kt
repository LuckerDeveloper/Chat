package study.kotlin.anonmoscowchat.adapter

import android.view.Gravity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.message_item.view.*
import study.kotlin.anonmoscowchat.R

class OtherMessageDelegateAdapter: ViewTypeDelegateAdapter {

    override fun bind(p0: RecyclerView.ViewHolder) {
        p0.itemView.item_of_recyclerview.gravity = Gravity.START
        p0.itemView.container_for_message.setBackgroundResource(R.drawable.other_message)
        val constraintSet = ConstraintSet()
        constraintSet.clone(p0.itemView.container_for_message)
        constraintSet.connect(p0.itemView.message_text.id, ConstraintSet.START, p0.itemView.container_for_message.id, ConstraintSet.START)
        constraintSet.connect(p0.itemView.message_time.id, ConstraintSet.START, p0.itemView.container_for_message.id, ConstraintSet.START)
        constraintSet.applyTo(p0.itemView.container_for_message)
    }
}