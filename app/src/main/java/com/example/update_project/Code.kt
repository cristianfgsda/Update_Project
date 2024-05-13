package com.example.update_project

import java.lang.IllegalArgumentException

class Calculator(private val loanAmount: Double, private val interestRate: Double, private val amortizationPeriod: Int) {

    @Throws(IllegalArgumentException::class)
    fun calculateResult(): Double {
        if (loanAmount <= 0 || loanAmount > 1_000_000) {
            throw IllegalArgumentException("Monto del préstamo <> 1,000,000")
        }

        if (amortizationPeriod <= 0 || amortizationPeriod > 5) {
            throw IllegalArgumentException("Amortización (5 años) ")
        }

        if (interestRate <= 0 || interestRate > 33.07) {
            throw IllegalArgumentException("Tasa de interés 33,07%")
        }

        val monthlyInterestRate = interestRate / 100 / 12
        val numberOfPayments = amortizationPeriod * 12
        return loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfPayments.toDouble()) /
                (Math.pow(1 + monthlyInterestRate, numberOfPayments.toDouble()) - 1)
    }
}
