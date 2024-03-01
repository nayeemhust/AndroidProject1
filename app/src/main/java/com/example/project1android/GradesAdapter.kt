package com.example.project1android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GradesAdapter(private val grades: MutableList<Map<String, String>>) : RecyclerView.Adapter<GradesAdapter.GradeViewHolder>() {

    class GradeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseNumberTextView: TextView = itemView.findViewById(R.id.courseNumber)
        val gradeTypeTextView: TextView = itemView.findViewById(R.id.gradeType)
        val percentageTextView: TextView = itemView.findViewById(R.id.percentage)
        val gradeReceivedTextView: TextView = itemView.findViewById(R.id.gradeReceived)
//        val rvLayout:RecyclerView = itemView.findViewById(R.id.rvDesign)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GradeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyler_layout, parent, false)
        return GradeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GradeViewHolder, position: Int) {
        val currentGrade = grades[position]


        // Concatenate additional strings with the values
        val courseNumberText = "Course Number: ${currentGrade["courseNumber"]}"
        val gradeTypeText = "Grade Type: ${currentGrade["gradeType"]}"
        val percentageText = "Percentage: ${currentGrade["percentage"]}"
        val gradeReceivedText = "Grade Received: ${currentGrade["gradeReceived"]}"
        ///
        //
        //

        // Set the concatenated strings to the TextViews
        holder.courseNumberTextView.text = courseNumberText
        holder.gradeTypeTextView.text = gradeTypeText
        holder.percentageTextView.text = percentageText
        holder.gradeReceivedTextView.text = gradeReceivedText
    }

    override fun getItemCount(): Int {
        return grades.size
    }
}
