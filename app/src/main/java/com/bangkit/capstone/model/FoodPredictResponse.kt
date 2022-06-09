package com.bangkit.capstone.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodPredictResponse(

	@field:SerializedName("data")
	val data: Data1,

	@field:SerializedName("status")
	val status: String
): Parcelable

@Parcelize
data class Snack(

	@field:SerializedName("gizi_needed")
	val giziNeeded: GiziNeeded,

	@field:SerializedName("recommended")
	val recommended: List<RecommendedItem>
): Parcelable

@Parcelize
data class Data1(

	@field:SerializedName("breakfast")
	val breakfast: Breakfast?,

	@field:SerializedName("lunch")
	val lunch: Lunch?,

	@field:SerializedName("dinner")
	val dinner: Dinner?,

	@field:SerializedName("snack")
	val snack: Snack?
): Parcelable

@Parcelize
data class Lunch(

	@field:SerializedName("gizi_needed")
	val giziNeeded: GiziNeeded,

	@field:SerializedName("recommended")
	val recommended: List<RecommendedItem>
): Parcelable

@Parcelize
data class Breakfast(

	@field:SerializedName("gizi_needed")
	val giziNeeded: GiziNeeded,

	@field:SerializedName("recommended")
	val recommended: List<RecommendedItem>
): Parcelable

@Parcelize
data class Gizi(

	@field:SerializedName("protein")
	val protein: Double,

	@field:SerializedName("karbohidrat_total")
	val karbohidratTotal: Double,

	@field:SerializedName("lemak_total")
	val lemakTotal: Double,

	@field:SerializedName("energi")
	val energi: Double
): Parcelable

@Parcelize
data class Dinner(

	@field:SerializedName("gizi_needed")
	val giziNeeded: GiziNeeded,

	@field:SerializedName("recommended")
	val recommended: List<RecommendedItem>
): Parcelable

@Parcelize
data class GiziNeeded(

	@field:SerializedName("protein")
	val protein: Double,

	@field:SerializedName("karbohidrat_total")
	val karbohidratTotal: Double,

	@field:SerializedName("lemak_total")
	val lemakTotal: Double,

	@field:SerializedName("energi")
	val energi: Double
): Parcelable

@Parcelize
data class RecommendedItem(

	@field:SerializedName("gizi")
	val gizi: Gizi,

	@field:SerializedName("nama_makanan")
	val namaMakanan: String
): Parcelable
