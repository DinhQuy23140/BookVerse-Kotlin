package com.example.readbookkotlin.data.source.firebase

import android.content.Context
import com.example.readbookkotlin.data.preferences.SharePrefConstants
import com.example.readbookkotlin.data.preferences.UserSharedPref
import com.example.readbookkotlin.domain.model.User
import com.example.readbookkotlin.util.Constants
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await

class UserFirebaseSource(
    private val context: Context,
    private val sharedPref: UserSharedPref = UserSharedPref(context)
) {
    private val firestore = FirebaseProvider.firebaseFirestore

    fun getDob():String? {
        return sharedPref.getDoB()
    }

    fun getEmail():String? {
        return sharedPref.getEmail()
    }

    fun getImage():String? {
        return sharedPref.getImg()
    }

    fun getPhoneNumber():String? {
        return sharedPref.getPhone()
    }

    fun getUserName():String? {
        return sharedPref.getUserName()
    }

    suspend fun checkEmailExists():Boolean {
        val email:String = sharedPref.getEmail() ?: return false
        val snapshot = firestore.collection(FirebaseConstants.KEY_COLLECTION_USERS)
            .whereEqualTo(SharePrefConstants.KEY_EMAIL, email)
            .get()
            .await()
        return !snapshot.documents.isEmpty()
    }

    suspend fun login(user:User, isSaveInf:Boolean):Boolean {
        val email:String = user.email
        val snapshot = firestore.collection(FirebaseConstants.KEY_COLLECTION_USERS)
            .document(email)
            .get()
            .await()
        if (snapshot.exists()) {
            sharedPref.saveEmail(snapshot.get(SharePrefConstants.KEY_EMAIL).toString())
            sharedPref.savePassword(snapshot.get(SharePrefConstants.KEY_PASSWORD).toString())
            sharedPref.setIsLogin(true)
            sharedPref.setSaveInf(isSaveInf)
            sharedPref.saveUserName(snapshot.get(SharePrefConstants.KEY_NAME).toString())
            sharedPref.saveImg(snapshot.get(SharePrefConstants.KEY_IMAGE).toString())
            sharedPref.savePhone(snapshot.get(SharePrefConstants.KEY_PHONE).toString())
            sharedPref.saveDoB(snapshot.get(SharePrefConstants.KEY_DOB).toString())
            sharedPref.saveSex(snapshot.get(SharePrefConstants.KEY_SEX).toString())
            return true
        } else return false
    }

    fun isLogin():Boolean {
        return sharedPref.isLogin()
    }

    fun setIsLogin(status:Boolean) {
        sharedPref.setIsLogin(status)
    }

    suspend fun signup(data:Map<String, String>):Boolean {
        val email:String? = data.get(SharePrefConstants.KEY_EMAIL)
        val password:String? = data.get(SharePrefConstants.KEY_PASSWORD)
        if (email != null && password != null) {
            firestore.collection(FirebaseConstants.KEY_COLLECTION_USERS)
                .document(data.get(SharePrefConstants.KEY_EMAIL) ?: "")
                .set(data)
                .await()
            return true
        } else return false
    }

    suspend fun updateUser(data:Map<String, String>):Boolean {
        val email:String = sharedPref.getEmail() ?: return false
        val snapshot:DocumentSnapshot = firestore.collection(FirebaseConstants.KEY_COLLECTION_USERS)
            .document(email)
            .get()
            .await()
        if (snapshot.exists()) {
            snapshot.reference.update(data).await()
            sharedPref.saveUserName(data.get(SharePrefConstants.KEY_NAME) ?: "")
            sharedPref.saveImg(data.get(SharePrefConstants.KEY_IMAGE) ?: "")
            sharedPref.savePhone(data.get(SharePrefConstants.KEY_PHONE) ?: "")
            sharedPref.saveEmail(data.get(SharePrefConstants.KEY_EMAIL) ?: "")
            sharedPref.saveDoB(data.get(SharePrefConstants.KEY_DOB) ?: "")
            return true
        } else return false
    }
}