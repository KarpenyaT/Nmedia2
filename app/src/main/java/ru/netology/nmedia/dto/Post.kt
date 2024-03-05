package ru.netology.nmedia.dto

data class Post (
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likes: Long = 0,
    val likedByMe: Boolean = false,
    val shareCount: Long = 0,
    val shareByMe: Boolean = false,
    val viewCount: Long = 0
)