package id.scode.kotlincrud

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_crud.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_treatment.*
import org.json.JSONObject
import id.scode.kotlincrud.R.string.nama



class Treatment : AppCompatActivity() {

    val arrayList = ArrayList<DataTreatment>()
//    var EXTRA_DATETIME = "extra_datetime"

    var datetimesort: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_treatment)

        recycleViewDataTreatment.setHasFixedSize(true)
        recycleViewDataTreatment.layoutManager = LinearLayoutManager(this)

//        val dateTimeSort = intent.getStringExtra(EXTRA_DATETIME)
        datetimesort = intent?.getStringExtra("DateTime")

        txtViewSortDate?.text = "data tahun : " +datetimesort+ " sampai sekarang"


    }

    private fun loadAllStudents()
    {
        val loadingProgressBar = ContentLoadingProgressBar(this)
        loadingProgressBar.show()
        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data . . .")
        loading.show()

        /**
         * melakukan request ke server untuk memuat data dari read.php [ApiEndPoint]
         * handle respon from server onResponse dan onError
         */
//        AndroidNetworking.get(ApiEndPoint.DELETE+"?nim="+edtNim1.text.toString())

        Log.d("DATE_TIME","ke = "+datetimesort)
//        AndroidNetworking.get(ApiEndPoint.DATAVIEW+"?treat_date="+txtViewSortDate.text.toString())
        AndroidNetworking.get(ApiEndPoint.DATAVIEW+"?treat_date="+datetimesort)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        arrayList.clear() //agar data tidak duplicate saat script json ini di panggil
                        val jsonArray = response?.optJSONArray("result")

                        if (jsonArray?.length() == 0)
                        {
                            loadingProgressBar.hide()
                            loading.dismiss()
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

                            arrayList.add(DataTreatment(jsonObject.getString("treat_code"),
                                    jsonObject.getString("treat_date"),
                                    jsonObject.getString("doc_id"),
                                    jsonObject.getString("pat_id"),
                                    jsonObject.getString("year")))
                            if (jsonArray.length() - 1 == i)
                            {
                                loadingProgressBar.hide()
                                loading.dismiss()
                                //set adapter from RvAdapter with data from arrayList<DataStudents>
                                val adapter = RvAdapterTreatment(applicationContext, arrayList)
                                adapter.notifyDataSetChanged()
                                // isi adapter => dari recycleView dengan RvAdapter
                                recycleViewDataTreatment.adapter = adapter
                            }
                        }
                    }

                    override fun onError(anError: ANError?) {
                        loading.dismiss()
                        Log.d("ONERROR",anError?.errorDetail.toString())
                        Toast.makeText(applicationContext,"Connection Failure", Toast.LENGTH_LONG).show()
                    }

                })
    }

    /**
     * this for loadData while application in the START of condition onResume
     */
    override fun onResume() {
        super.onResume()
        loadAllStudents()
    }
}
