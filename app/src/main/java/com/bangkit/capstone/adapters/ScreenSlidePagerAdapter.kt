package com.bangkit.capstone.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bangkit.capstone.model.RecommendedItem
import com.bangkit.capstone.ui.ScreenSlidePageFragment
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList


class ScreenSlidePagerAdapter(fa: FragmentActivity, data: String?) : FragmentStateAdapter(fa) {

//    val dummy = mutableListOf<String>("1", "2", "3", "4")
    val data = data

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment{
        val fragment = ScreenSlidePageFragment()
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putString("nama_makanan", getObjectFromString(data)?.get(position)?.namaMakanan?.capitalizeWords())
            putDouble("energi", getObjectFromString(data)?.get(position)?.gizi!!.energi)
            putDouble("karbohidrat_total", getObjectFromString(data)?.get(position)?.gizi!!.karbohidratTotal)
            putDouble("lemak_total", getObjectFromString(data)?.get(position)?.gizi!!.lemakTotal)
            putDouble("protein", getObjectFromString(data)?.get(position)?.gizi!!.protein)

//            putInt("object", position + 1)
        }
        return fragment
    }
    private fun getObjectFromString(jsonString: String?): List<RecommendedItem>? {
        val listType: Type? = object : TypeToken<ArrayList<RecommendedItem?>?>() {}.type
        return Gson().fromJson<List<RecommendedItem>>(jsonString, listType)
    }

    fun String.capitalizeWords(): String =
        split(" ").joinToString(" ") { it.lowercase().capitalize() }

//    open class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView)

//    override fun onBindViewHolder(
//        holder: FragmentViewHolder,
//        position: Int,
//        payloads: MutableList<Any>
//    ) {
////        val textView = holder.itemView.findViewById<TextView>(R.id.tv_makanan)
////        textView.text = "Makanan ${position}"
//        super.onBindViewHolder(holder, position, payloads)
//    }
}