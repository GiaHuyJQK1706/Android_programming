package com.example.listnumbersbasic

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var edtNumber: EditText
    private lateinit var radioEven: RadioButton
    private lateinit var radioOdd: RadioButton
    private lateinit var radioSquare: RadioButton
    private lateinit var btnShow: Button
    private lateinit var listView: ListView
    private lateinit var tvError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Anh xa cac view
        edtNumber = findViewById(R.id.edtNumber)
        radioEven = findViewById(R.id.radioEven)
        radioOdd = findViewById(R.id.radioOdd)
        radioSquare = findViewById(R.id.radioSquare)
        btnShow = findViewById(R.id.btnShow)
        listView = findViewById(R.id.listView)
        tvError = findViewById(R.id.tvError)

        // Xu ly su kien khi an nut show
        btnShow.setOnClickListener {
            val input = edtNumber.text.toString()

            if (input.isEmpty() || !isPositiveInteger(input)) {
                showError("Vui lòng nhập một số nguyên dương hợp lệ.")
                return@setOnClickListener
            }

            val n = input.toInt()
            val result = when {
                radioEven.isChecked -> getEvenNumbers(n)
                radioOdd.isChecked -> getOddNumbers(n)
                radioSquare.isChecked -> getSquareNumbers(n)
                else -> {
                    showError("Vui lòng chọn số nguyên dương.")
                    return@setOnClickListener
                }
            }

            displayResult(result)
        }
    }

    // Kiem tra so hop le
    private fun isPositiveInteger(input: String): Boolean {
        return try {
            val number = input.toInt()
            number > 0
        } catch (e: NumberFormatException) {
            false
        }
    }

    // Hien thi loi
    private fun showError(message: String) {
        tvError.text = message
        tvError.visibility = View.VISIBLE
    }

    // Hien thi so thich hop trong ListView
    private fun displayResult(result: List<Int>) {
        tvError.visibility = View.GONE // Ẩn lỗi nếu có
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, result)
        listView.adapter = adapter
    }

    // DS so chan
    private fun getEvenNumbers(n: Int): List<Int> {
        return (0..n step 2).toList()
    }

    // DS so le
    private fun getOddNumbers(n: Int): List<Int> {
        return (1..n step 2).toList()
    }

    // DS so chinh phuong
    private fun getSquareNumbers(n: Int): List<Int> {
        val list = mutableListOf<Int>()
        var i = 0
        while (i * i <= n) {
            list.add(i * i)
            i++
        }
        return list
    }
}