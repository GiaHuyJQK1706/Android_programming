package com.example.studentman

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class StudentDialog(
    context: Context,
    private val student: StudentModel? = null,
    private val onSave: (StudentModel) -> Unit
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_student)

        val editName = findViewById<EditText>(R.id.edit_student_name)
        val editId = findViewById<EditText>(R.id.edit_student_id)
        val btnSave = findViewById<Button>(R.id.btn_save)

        student?.let {
            editName.setText(it.studentName)
            editId.setText(it.studentId)
        }

        btnSave.setOnClickListener {
            val name = editName.text.toString()
            val id = editId.text.toString()
            if (name.isNotEmpty() && id.isNotEmpty()) {
                onSave(StudentModel(name, id))
                dismiss()
            }
        }
    }
}
