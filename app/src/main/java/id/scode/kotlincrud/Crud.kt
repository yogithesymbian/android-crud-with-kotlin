package id.scode.kotlincrud

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_crud.*
import org.json.JSONObject

class Crud : AppCompatActivity() {

    lateinit var i: Intent
    private var gender = "Pria"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud)

        /**
         * mengecek apakah ada sebuah key “editmode” dari intent ?, [click cardView]
         * jika ada maka di cek lagi apakah “editmode” mempunyai nilai “1”?
         * jika iya maka view dari class ManageStudentActivity akan diubah kemode edit/delete.
         */
        i = intent
        if (i.hasExtra("editmode"))
        {
            if (i.getStringExtra("editmode").equals("1"))
            {
                onEditMode()
            }
        }

        /**
         * memberikan checker listener pada rgGender dimana jika yang di check adalah radioBoy
         * maka kita mengubah nilai yang ada pada variabel gender menjadi “Pria”
         * jika radioGirl maka nilai menjadi “Wanita”.
         */
        rdGender.setOnCheckedChangeListener{group: RadioGroup?, checkedId: Int ->
            when(checkedId)
            {
                R.id.rdBoy->
                {
                    gender = "Pria"
                }

                R.id.rdGirl->
                {
                    gender = "Wanita"
                }
            }
        }

        btnCreate.setOnClickListener {
            create()
        }

        btnUpdate.setOnClickListener{
            update()
        }
        btnDelete.setOnClickListener{
            AlertDialog.Builder(this)
                    .setTitle("Confirmation")
                    .setMessage("Hapus Data Ini ?")
                    .setPositiveButton("Hapus", DialogInterface.OnClickListener{ dialog: DialogInterface?, which: Int ->
                        delete()
                    })
                    .setNegativeButton("Batal", DialogInterface.OnClickListener{ dialog, which ->
                        dialog.dismiss()
                    })
                    .show()
        }

    }

    private fun onEditMode()
    {
        /**
         * saat method ini dipanggil maka kita akan melakukan set value pada txNim, txName,txAddress dan rgGender
         * dengan value yang diambil dari intent. Dan juga kita mencagah agar NIM tidak dapat diedit.
         * ONEDITMODE
         * Saya juga menghilangkan btnCreate dan memunculkan btnUpdate dan btnDelete.
         */
        edtNim1.setText(i.getStringExtra("nim"))
        edtNama1.setText(i.getStringExtra("nama"))
        edtAddress1.setText(i.getStringExtra("address"))
        edtNim1.isEnabled = false


        btnCreate.visibility = View.GONE
        btnUpdate.visibility = View.VISIBLE
        btnDelete.visibility = View.VISIBLE

        gender = i.getStringExtra("gender")
        if (gender.equals("Pria"))
        {
            rdGender.check(R.id.rdBoy)
        }
        else
        {
            rdGender.check(R.id.rdGirl)
        }
    }

    /**
     * proses networking kali ini kita menggunkan method post pada file create.php
     * dengan body parameter yang di passing adalah
     * “nim”,”name”,”address”,”gender”.
     * CREATE
     */
    private fun create()
    {
        val loading = ProgressDialog(this)
        loading.setMessage("Menambahkan data . . . ")
        loading.show()

        AndroidNetworking.post(ApiEndPoint.CREATE)
                .addBodyParameter("nim", edtNim1.text.toString())
                .addBodyParameter("nama", edtNama1.text.toString())
                .addBodyParameter("address", edtAddress1.text.toString())
                .addBodyParameter("gender", gender)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener{
                    override fun onResponse(response: JSONObject?) {
                        loading.dismiss()
                        Toast.makeText(applicationContext,response?.getString("message"), Toast.LENGTH_LONG).show()

                        if (response?.getString("message")?.contains("successfully")!!)
                        {
                            Log.d("ONRESPONSE", response.getString("message"))
                            this@Crud.finish()
                        }
                    }

                    override fun onError(anError: ANError?) {
                        loading.dismiss()
                        Log.d("ONERROR", anError?.errorDetail?.toString())
                        Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_LONG).show()
                    }

                })
    }
    private fun update()
    {
        val loading = ProgressDialog(this)
        loading.setMessage("Mengubah data")
        loading.show()

        AndroidNetworking.post(ApiEndPoint.UPDATE)
                .addBodyParameter("nim", edtNim1.text.toString())
                .addBodyParameter("nama", edtNama1.text.toString())
                .addBodyParameter("address", edtAddress1.text.toString())
                .addBodyParameter("gender", gender)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener{
                    override fun onResponse(response: JSONObject?) {
                        loading.dismiss()
                        Toast.makeText(applicationContext, response?.getString("message"), Toast.LENGTH_LONG).show()
                        if (response?.getString("message")?.contains("successfully")!!)
                        {
                            this@Crud.finish()
                        }
                    }

                    override fun onError(anError: ANError?) {
                        loading.dismiss()
                        Log.d("ONERROR", anError?.errorDetail?.toString())
                        Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_LONG).show()
                    }

                })
    }
    private fun delete()
    {
        val loading = ProgressDialog(this)
        loading.setMessage("Menghapus data . . .")
        loading.show()

        AndroidNetworking.get(ApiEndPoint.DELETE+"?nim="+edtNim1.text.toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener{
                    override fun onResponse(response: JSONObject?) {
                        loading.dismiss()
                        Toast.makeText(applicationContext, response?.getString("message"), Toast.LENGTH_LONG).show()
                        if (response?.getString("message")?.contains("successfully")!!)
                        {
                            this@Crud.finish()
                        }
                    }

                    override fun onError(anError: ANError?) {
                        loading.dismiss()
                        Log.d("ONERROR", anError?.errorDetail?.toString())
                        Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_LONG).show()
                    }

                })

    }
}
