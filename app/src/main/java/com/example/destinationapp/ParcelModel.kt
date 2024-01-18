package com.example.destinationapp

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng

//Prenesie data medzi roznymi aktivitami
data class ParcelModel(
    val correctPlace: LatLng?,
    val guessedPlace: LatLng?,
    val score: Int,
    val distance: Int
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(LatLng::class.java.classLoader),
        parcel.readParcelable(LatLng::class.java.classLoader),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(correctPlace, flags)
        parcel.writeParcelable(guessedPlace, flags)
        parcel.writeInt(score)
        parcel.writeInt(distance)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParcelModel> {
        override fun createFromParcel(parcel: Parcel): ParcelModel {
            return ParcelModel(parcel)
        }

        override fun newArray(size: Int): Array<ParcelModel?> {
            return arrayOfNulls(size)
        }
    }
}
