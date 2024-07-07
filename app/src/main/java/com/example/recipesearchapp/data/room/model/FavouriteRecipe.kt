package com.example.recipesearchapp.data.room.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favouriterecipe-table")
data class FavouriteRecipe(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val image: String,
    val readyInMinutes: Long,
    val servings: String,
    val pricePerServing: String,
    val ingredientsName: List<String>,
    val ingredientsImage: List<String>,
    val instructions: String,
    val stepNumber: List<String>,
    val step: List<String>,
    val equipmentsName: List<String>,
    val equipmentsImage: List<String>,
    val summary: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.createStringArrayList()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.createStringArrayList()!!,
        parcel.createStringArrayList()!!,
        parcel.createStringArrayList()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(image)
        parcel.writeLong(readyInMinutes)
        parcel.writeString(servings)
        parcel.writeString(pricePerServing)
        parcel.writeStringList(ingredientsName)
        parcel.writeStringList(ingredientsImage)
        parcel.writeString(instructions)
        parcel.writeStringList(stepNumber)
        parcel.writeStringList(step)
        parcel.writeStringList(equipmentsName)
        parcel.writeStringList(equipmentsImage)
        parcel.writeString(summary)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FavouriteRecipe> {
        override fun createFromParcel(parcel: Parcel): FavouriteRecipe {
            return FavouriteRecipe(parcel)
        }

        override fun newArray(size: Int): Array<FavouriteRecipe?> {
            return arrayOfNulls(size)
        }
    }
}
