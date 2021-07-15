package com.task.stagger

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.task.stagger.databinding.ActivityItemBinding

class MainActivityAdapter(private val imageList: ArrayList<String>, private val context: Context): RecyclerView.Adapter<MainActivityAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ActivityItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.image
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  {
       val view = ActivityItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(imageList[position])
            .listener(object : RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                   return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.itemView.setOnClickListener {
                        val intent = Intent(context, FullImageActivity::class.java)
                        intent.putExtra("image", imageList[position])
                        context.startActivity(intent)
                    }
                    return false
                }
            })
            .placeholder(R.drawable.progress_image_animation)
            .error(R.drawable.default_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .fitCenter()
            .override(500,500)
            .dontAnimate()
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}