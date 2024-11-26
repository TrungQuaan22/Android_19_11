package vn.edu.hust.activityexamples

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import vn.edu.hust.activityexamples.model.Student

class AddEditStudentActivity : AppCompatActivity() {
    private lateinit var edtFullName: EditText
    private lateinit var edtStudentId: EditText
    private lateinit var btnSave: Button
    private var student: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_student)

        edtFullName = findViewById(R.id.edtFullName)
        edtStudentId = findViewById(R.id.edtStudentId)
        btnSave = findViewById(R.id.btnSave)

        // Lấy dữ liệu nếu đang chỉnh sửa
        student = intent.getSerializableExtra("student") as? Student
        student?.let {
            edtFullName.setText(it.fullName)
            edtStudentId.setText(it.studentId)
        }

        // Xử lý sự kiện nút Lưu
        btnSave.setOnClickListener {
            val fullName = edtFullName.text.toString()
            val studentId = edtStudentId.text.toString()

            if (fullName.isNotEmpty() && studentId.isNotEmpty()) {
                val resultStudent = Student(fullName, studentId)
                intent.putExtra("student", resultStudent)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }
}
