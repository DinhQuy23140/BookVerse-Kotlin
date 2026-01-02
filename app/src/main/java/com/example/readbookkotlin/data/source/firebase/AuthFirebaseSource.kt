package com.example.readbookkotlin.data.source.firebase

import com.google.firebase.firestore.FirebaseFirestore

class AuthFirebaseSource {
    private val firestore = FirebaseProvider.firebaseFirestore

    fun getAllBook():List<Book>
}