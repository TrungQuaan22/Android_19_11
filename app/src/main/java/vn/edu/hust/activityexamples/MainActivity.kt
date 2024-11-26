package vn.edu.hust.activityexamples

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import vn.edu.hust.activityexamples.model.Student

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val students = mutableListOf<Student>()
    private val studentNames = mutableListOf<String>() // Danh sách tên hiển thị trong ListView
    private var selectedStudentIndex: Int = -1 // Index của sinh viên được chọn

    companion object {
        const val REQUEST_ADD = 1
        const val REQUEST_EDIT = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listViewStudents)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, studentNames)
        listView.adapter = adapter

        // Đăng ký context menu cho ListView
        registerForContextMenu(listView)

        // Xử lý sự kiện click để chọn sinh viên
        listView.setOnItemClickListener { _, _, position, _ ->
            selectedStudentIndex = position
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add_new -> {
                // Mở màn hình thêm sinh viên
                val intent = Intent(this, AddEditStudentActivity::class.java)
                startActivityForResult(intent, REQUEST_ADD)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Tạo context menu
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        selectedStudentIndex = info.position

        return when (item.itemId) {
            R.id.menu_edit -> {
                // Mở màn hình chỉnh sửa sinh viên
                val intent = Intent(this, AddEditStudentActivity::class.java)
                intent.putExtra("student", students[selectedStudentIndex])
                startActivityForResult(intent, REQUEST_EDIT)
                true
            }
            R.id.menu_remove -> {
                // Xóa sinh viên
                students.removeAt(selectedStudentIndex)
                studentNames.removeAt(selectedStudentIndex)
                adapter.notifyDataSetChanged()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            val student = data.getSerializableExtra("student") as Student

            when (requestCode) {
                REQUEST_ADD -> {
                    // Thêm sinh viên mới
                    students.add(student)
                    studentNames.add("${student.fullName} - ${student.studentId}")
                }
                REQUEST_EDIT -> {
                    // Cập nhật thông tin sinh viên
                    students[selectedStudentIndex] = student
                    studentNames[selectedStudentIndex] = "${student.fullName} - ${student.studentId}"
                }
            }
            adapter.notifyDataSetChanged()
        }
    }
}
