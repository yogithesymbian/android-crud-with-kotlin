package id.scode.kotlincrud

import android.app.ProgressDialog
import android.content.Intent
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
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val arrayList = ArrayList<DataStudents>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        supportActionBar?.title = "Kotlin Crud"

        /**
         * set recyclerView to LinearLayout
         */
        recycleViewData.setHasFixedSize(true)
        recycleViewData.layoutManager = LinearLayoutManager(this)

//        val adapterListPresident = AdapterListPresident(this)
//        adapterListPresident.setListPresident(list)
//        rvCategory.setAdapter(adapterListPresident)

        btnFloating.setOnClickListener{
            startActivity(Intent(this, Crud::class.java))
        }

//        cvList.setOnClickListener{
//            val intent = Intent(this, Crud::class.java)
//            intent.putExtra("editmode", "1")
//
//            startActivity(intent)
//
//        }
    }

    /**
     * ProgressDialog Load Data
     */
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
        AndroidNetworking.get(ApiEndPoint.READ)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener{
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

                            arrayList.add(DataStudents(jsonObject.getString("nim"),
                            jsonObject.getString("nama"),
                            jsonObject.getString("address"),
                            jsonObject.getString("gender")))
                            if (jsonArray.length() - 1 == i)
                            {
                                loadingProgressBar.hide()
                                loading.dismiss()
                                //set adapter from RvAdapter with data from arrayList<DataStudents>
                                val adapter = RvAdapterStudents(applicationContext, arrayList)
                                adapter.notifyDataSetChanged()
                                // isi adapter => dari recycleView dengan RvAdapter
                                recycleViewData.adapter = adapter
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
