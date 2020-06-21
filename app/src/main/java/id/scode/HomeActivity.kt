package id.scode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.scode.kotlincrud.MainActivity
import id.scode.kotlincrud.R
import id.scode.oprekCrud.ImageOprek
import id.scode.oprekCrud.OprekCrud
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btn1.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
        btn2.setOnClickListener{
            startActivity(Intent(this, ImageOprek::class.java))
        }
        btn3.setOnClickListener{
            startActivity(Intent(this, OprekCrud::class.java))
        }
    }
}
