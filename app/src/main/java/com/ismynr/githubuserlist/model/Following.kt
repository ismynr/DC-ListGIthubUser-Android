package com.ismynr.githubuserlist.model

data class Following(
    var name: String? = null,
    var username: String? = null,
    var followers: String? = null,
    var following: String? = null,
    var location: String? = null,
    var repository: String? = null,
    var company: String? = null,
    var avatar: String? = null
)