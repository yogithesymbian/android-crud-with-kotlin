package id.scode.kotlincrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_filtering.*
import android.content.Intent
import android.util.Log


class Filtering : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtering)

        edtTo.isEnabled = false

        btnDate.setOnClickListener{

            val fromdata = edtFrom.text.toString()

            Log.d("MOVING", "sorted{$fromdata}")

            val moveIntentWithData = Intent(this@Filtering, Treatment::class.java)
            moveIntentWithData.putExtra("DateTime", fromdata)
//            moveIntentWithData.putExtra("DateTime", "abcdefghijklmn")
            startActivity(moveIntentWithData)
        }
    }
}
