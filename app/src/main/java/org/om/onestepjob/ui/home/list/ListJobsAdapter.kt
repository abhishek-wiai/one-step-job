package org.om.onestepjob.ui.home.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.om.onestepjob.databinding.ItemListJobBinding
import org.om.onestepjob.model.Job

class ListJobsAdapter(private var jobs: List<Job> = emptyList()) :
    RecyclerView.Adapter<ListJobsAdapter.ListJobsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListJobsViewHolder {
        val binding = ItemListJobBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        return ListJobsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return jobs.size
    }

    override fun onBindViewHolder(holder: ListJobsViewHolder, position: Int) {
        holder.bind()
    }


    inner class ListJobsViewHolder(val binding: ItemListJobBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.job = jobs[adapterPosition]
        }
    }
}