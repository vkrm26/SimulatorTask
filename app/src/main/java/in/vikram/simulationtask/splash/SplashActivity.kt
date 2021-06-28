package `in`.vikram.simulationtask.splash

import `in`.vikram.simulationtask.databinding.ActivitySplashBinding
import `in`.vikram.simulationtask.search.MainActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(this.mainLooper).postDelayed({
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()
        }, 500)

    }
}