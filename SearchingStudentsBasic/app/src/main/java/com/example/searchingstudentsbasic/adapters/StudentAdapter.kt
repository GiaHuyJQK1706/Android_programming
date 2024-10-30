package com.example.searchingstudentsbasic.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.searchingstudentsbasic.R
import com.example.searchingstudentsbasic.models.Student

class StudentAdapter(
    private var studentList: List<Student>
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tvName)
        private val mssvTextView: TextView = itemView.findViewById(R.id.tvMSSV)

        fun bind(student: Student) {
            nameTextView.text = student.name
            mssvTextView.text = student.mssv
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(studentList[position])
    }

    override fun getItemCount() = studentList.size

    // Cập nhật danh sách sau khi tìm kiếm
    fun updateList(newList: List<Student>) {
        studentList = newList
        notifyDataSetChanged()
    }
}