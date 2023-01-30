package com.jesus.villa.technicaltest.presentation.transference.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jesus.villa.technicaltest.R
import com.jesus.villa.technicaltest.presentation.dashboard.adapter.TransactionAdapter
import com.jesus.villa.technicaltest.presentation.dashboard.model.ActivityTransaction
import com.jesus.villa.technicaltest.presentation.transference.model.ContactTransference
import com.jesus.villa.technicaltest.util.getTwoCharacteres


/**
 * Created by Jesús Villa on 30/01/23
 */
class ContactAdapter (val context: Context, items:List<ContactTransference>, var listener: OnClickListener): RecyclerView.Adapter<ContactAdapter.MyViewHolder>() {

    var items:List<ContactTransference>? = null

    init {
        this.items = items
    }

    class MyViewHolder(itemView : View, listener: OnClickListener): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var listener: OnClickListener? = null
        var avatarTextView: TextView
        var titleContact: TextView
        var detailContact: TextView

        init {
            avatarTextView = itemView.findViewById(R.id.avatarTextView) as TextView
            titleContact = itemView.findViewById(R.id.titleContact) as TextView
            detailContact = itemView.findViewById(R.id.detailContact) as TextView

            this.listener = listener
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!,adapterPosition)

        }

        fun bind(item: ContactTransference, position: Int, context: Context) {
            avatarTextView.text = item.fullName.getTwoCharacteres()
            titleContact.text = item.fullName
            detailContact.text = item.detail
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return MyViewHolder(vista, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items!![position], position, context)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    interface OnClickListener {
        fun onClick(vista: View, index:Int)
    }
}