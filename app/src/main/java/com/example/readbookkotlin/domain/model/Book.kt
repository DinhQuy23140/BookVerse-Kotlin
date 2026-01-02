package com.example.readbookkotlin.domain.model

data class Book(
    var id: Int,
    var download_count: Int,
    var title: String,
    var media_type: String,
    var subjects: List<String>,
    var bookshelves: List<String>,
    var languages: List<String>,
    var users: List<String>,
    var summaries: List<String>,
    var authors: List<Person>,
    var translators:List<Person>,
    var copyright:Boolean,
    var formats: Map<String, String>,
)