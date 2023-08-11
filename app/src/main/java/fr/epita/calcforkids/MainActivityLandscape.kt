package fr.epita.calcforkids

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivityLandscape : AppCompatActivity() {
    lateinit var  calcType: String;
    lateinit var num1TextView: TextView;
    lateinit var num2TextView: TextView;
    lateinit var resultText: TextView;
    lateinit var inputCalcImageView: ImageView
    var calImageSet: Boolean = false;
    var activatedField: Int = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_landscape)
        inputCalcImageView = findViewById<ImageView>(R.id.inputCalcImageView)
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
                        val divide = divide(num1.toDouble(), num2.toDouble())
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
        calImageSet = !calImageSet
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

    fun divide(dividend: Double, divisor: Double): Double {
        if (divisor == 0.0) {
            throw IllegalArgumentException("Cannot divide by zero")
        }
        return dividend / divisor
    }

    private fun numberClicked(number: String) {
        val textView:TextView
        if (!calImageSet || TextUtils.isEmpty(num1TextView.text)) {
            textView = findViewById<TextView>(R.id.number1TextView)
        } else {
            textView = findViewById<TextView>(R.id.number2TextView)
        }
        textView.append(number)
    }

    private fun signClicked(type: String) {
        calImageSet = !calImageSet
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
}