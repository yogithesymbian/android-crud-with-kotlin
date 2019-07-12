package id.scode.oprekCrud

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import id.scode.kotlincrud.*
import kotlinx.android.synthetic.main.activity_oprek_crud.*
import org.json.JSONObject

class OprekCrud : AppCompatActivity() {

    val arrayList = ArrayList<DataOprek>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oprek_crud)

        rvOprek.setHasFixedSize(true)
        rvOprek.layoutManager = GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)


    }

    private fun loadAllStudents()
    {

//        val loading = ProgressDialog(this)
//        loading.setMessage("Memuat data . . .")
//        loading.show()
        progressCrud.isIndeterminate = true
        /**
         * melakukan request ke server untuk memuat data dari read.php [ApiEndPoint]
         * handle respon from server onResponse dan onError
         */
        AndroidNetworking.get(ApiEndPoint.READ)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        arrayList.clear() //agar data tidak duplicate saat script json ini di panggil
                        val jsonArray = response?.optJSONArray("result")

                        if (jsonArray?.length() == 0)
                        {
//                            loading.dismiss()
                            progressCrud.isIndeterminate = false
                            progressCrud.visibility = View.GONE
                            //json "result" berada di read.php result adalah reponse yang ane buat
                            Toast.makeText(applicationContext,"Student data is empty, Add the data first", Toast.LENGTH_LONG).show()
                        }
                        /**
                         * for closing progress dialog ini
                         * looping for jsonArray to save data in arrayList has create, then checking has it ? with if statements
                         */
                        for (i in 0 until jsonArray?.length()!!)
                        {
                            val jsonObject = jsonArray.optJSONObject(i)

                            arrayList.add(DataOprek(jsonObject.getString("gender")))

                            if (jsonArray.length() - 1 == i)
                            {
//                                loading.dismiss()
                                progressCrud.isIndeterminate = false
                                progressCrud.visibility = View.GONE
                                /**
                                 * PENTING [ P E N T I N G   N A N T I ]
                                 * set adapter from RvAdapter with data from arrayList<DataStudents>
                                 */
                                val adapterOprek = RvAdapterOprek(applicationContext, arrayList)
                                adapterOprek.notifyDataSetChanged()
                                rvOprek.adapter = adapterOprek
                            }
                        }
                    }

                    override fun onError(anError: ANError?) {
//                        loading.dismiss()
                        progressCrud.isIndeterminate = false
                        progressCrud.visibility = View.GONE
                        Log.d("ONERROR",anError?.errorDetail.toString())
                        Toast.makeText(applicationContext,"Connection Failure", Toast.LENGTH_LONG).show()
                    }

                })
    }

    override fun onResume() {
        super.onResume()
        loadAllStudents()
    }


}
