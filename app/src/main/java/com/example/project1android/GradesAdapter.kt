package com.example.project1android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button

class GradesAdapter(private val grades: MutableList<Map<String, String>>) : RecyclerView.Adapter<GradesAdapter.GradeViewHolder>() {

    class GradeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseNumberTextView: TextView = itemView.findViewById(R.id.courseNumber)
        val gradeTypeTextView: TextView = itemView.findViewById(R.id.gradeType)
        val percentageTextView: TextView = itemView.findViewById(R.id.percentage)
        val gradeReceivedTextView: TextView = itemView.findViewById(R.id.gradeReceived)
        val progress: ProgressBar = itemView.findViewById(R.id.determinateBar)
        val percentageTextView2: TextView = itemView.findViewById(R.id.textView5)
        val removeButton: Button = itemView.findViewById(R.id.gradeRemoved)
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

        // Set the concatenated strings to the TextViews
        holder.courseNumberTextView.text = courseNumberText
        holder.gradeTypeTextView.text = gradeTypeText
        holder.percentageTextView.text = percentageText
        holder.gradeReceivedTextView.text = gradeReceivedText

        val progress = ((currentGrade["gradeReceived"]!!.toInt() * 100) / currentGrade["percentage"]!!.toInt()) ?: 0
        holder.percentageTextView2.text = "Total Achieved grade: $progress%"
        holder.progress.progress = progress

        // Set OnClickListener for the remove button
        holder.removeButton.setOnClickListener {
            removeGrade(position)
            
        }
    }

    override fun getItemCount(): Int {
        return grades.size
    }

    // Function to remove a grade at a specific position
    fun removeGrade(position: Int) {
        grades.removeAt(position)
        notifyItemRemoved(position)
    }
}

