package id.scode.oprekCrud

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.scode.kotlincrud.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.oprek_image_view.view.*

/**
 * Created by Dani on 1/4/2019.
 * Yogi Arif Widodo
 */
class RvAdapterImage(private val context: Context, private val arrayList: ArrayList<DataImage>) : androidx.recyclerview.widget.RecyclerView.Adapter<RvAdapterImage.Holder>() {

    class Holder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.oprek_image_view, parent, false))
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(context)
                .load(arrayList[position].imageUrlData)
                .apply(RequestOptions().override(350, 550))
                .into(holder.view.imageView)
    }
}
