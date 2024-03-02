package com.example.project1android

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class RecordGrades : AppCompatActivity() {

    var courses = arrayOf<String?>("Quiz", "Lab", "Project", "Midterm", "Final")

    private lateinit var courseNumberTextView: TextView
    private lateinit var gradeTypeSpinner: Spinner
    private lateinit var percentageRadioGroup: RadioGroup
    private lateinit var gradeReceivedEditText: EditText
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.record_grades)
        val spin = findViewById<Spinner>(R.id.spinner)
        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this, android.R.layout.simple_spinner_item, courses
        )

        ad.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spin.adapter = ad

        // Initialize UI components
        courseNumberTextView = findViewById(R.id.textView1)
        gradeTypeSpinner = findViewById(R.id.spinner)
        percentageRadioGroup = findViewById(R.id.radioGroup)
        gradeReceivedEditText = findViewById(R.id.editText2)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("grades", Context.MODE_PRIVATE)


        val studentInfoButton: Button = findViewById(R.id.showStudent)
        studentInfoButton.setOnClickListener {
            // Create an Intent to navigate to StudentInfo activity
            val intent = Intent(this, StudentInfo::class.java)
            startActivity(intent)

            // Show a Toast message
            Toast.makeText(this, "About the App Info", Toast.LENGTH_SHORT).show()


        }
    }

    fun recordGradeTapped(view: View) {
        val courseNumber = courseNumberTextView.text.toString()

        if (courseNumber.isEmpty()) {
            showError("Please enter course number")
            return
        }
        if (percentageRadioGroup.checkedRadioButtonId == -1) {
            showError("Please choose percentage of the grade")
            return
        }
        val selectedPercentage = getSelectedPercentage()
        val gradeReceivedString = gradeReceivedEditText.text.toString()

        if (gradeReceivedString.isEmpty()) {
            showError("Please enter grade received")
            return
        }
        val gradeReceived = gradeReceivedString.replace("%", "").trim().toIntOrNull()
        if (gradeReceived == null || gradeReceived > selectedPercentage) {
            showError("Grade received cannot be higher than the selected percentage")
            return
        }

        saveGrade(courseNumber, selectedPercentage, gradeReceived)
        // Show a Toast message to indicate that the event was recorded
        showToast("Grade recorded successfully")

        startActivity(Intent(this, VisualizeGrades::class.java))

    }

    private fun getSelectedPercentage(): Int {
        val selectedPercentageId = percentageRadioGroup.checkedRadioButtonId
        val selectedRadioButton = findViewById<RadioButton>(selectedPercentageId)
        return selectedRadioButton?.text.toString().replace("%", "").toInt()
    }

    // Function to save grade data to SharedPreferences
    private fun saveGrade(courseNumber: String, percentage: Int, gradeReceived: Int) {
        val gradeType = gradeTypeSpinner.selectedItem.toString()

        // Retrieve existing grades from SharedPreferences
        val sharedPreferences = getSharedPreferences("grades", MODE_PRIVATE)
        val jsonString = sharedPreferences.getString("grades", null)
        val listofGrades: MutableList<Map<String, String>> = jsonString?.let {
            Gson().fromJson(it, object : TypeToken<MutableList<Map<String, String>>>() {}.type)
        } ?: mutableListOf()

        // Create a new dictionary for the current grade
        val dictionary = mutableMapOf<String, String>()
        dictionary["courseNumber"] = courseNumber
        dictionary["gradeType"] = gradeType
        dictionary["percentage"] = percentage.toString()
        dictionary["gradeReceived"] = gradeReceived.toString()

        // Add the current grade to the list of grades
        listofGrades.add(dictionary)

        // Convert the list of grades back to JSON and save it in SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("grades", Gson().toJson(listofGrades))
        editor.apply()
    }
    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}



