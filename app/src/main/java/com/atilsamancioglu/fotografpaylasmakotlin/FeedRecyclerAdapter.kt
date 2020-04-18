package com.atilsamancioglu.fotografpaylasmakotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row.view.*

class FeedRecyclerAdapter(val postList : ArrayList<Post>) : RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder>() {

    class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_row,parent,false)
        return PostHolder(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.itemView.item_row_kullanici_email.text = postList[position].kullaniciEmail
        holder.itemView.item_row_kullanici_yorum.text = postList[position].kullaniciYorum
        Picasso.get().load(postList[position].gorselUrl).into(holder.itemView.item_row_imageview)
    }
}