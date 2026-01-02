package com.example.readbookkotlin.domain.model

data class ListOfBook(
    var count:Int,
    var next:String,
    var previous:String,
    var results:List<Book>,
)
