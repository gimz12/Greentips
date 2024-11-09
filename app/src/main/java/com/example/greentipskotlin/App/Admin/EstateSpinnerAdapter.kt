package com.example.greentipskotlin.App.Admin

import android.content.Context
import android.widget.ArrayAdapter
import com.example.greentipskotlin.App.Model.Estate

class EstateSpinnerAdapter(context: Context, estates: List<Estate>) : ArrayAdapter<Estate>(context, android.R.layout.simple_spinner_item, estates) {
    override fun getDropDownView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
        val view = super.getDropDownView(position, convertView, parent)
        // Custom behavior, if needed
        return view
    }

    override fun getItem(position: Int): Estate {
        return super.getItem(position)!!
    }

    override fun getItemId(position: Int): Long {
        return super.getItem(position)?.estateId?.toLong() ?: 0L
    }

    override fun getCount(): Int {
        return super.getCount()
    }
}
