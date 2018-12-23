package diabetes.com.synchronization

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.diabetesm.addons.api.DiabetesAppConnection



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectionTest()
    }

    private fun connectionTest() {
        val diabetesAppConnection = DiabetesAppConnection(this@MainActivity)

        val checkStatus = diabetesAppConnection.checkDiabetesMApp()
        Toast.makeText(this@MainActivity, "checkStatus = $checkStatus",  Toast.LENGTH_SHORT).show()
    }
}
