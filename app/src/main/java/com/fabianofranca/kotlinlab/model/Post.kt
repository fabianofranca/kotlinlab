package com.fabianofranca.kotlinlab.model

data class Post(
    var userId: Long,
    var id: Int,
    var title: String,
    var body: String
)