package com.example.readbookkotlin.data.source.firebase

import android.content.Context
import com.example.readbookkotlin.data.preferences.UserSharedPref
import com.example.readbookkotlin.domain.model.Book
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class BookFirebaseSource(
    private val context: Context,
    private val sharedPref: UserSharedPref = UserSharedPref(context)
) {
    private val firestore = FirebaseProvider.firebaseFirestore

    suspend fun getAllBook():List<Book> {
        return firestore.collection(FirebaseConstants.KEY_COLLECTION_BOOKS)
            .get()
            .await()
            .documents
            .mapNotNull { it.toObject(Book::class.java) }
    }

    suspend fun getBookByTitle(search:String):List<Book> {
        return firestore.collection(FirebaseConstants.KEY_COLLECTION_BOOKS)
            .get()
            .await()
            .documents
            .mapNotNull { it.toObject(Book::class.java) }
            .filter { it.title.contains(search, ignoreCase = true) }
    }

    suspend fun getRecentBook():List<Book> {
        val email:String = sharedPref.getEmail() ?: return emptyList()
        val snapshot = firestore.collection(FirebaseConstants.KEY_COLLECTION_RECENTREAD)
            .document(email)
            .get()
            .await()
        val listBookId = snapshot?.get("BookId") as? List<*> ?: return emptyList()

        return listBookId.chunked(10).flatMap { chunk ->
            firestore.collection(FirebaseConstants.KEY_COLLECTION_BOOKS)
                .whereIn(FieldPath.documentId(), chunk)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Book::class.java) }
        }
    }

    suspend fun getViralBook():List<Book> {
        return firestore.collection(FirebaseConstants.KEY_COLLECTION_BOOKS)
            .orderBy("dowload_count")
            .limit(100)
            .get()
            .await()
            .documents
            .mapNotNull { it.toObject(Book::class.java) }
    }

    suspend fun getBookByTopic(topic:String):List<Book> {
        return firestore.collection(FirebaseConstants.KEY_COLLECTION_BOOKS)
            .get()
            .await()
            .documents
            .mapNotNull { it.toObject(Book::class.java) }
            .filter {
                book -> book.bookshelves.any {
                    shelf->shelf.lowercase().contains(topic.lowercase())
                }
            }
    }

    suspend fun getFavoriteBook():List<Book> {
        val email: String = sharedPref.getEmail() ?: return emptyList()
        return firestore.collection(FirebaseConstants.KEY_COLLECTION_BOOKS)
                .whereArrayContains(FirebaseConstants.KEY_COLLECTION_USERS, email)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Book::class.java) }
    }

    suspend fun addFavoriteBook(book: Book) {
        val email:String = sharedPref.getEmail() ?: throw IllegalStateException("Email not available")
        val data: MutableMap<String, Any> = HashMap()
        data.put("users", FieldValue.arrayUnion(email))
        firestore.collection(FirebaseConstants.KEY_COLLECTION_BOOKS)
            .document(book.id.toString())
            .set(data, SetOptions.merge())
            .await()
    }

    suspend fun deleteFavoriteBook(book: Book) {
        val email:String = sharedPref.getEmail() ?: throw IllegalStateException("Email not available")
        firestore.collection(FirebaseConstants.KEY_COLLECTION_BOOKS)
            .document(book.id.toString())
            .update("users", FieldValue.arrayRemove(email))
            .await()
    }

    suspend fun addRecentBook(book: Book) {
        val email:String = sharedPref.getEmail() ?: throw IllegalStateException("Email not available")
        val data:MutableMap<String, Any> = HashMap()
        data.put("BookId", FieldValue.arrayUnion(book.id))
        firestore.collection(FirebaseConstants.KEY_COLLECTION_RECENTREAD)
            .document(email)
            .set(data, SetOptions.merge())
            .await()
    }

    suspend fun isFavoriteBook(book: Book):Boolean {
        val email:String = sharedPref.getEmail() ?: return false
        val snapshot: DocumentSnapshot = firestore.collection(FirebaseConstants.KEY_COLLECTION_BOOKS)
            .document(book.id.toString())
            .get()
            .await()
        if (!snapshot.exists()) return false

        val users = snapshot.get("users") as? List<*> ?: return false
        return email in users
    }
}