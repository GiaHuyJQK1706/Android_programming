package com.example.searchingstudentsbasic

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.searchingstudentsbasic.adapters.StudentAdapter
import com.example.searchingstudentsbasic.models.Student
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var studentAdapter: StudentAdapter
    private val studentList = listOf(
        Student("Nguyễn Văn A", "20180001"),
        Student("Trần Thị B", "20180002"),
        Student("Lê Minh C", "20180003"),
        Student("Phạm Quốc D", "20180004"),
        Student("Hoàng Văn E", "20180005")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo adapter và gán vào RecyclerView
        studentAdapter = StudentAdapter(studentList)
        val rvStudents: RecyclerView = findViewById(R.id.rvStudents)
        rvStudents.layoutManager = LinearLayoutManager(this)
        rvStudents.adapter = studentAdapter

        // Lắng nghe sự kiện khi người dùng nhập vào ô tìm kiếm
        val etSearch: EditText = findViewById(R.id.etSearch)
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val keyword = s.toString().trim()
                if (keyword.length > 2) {
                    val filteredList = studentList.filter {
                        it.name.contains(keyword, ignoreCase = true) ||
                                it.mssv.contains(keyword)
                    }
                    studentAdapter.updateList(filteredList)
                } else {
                    studentAdapter.updateList(studentList)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}