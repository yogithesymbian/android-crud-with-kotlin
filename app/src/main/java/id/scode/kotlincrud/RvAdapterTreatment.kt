package id.scode.kotlincrud

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.oprek_view.view.*
import kotlinx.android.synthetic.main.student_list.view.*
import kotlinx.android.synthetic.main.treatment_data.view.*

/**
 * Created by Dani on 3/9/2019.
 * Yogi Arif Widodo
 */

class RvAdapterTreatment(private val context: Context, private val arrayList: ArrayList<DataTreatment>) : androidx.recyclerview.widget.RecyclerView.Adapter<RvAdapterTreatment.Holder>()
{
    class Holder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.treatment_data, parent,false))
    }

    override fun getItemCount(): Int = arrayList.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.txt_tc.text = arrayList[position].treat_code
        holder.view.txt_td.text = arrayList[position].treat_date
        holder.view.txt_di.text = arrayList[position].doc_id
        holder.view.txt_pi.text = arrayList[position].pat_id
//        holder.view.txtNim.text = arrayList[position].nim
//        holder.view.txtNama.text = "Nama    : "+arrayList[position].nama
//        holder.view.txtAddress.text = "Address : "+arrayList[position].address
//        holder.view.txtGender.text = "Gender  : "+arrayList[position].gender

        //==========
//        holder.view.oprekTes?.text = "j"+arrayList[position].gender

        /**
         * mengoper beberapa value via intent, value ini nanti yang akan diterima oleh class [Crud.kt].
         * Ingat method onEditMode() yang telah saya buat sebelumnya ?,
         * ya. Datanya akan diolah oleh method tersebut.
         */
//        holder.view.cvList.setOnClickListener{
//            val i = Intent(context, Crud::class.java)
//            i.putExtra("editmode", "1")
//            i.putExtra("nim", arrayList[position].nim)
//            i.putExtra("nama", arrayList[position].nama)
//            i.putExtra("address", arrayList[position].address)
//            i.putExtra("gender", arrayList[position].gender)
//            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
////            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            context.startActivity(i)
////            context.applicationContext.startActivity(i)
//        }

    }

}