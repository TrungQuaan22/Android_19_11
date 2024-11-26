package vn.edu.hust.activityexamples.model

import java.io.Serializable

data class Student(
    val fullName: String,
    val studentId: String
) : Serializable
