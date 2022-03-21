package com.example.socialapp.model

import android.os.Parcel
import android.os.Parcelable

data class PostX(
    var _id: String?,
    var content: Content,
    var total_like: Int?,
    var total_comment: Int?,
    var type: String?,
    var user_id: String?,
    var created_at: String?,
    var updated_at: String?,
    var __v: Int?,
    var isLike: Boolean?,
    var user: UserX
) :Parcelable{


    constructor(parcel: Parcel) : this(
        parcel.readString(),
        TODO("content"),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        TODO("user")
    ) {
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(type)
        parcel.writeString(user_id)
        parcel.writeString(created_at)
        parcel.writeString(updated_at)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostX> {
        override fun createFromParcel(parcel: Parcel): PostX {
            return PostX(parcel)
        }

        override fun newArray(size: Int): Array<PostX?> {
            return arrayOfNulls(size)
        }
    }
}