package id.scode.kotlincrud

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.scode.oprekCrud.DataOprek
import kotlinx.android.synthetic.main.oprek_view.view.*

/**
 * Created by Dani on 1/2/2019.
 * Yogi Arif Widodo
 */

class RvAdapterOprek(private val context: Context, private val arrayList: ArrayList<DataOprek>) : RecyclerView.Adapter<RvAdapterOprek.Holder>()
{
    class Holder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.oprek_view, parent,false))
    }

    override fun getItemCount(): Int = arrayList.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        //==========
        holder.view.oprekTes.text = "j"+arrayList[position].gender


    }

}