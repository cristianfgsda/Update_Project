package com.example.update_project

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val calculateButton = findViewById<Button>(R.id.calculateButton)

        calculateButton.setOnClickListener {
            calculateInstallment()
        }
    }

    private fun calculateInstallment() {
        val loanText = findViewById<EditText>(R.id.loanText)
        val interestText = findViewById<EditText>(R.id.interestText)
        val periodText = findViewById<EditText>(R.id.periodText)

        val loanAmount = loanText.text.toString().toDoubleOrNull() ?: return
        val interestRate = interestText.text.toString().toDoubleOrNull() ?: return
        val amortizationPeriod = periodText.text.toString().toIntOrNull() ?: return

        try {
            val calculator = Calculator(loanAmount, interestRate, amortizationPeriod)
            val result = calculator.calculateResult()

            val pesoFormat = NumberFormat.getCurrencyInstance(java.util.Locale("es", "CO"))
            pesoFormat.minimumFractionDigits = if (result % 1 == 0.0) 0 else 2
            val formattedResult = pesoFormat.format(result)

            findViewById<TextView>(R.id.resultText).text = "Monthly Installment: $formattedResult"
        } catch (e: IllegalArgumentException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}
