package id.scode.oprekCrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import id.scode.kotlincrud.ApiEndPoint
import id.scode.kotlincrud.R
import kotlinx.android.synthetic.main.activity_image_oprek.*
import org.json.JSONObject

class ImageOprek : AppCompatActivity() {

    val arrayList = ArrayList<DataImage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_oprek)

        rvImageOprek.setHasFixedSize(true)
        rvImageOprek.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 1, androidx.recyclerview.widget.GridLayoutManager.HORIZONTAL, false)

    }

    private fun loadImageData()
    {
        progressCrud.isIndeterminate = true

        AndroidNetworking.get(ApiEndPoint.RIMG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener{
                    override fun onResponse(response: JSONObject?) {

                        arrayList.clear()

                        val jsonArray = response?.optJSONArray("result")

                        if (jsonArray?.length() == 0)
                        {

                            progressCrud.isIndeterminate = false
                            progressCrud.visibility = View.GONE
                            Toast.makeText(applicationContext, "Image is empty", Toast.LENGTH_LONG).show()
                        }

                        for (i in 0 until jsonArray?.length()!!)
                        {
                            val jsonObject = jsonArray.optJSONObject(i)

                            arrayList.add(DataImage(jsonObject.getString("image_url")))


                            if (jsonArray.length() - 1 == i)
                            {

                                progressCrud.isIndeterminate = false
                                progressCrud.visibility = View.GONE

                                val rvAdapterImage = RvAdapterImage(applicationContext, arrayList)
                                rvAdapterImage.notifyDataSetChanged()
                                rvImageOprek.adapter = rvAdapterImage

                            }
                        }
                    }

                    override fun onError(anError: ANError?) {


                        progressCrud.isIndeterminate = false
                        progressCrud.visibility = View.GONE

                        Log.d("ONERROR", anError?.errorDetail.toString())
                        Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_LONG).show()
                    }

                })
    }

    override fun onResume() {
        super.onResume()
        loadImageData()
    }
}
