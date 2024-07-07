package com.example.recipesearchapp.data.remote.model.RandomRecipeModel

import android.os.Parcel
import android.os.Parcelable

data class Ingredient2(
    val id: Long,
    val name: String,
    val localizedName: String,
    val image: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(localizedName)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<com.example.recipesearchapp.data.remote.model.RandomRecipeModel.Ingredient2> {
        override fun createFromParcel(parcel: Parcel): com.example.recipesearchapp.data.remote.model.RandomRecipeModel.Ingredient2 {
            return com.example.recipesearchapp.data.remote.model.RandomRecipeModel.Ingredient2(
                parcel
            )
        }

        override fun newArray(size: Int): Array<com.example.recipesearchapp.data.remote.model.RandomRecipeModel.Ingredient2?> {
            return arrayOfNulls(size)
        }
    }
}
