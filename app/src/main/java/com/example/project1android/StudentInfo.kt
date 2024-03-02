package com.example.project1android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.content.res.Configuration

class StudentInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_info)
    }

    fun onGoBackTapped(view: View) {

        startActivity(Intent(this, RecordGrades::class.java))
        // Show a Toast message
        Toast.makeText(this, "Go Back to Calculate Grades", Toast.LENGTH_SHORT).show()
    }
}