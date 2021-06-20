package org.om.onestepjob.ui.home.stacked

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.om.onestepjob.databinding.ItemStackedJobBinding
import org.om.onestepjob.model.Job

class StackedJobsAdapter(private var jobs: List<Job>) :
    RecyclerView.Adapter<StackedJobsAdapter.StackedJobViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StackedJobViewHolder {
        val binding = ItemStackedJobBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        return StackedJobViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return jobs.size
    }

    override fun onBindViewHolder(holder: StackedJobViewHolder, position: Int) {
        holder.bind()
    }

    fun getJobsList() = jobs


    inner class StackedJobViewHolder(val binding: ItemStackedJobBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.job = jobs[adapterPosition]
        }
    }
}