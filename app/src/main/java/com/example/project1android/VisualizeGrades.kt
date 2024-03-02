package com.example.project1android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class VisualizeGrades : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    // Declare listofGrades as a member variable of the class
    private val listofGrades: MutableList<Map<String, String>> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.visualize_grades)

        recyclerView = findViewById(R.id.recyclerView)

        // Setup RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Retrieve saved grade data from SharedPreferences
        val sharedPreferences = getSharedPreferences("grades", MODE_PRIVATE)
        val jsonString = sharedPreferences.getString("grades", null)
        val listofGrades: MutableList<Map<String, String>> = jsonString?.let {
            Gson().fromJson(it, object : TypeToken<MutableList<Map<String, String>>>() {}.type)
        } ?: mutableListOf()
        recyclerView.adapter = GradesAdapter(listofGrades)
    }

    fun backButtonTapped(view: View) {

        navigateToRecordGrades()
    }
    private fun navigateToRecordGrades() {
        val intent = Intent(this, RecordGrades::class.java)
        startActivity(intent)
        // Finish current activity to prevent navigating back to it from the RecordGradesActivity
        finish()
    }

    fun removeButtonTapped(view: View) {

        // Retrieve SharedPreferences
        val sharedPreferences = getSharedPreferences("grades", MODE_PRIVATE)

        // Remove data associated with the key 'grades'
        sharedPreferences.edit().remove("grades").apply()

        // Clear the ArrayList
        listofGrades.clear()
        // Clear the RecyclerView adapter
        recyclerView.adapter = null

        // Showing a Toast message
        Toast.makeText(this, "Grades Removed Successfully", Toast.LENGTH_SHORT).show()

    }
}

