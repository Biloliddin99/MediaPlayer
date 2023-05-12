package com.example.musicplayer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.databinding.ItemRvBinding
import com.example.musicplayer.models.MyMusic

class MyRvAdapter(
    val context: Context,
    val list: ArrayList<MyMusic>,
    val rvClickInterface: RvClickInterface
) : RecyclerView.Adapter<MyRvAdapter.Vh>() {

    inner class Vh(private val itemRvBinding: ItemRvBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {

        @SuppressLint("SuspiciousIndentation")
        fun onBind(myMusic: MyMusic, position: Int) {
            itemRvBinding.tvName.text = myMusic.title
            itemRvBinding.tvArtist.text = myMusic.artist
            val imageTxt = itemRvBinding.tvName.text

            if (imageTxt.length<10){
                itemRvBinding.image.setImageResource(R.drawable.image_item3)
            }else if (imageTxt.length in 11..20){
                itemRvBinding.image.setImageResource(R.drawable.img_item)
            }else{
                itemRvBinding.image.setImageResource(R.drawable.image_item2)

            }


       /**
            doesn't work getting image way
            if (myMusic.imagePath!=""){
                val image1 = BitmapFactory.decodeFile(myMusic.imagePath)
                itemRvBinding.image.setImageBitmap(image1)
            }
            Glide.with(context)
                .load(list[position].imagePath)
                .into(itemRvBinding.image)
*/

            itemRvBinding.apply {
                root.setOnClickListener {
                    rvClickInterface.itemClick(myMusic, position)
                }

                imageMore.setOnClickListener {
                    rvClickInterface.moreClick(myMusic, position)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {

        holder.onBind(list[position], position)


    }

}

interface RvClickInterface {
    fun moreClick(myMusic: MyMusic, position: Int)
    fun itemClick(myMusic: MyMusic, position: Int)
}