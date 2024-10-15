package com.example.calculator

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.math.BigDecimal

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var display: EditText
    private var operand1: BigDecimal = BigDecimal.ZERO
    private var operand2: BigDecimal = BigDecimal.ZERO
    private var operation: String? = null
    private var isNewOp: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        display = findViewById(R.id.editTextText)

        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnAdd, R.id.btnSub, R.id.btnMul, R.id.btnDiv,
            R.id.btnEqual, R.id.btnC, R.id.btnCE, R.id.btnBS,
            R.id.btnPlusMinus, R.id.btnDot
        )

        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener(this)
        }
    }

    override fun onClick(view: View) {
        val button = view as Button
        val buttonText = button.text.toString()

        when (buttonText) {
            "C" -> clearAll()
            "CE" -> clearEntry()
            "BS" -> backspace()
            "+/-" -> toggleSign()
            "." -> addDot()
            "+", "-", "*", "/" -> selectOperation(buttonText)
            "=" -> calculateResult()
            else -> handleDigit(buttonText)
        }
        adjustTextSize()
    }

    private fun handleDigit(digit: String) {
        if (isNewOp) {
            display.setText("")
            isNewOp = false
        }
        if (display.text.toString() == "0" && digit != ".") {
            display.setText(digit)
        } else {
            display.append(digit)
        }
    }


    private fun selectOperation(op: String) {
        operation = op
        operand1 = display.text.toString().toBigDecimalOrNull() ?: BigDecimal.ZERO
        display.append("\n$op")
        isNewOp = true
    }

    private fun calculateResult() {
        operand2 = display.text.toString().toBigDecimalOrNull() ?: BigDecimal.ZERO
        val result = when (operation) {
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "*" -> operand1 * operand2
            "/" -> if (operand2 != BigDecimal.ZERO) operand1.divide(operand2, 10, BigDecimal.ROUND_HALF_UP)
            else BigDecimal(Double.NaN)
            else -> BigDecimal.ZERO
        }
        display.setText(result.stripTrailingZeros().toPlainString())
        isNewOp = true
    }

    private fun clearAll() {
        display.setText("0")
        operand1 = BigDecimal.ZERO
        operand2 = BigDecimal.ZERO
        operation = null
    }

    private fun clearEntry() {
        display.setText("0")
    }

    private fun backspace() {
        val currentText = display.text.toString()
        if (currentText.isNotEmpty()) {
            display.setText(currentText.dropLast(1))
        }
        if (TextUtils.isEmpty(display.text)) {
            display.setText("0")
        }
    }

    private fun toggleSign() {
        val currentValue = display.text.toString().toBigDecimalOrNull() ?: return
        display.setText((currentValue.negate()).toPlainString())
    }

    private fun addDot() {
        val currentText = display.text.toString()
        if (isNewOp || currentText.isEmpty()) {
            display.setText("0.")
            isNewOp = false
        } else if (!currentText.contains(".")) {
            display.append(".")
        }
    }

    private fun adjustTextSize() {
        val length = display.text.length
        when {
            length > 30 -> display.textSize = 10f
            length > 20 -> display.textSize = 18f
            length > 15 -> display.textSize = 24f
            length > 10 -> display.textSize = 30f
            else -> display.textSize = 40f
        }
    }
}
