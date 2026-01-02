package com.example.readbookkotlin.data.source.firebase

import com.google.firebase.firestore.FirebaseFirestore

object FirebaseProvider {
    val firebaseFirestore by lazy { FirebaseFirestore.getInstance() }
}