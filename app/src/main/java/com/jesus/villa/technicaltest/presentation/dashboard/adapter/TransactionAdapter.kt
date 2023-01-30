package com.jesus.villa.technicaltest.presentation.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jesus.villa.technicaltest.R
import com.jesus.villa.technicaltest.presentation.dashboard.model.ActivityTransaction


/**
 * Created by Jesús Villa on 30/01/23
 */
class TransactionAdapter (val context: Context, items:List<ActivityTransaction>, var listener: OnClickListener): RecyclerView.Adapter<TransactionAdapter.MyViewHolder>() {

    var items:List<ActivityTransaction>? = null

    init {
        this.items = items
    }

    class MyViewHolder(itemView : View, listener: OnClickListener): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var listener: OnClickListener? = null
        var transactionView: ImageView
        var titleTransaction: TextView
        var detailTransaction: TextView
        var amountTransaction: TextView
        var dateTransaction: TextView

        init {
            transactionView = itemView.findViewById(R.id.transactionView) as ImageView
            titleTransaction = itemView.findViewById(R.id.titleTransaction) as TextView
            detailTransaction = itemView.findViewById(R.id.detailTransaction) as TextView
            amountTransaction = itemView.findViewById(R.id.amountTransaction) as TextView
            dateTransaction = itemView.findViewById(R.id.dateTransaction) as TextView

            this.listener = listener
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!,adapterPosition)

        }

        fun bind(item: ActivityTransaction, position: Int, context: Context) {
            val incomeBg = if(item.income) R.drawable.ic_transaction_in else R.drawable.ic_transaction_out
            val incomeColor = if(item.income) R.color.income_in else R.color.app

            transactionView.setImageResource(incomeBg)
            titleTransaction.text = item.title
            detailTransaction.text = item.detail
            amountTransaction.setTextColor(context.resources.getColor(incomeColor,null))
            amountTransaction.text = item.amount
            dateTransaction.text = item.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction_activity, parent, false)
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