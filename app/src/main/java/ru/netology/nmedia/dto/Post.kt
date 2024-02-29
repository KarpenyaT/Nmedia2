package ru.netology.nmedia.dto

data class Post (
    val id:Int,
    val author:String,
    val published:String,
    val content:String,
    val likes:Int=0,
    val likedByMe:Boolean=false,
    val shareCount:Int=0,
    val shareByMe:Boolean=false,
    val viewCount:Int=0
)