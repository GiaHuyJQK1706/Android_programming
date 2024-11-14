package com.example.studentman

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val students = mutableListOf(
        StudentModel("Nguyễn Văn An", "SV001"),
        StudentModel("Trần Thị Bảo", "SV002"),
        StudentModel("Lê Hoàng Cường", "SV003"),
        StudentModel("Phạm Thị Dung", "SV004"),
        StudentModel("Đỗ Minh Đức", "SV005"),
        StudentModel("Vũ Thị Hoa", "SV006"),
        StudentModel("Hoàng Văn Hải", "SV007"),
        StudentModel("Bùi Thị Hạnh", "SV008"),
        StudentModel("Đinh Văn Hùng", "SV009"),
        StudentModel("Bùi Phương Linh", "SV010"),
        StudentModel("Phạm Văn Long", "SV011"),
        StudentModel("Trần Thị Mai", "SV012"),
        StudentModel("Lê Thị Ngọc", "SV013"),
        StudentModel("Đỗ Nguyễn Hà Nam", "SV014"),
        StudentModel("Hoàng Thị Phương", "SV015"),
        StudentModel("Đỗ Văn Quân", "SV016"),
        StudentModel("Nguyễn Thị Thu", "SV017"),
        StudentModel("Trần Văn Tài", "SV018"),
        StudentModel("Phạm Thị Tuyết", "SV019"),
        StudentModel("Lê Văn Vũ", "SV020")
    )
    private lateinit var studentAdapter: StudentAdapter
    private var recentlyDeletedStudent: StudentModel? = null
    private var recentlyDeletedPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentAdapter = StudentAdapter(students, object : StudentAdapter.OnItemActionListener {
            override fun onEdit(student: StudentModel, position: Int) {
                showEditStudentDialog(student, position)
            }

            override fun onDelete(student: StudentModel, position: Int) {
                showDeleteConfirmationDialog(student, position)
            }
        })

        findViewById<RecyclerView>(R.id.recycler_view_students).run {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        findViewById<Button>(R.id.btn_add_new).setOnClickListener {
            showAddStudentDialog()
        }
    }

    private fun showAddStudentDialog() {
        val dialog = StudentDialog(this) { newStudent ->
            students.add(newStudent)
            studentAdapter.notifyItemInserted(students.size - 1)
        }
        dialog.show()
    }

    private fun showEditStudentDialog(student: StudentModel, position: Int) {
        val dialog = StudentDialog(this, student) { updatedStudent ->
            students[position] = updatedStudent
            studentAdapter.notifyItemChanged(position)
        }
        dialog.show()
    }

    private fun showDeleteConfirmationDialog(student: StudentModel, position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Xóa sinh viên")
            .setMessage("Bạn có chắc chắn muốn xóa sinh viên này không?")
            .setPositiveButton("Xóa") { _, _ ->
                recentlyDeletedStudent = student
                recentlyDeletedPosition = position
                students.removeAt(position)
                studentAdapter.notifyItemRemoved(position)
                showUndoSnackbar()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun showUndoSnackbar() {
        val mainView = findViewById<View>(R.id.main)
        Snackbar.make(mainView, "Đã xóa sinh viên", Snackbar.LENGTH_LONG)
            .setAction("Hoàn tác") {
                recentlyDeletedStudent?.let {
                    students.add(recentlyDeletedPosition, it)
                    studentAdapter.notifyItemInserted(recentlyDeletedPosition)
                }
            }.show()
    }
}
