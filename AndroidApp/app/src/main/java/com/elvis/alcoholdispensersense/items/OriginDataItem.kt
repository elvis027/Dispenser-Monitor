package com.elvis.alcoholdispensersense.items

import com.elvis.alcoholdispensersense.R
import com.elvis.alcoholdispensersense.models.OriginData
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.origin_data_row.view.*

class OriginDataItem(val originData: OriginData?): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.id_textview.text = originData?.id.toString()
        viewHolder.itemView.macaddr_textview.text = originData?.macaddr.toString()
        viewHolder.itemView.data_textview.text = originData?.data
        viewHolder.itemView.lat_textview.text = originData?.lat
        viewHolder.itemView.lng_textview.text = originData?.lng
        viewHolder.itemView.created_at_textview.text = originData?.created_at
        viewHolder.itemView.updated_at_textview.text = originData?.updated_at
    }

    override fun getLayout(): Int {
        return R.layout.origin_data_row
    }
}