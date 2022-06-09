package com.bangkit.capstone.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bangkit.capstone.R
import com.bangkit.capstone.model.FoodPredictResponse
import com.bangkit.capstone.model.RecommendedItem

class ScreenSlidePageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_screen_slide_page, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey("nama_makanan") }?.apply {
//            val listOfRecommend: List<RecommendedItem> = getParcelableArrayList("object")!!


            val textView: TextView = view.findViewById(R.id.tv_makanan)
            textView.text = getString("nama_makanan").toString()
//            textView.text = getInt("object").toString()
        }

    }
}