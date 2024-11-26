package vn.edu.hust.activityexamples.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.edu.hust.activityexamples.R
import vn.edu.hust.activityexamples.model.Student

class StudentAdapter(private val students: List<Student>) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student)
    }

    override fun getItemCount(): Int = students.size

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtFullName: TextView = itemView.findViewById(R.id.txtFullName)
        private val txtStudentId: TextView = itemView.findViewById(R.id.txtStudentId)

        fun bind(student: Student) {
            txtFullName.text = "Họ và tên: ${student.fullName}"
            txtStudentId.text = "Mã số: ${student.studentId}"
        }
    }
}
