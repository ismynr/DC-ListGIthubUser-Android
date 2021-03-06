package com.ismynr.consumerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
data class Favorite(
    var name: String? = null,
    var username: String? = null,
    var followers: String? = null,
    var following: String? = null,
    var location: String? = null,
    var repository: String? = null,
    var company: String? = null,
    var avatar: String? = null,
    var isFav: String? = null
) : Parcelable