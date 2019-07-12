package id.scode.progressBar

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import id.scode.kotlincrud.R
import kotlinx.android.synthetic.main.activity_progress_oprek.*

class ProgressOprek : AppCompatActivity() {

    var isStarted = false
    var progressStatus = 0
    var handler: Handler? = null
    var secondaryHandler: Handler? = Handler()
    var primaryProgressStatus = 0
    var secondaryProgressStatus = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_oprek)


        handler = Handler(Handler.Callback {
            if (isStarted) {
                oprekProgress.isIndeterminate = isStarted
                progressStatus++
                for (i in progressStatus..progressBarHorizontal.max)
                {
                    progressBarHorizontal.progress = progressStatus
                    textViewHorizontalProgress.text = "$progressStatus/${progressBarHorizontal.max}"
                }
            }

            if (progressStatus == progressBarHorizontal.max)
            {
//                progressBarHorizontal.isIndeterminate = true
//                progressBarHorizontal.visibility = View.GONE
//                textViewHorizontalProgress.visibility = View.GONE
                Toast.makeText(this, "asd", Toast.LENGTH_LONG).show()
                oprekProgress.isIndeterminate = !isStarted
            }
            handler?.sendEmptyMessageDelayed(0, 100)

            true
        })


        handler?.sendEmptyMessage(0)


        btnProgressBarSecondary.setOnClickListener {
            primaryProgressStatus = 0
            secondaryProgressStatus = 0

            Thread(Runnable {
                while (primaryProgressStatus < 100) {
                    primaryProgressStatus += 1

                    try {
                        Thread.sleep(1000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                    startSecondaryProgress()
                    secondaryProgressStatus = 0

                    secondaryHandler?.post {
                        progressBarSecondary.progress = primaryProgressStatus
                        textViewPrimary.text = "Complete $primaryProgressStatus% of 100"

                        if (primaryProgressStatus == 100) {
                            textViewPrimary.text = "All tasks completed"
                        }
                    }
                }
            }).start()
        }

    }

    fun startSecondaryProgress() {
        Thread(Runnable {
            while (secondaryProgressStatus < 100) {
                secondaryProgressStatus += 1

                try {
                    Thread.sleep(10)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                secondaryHandler?.post {
                    progressBarSecondary.setSecondaryProgress(secondaryProgressStatus)
                    textViewSecondary.setText("Current task progress\n$secondaryProgressStatus% of 100")

                    if (secondaryProgressStatus == 100) {
                        textViewSecondary.setText("Single task complete.")
                    }
                }
            }
        }).start()
    }

    private fun startStop()
    {
        isStarted = !isStarted
    }

    fun horizontalDeterminate(view: View) {
       startStop()
    }

}