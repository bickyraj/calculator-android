package fr.epita.calcforkids

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import fr.epita.calcforkids.models.AdsResponse
import fr.epita.calcforkids.services.ApiConfig
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var  calcType: String
    lateinit var num1TextView: TextView
    lateinit var num2TextView: TextView
    lateinit var resultText: TextView
    lateinit var inputCalcImageView: ImageView
    var calImageSet: Boolean = false
    var activatedField: Int = 1
    lateinit var clearAllButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Set the landscape layout as the content view
            setContentView(R.layout.activity_main_landscape)
            inputCalcImageView = findViewById<ImageView>(R.id.inputCalcImageView)
//            Toast.makeText(this, "Button clicked!", Toast.LENGTH_SHORT).show()
            val button0 = findViewById<Button>(R.id.button0)
            button0.setOnClickListener { numberClicked("0") }
            val button1 = findViewById<Button>(R.id.button1)
            button1.setOnClickListener { numberClicked("1") }
            val button2 = findViewById<Button>(R.id.button2)
            button2.setOnClickListener { numberClicked("2") }
            val button3 = findViewById<Button>(R.id.button3)
            button3.setOnClickListener { numberClicked("3") }
            val button4 = findViewById<Button>(R.id.button4)
            button4.setOnClickListener { numberClicked("4") }
            val button5 = findViewById<Button>(R.id.button5)
            button5.setOnClickListener { numberClicked("5") }
            val button6 = findViewById<Button>(R.id.button6)
            button6.setOnClickListener { numberClicked("6") }
            val button7 = findViewById<Button>(R.id.button7)
            button7.setOnClickListener { numberClicked("7") }
            val button8 = findViewById<Button>(R.id.button8)
            button8.setOnClickListener { numberClicked("8") }
            val button9 = findViewById<Button>(R.id.button9)
            button9.setOnClickListener { numberClicked("9") }

            val addButton = findViewById<ImageView>(R.id.plusImageView)
            val minusButton = findViewById<ImageView>(R.id.minusImageView)
            val multiplyButton = findViewById<ImageView>(R.id.timesImageView)
            val divideButton = findViewById<ImageView>(R.id.qmarkImageView)
            addButton.setOnClickListener { signClicked("plus") }
            minusButton.setOnClickListener { signClicked("minus") }
            multiplyButton.setOnClickListener { signClicked("times") }
            divideButton.setOnClickListener { signClicked("divide") }
            val showResultButton = findViewById<Button>(R.id.equalButton)
            showResultButton.setOnClickListener { calculate() }

            num1TextView = findViewById<TextView>(R.id.number1TextView)
            num2TextView = findViewById<TextView>(R.id.number2TextView)
            resultText = findViewById<TextView>(R.id.resultTextView)

            clearAllButton = findViewById<Button>(R.id.clearAllButton)
            clearAllButton.setOnClickListener { clearAll() }
            getAd()
        } else {
            // Set the portrait layout as the content view
            setContentView(R.layout.activity_main)
            // Add your code for portrait mode here
        }
    }

    private fun calculate(): Boolean {
        if (!TextUtils.isEmpty(resultText.text)) {
            // clear all text view
            clearAll()
        } else {
            if (!TextUtils.isEmpty(num2TextView.text) && !TextUtils.isEmpty(num1TextView.text)) {
                val num1Text: String = findViewById<TextView>(R.id.number1TextView).text.toString()
                val num2Text: String = findViewById<TextView>(R.id.number2TextView).text.toString()
                val num1: Int = num1Text.toInt()
                val num2: Int = num2Text.toInt()
                when (this.calcType) {
                    "plus" -> {
                        val sum = sum(num1, num2)
                        resultText.text = sum.toString()
                    }
                    "minus" -> {
                        val subtract = subtract(num1, num2)
                        resultText.text = subtract.toString()
                    }
                    "times" -> {
                        val multiply = multiply(num1, num2)
                        resultText.text = multiply.toString()
                    }
                    "divide" -> {
                        val divide = divide(num1, num2)
                        resultText.text = divide.toString()
                    }
                    else -> {

                    }
                }
            }
        }
        return true;
    }

    private fun clearAll(): Boolean {
        val inputStream = assets.open("img_qmark.png")
        val drawable = Drawable.createFromStream(inputStream, null)
        inputCalcImageView.setImageDrawable(drawable)
        num1TextView.text = ""
        num2TextView.text = ""
        resultText.text = ""
        calImageSet = false
        activatedField = 1
        return true;
    }

    private fun sum(a: Int, b: Int): Int {
        return a + b
    }

    private fun multiply(a: Int, b: Int): Int {
        return a * b
    }

    private fun subtract(a: Int, b: Int): Int {
        return a - b
    }

    private fun divide(dividend: Int, divisor: Int): Int? {
        return if (divisor == 0) {
            Toast.makeText(this, "Cannot divide by 0. Please try new calculation.", Toast.LENGTH_SHORT).show()
    //            throw IllegalArgumentException("Cannot divide by zero")
            0;
        } else {
            (dividend / divisor).toInt()
        }
    }

    private fun numberClicked(number: String) {
        val textView:TextView
        // check if the result is already set
        if (!TextUtils.isEmpty(resultText.text)) clearAll()
        if (!calImageSet || TextUtils.isEmpty(num1TextView.text)) {
            textView = findViewById<TextView>(R.id.number1TextView)
        } else {
            textView = findViewById<TextView>(R.id.number2TextView)
        }
        textView.append(number)
    }

    private fun signClicked(type: String) {
        calImageSet = true
        this.calcType = type
        when (type) {
            "plus" -> {
                val inputStream = assets.open("img_plus.png")
                val drawable = Drawable.createFromStream(inputStream, null)
                inputCalcImageView.setImageDrawable(drawable)
            }
            "minus" -> {
                val inputStream = assets.open("img_minus.png")
                val drawable = Drawable.createFromStream(inputStream, null)
                inputCalcImageView.setImageDrawable(drawable)
            }
            "times" -> {
                val inputStream = assets.open("img_times.png")
                val drawable = Drawable.createFromStream(inputStream, null)
                inputCalcImageView.setImageDrawable(drawable)
            }
            "divide" -> {
                val inputStream = assets.open("img_div.png")
                val drawable = Drawable.createFromStream(inputStream, null)
                inputCalcImageView.setImageDrawable(drawable)
            }
            else -> {
                println("Other")
            }
        }
    }

    private fun getAd() {
        val client = ApiConfig.getApiService().getAdList()
        // Send API request using Retrofit
        client.enqueue(object : Callback<List<AdsResponse>> {
            override fun onResponse(
                call: Call<List<AdsResponse>>,
                response: Response<List<AdsResponse>>
            ) {
                if (response.isSuccessful) {
                    val responseList = response.body()
                    if (responseList != null) {
                        val activeAds = responseList.filter { it.active }
                        val randomElement = activeAds.random()
                        val adTextView: TextView = findViewById(R.id.adTextView)
                        adTextView.text = randomElement.content
                    }

                } else {
                    // handle error
                }
            }

            override fun onFailure(call: Call<List<AdsResponse>>, t: Throwable) {
                Log.i(MainActivity::class.simpleName, "on FAILURE!!!!")
            }
        })
    }
}