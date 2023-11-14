package com.example.mahnyoh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mahnyoh.data.StepInfo

class StepAdapter(private val stepList: List<StepInfo>) : RecyclerView.Adapter<StepAdapter.StepViewHolder>() {

    class StepViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val tvStepCount: TextView = view.findViewById(R.id.tvStepCount)
        val tvDistanceCovered: TextView = view.findViewById(R.id.tvDistanceCovered)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.step_count, parent, false)
        return StepViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        val stepInfo = stepList[position]
        holder.tvDate.text = stepInfo.date
        holder.tvStepCount.text = stepInfo.stepCount.toString()
        holder.tvDistanceCovered.text = "${stepInfo.distanceCovered} km" // Adjust the unit if necessary
    }

    override fun getItemCount() = stepList.size
}
