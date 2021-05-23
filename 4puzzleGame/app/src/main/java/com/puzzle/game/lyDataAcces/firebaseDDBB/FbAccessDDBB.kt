package com.puzzle.game.lyDataAcces.firebaseDDBB

import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import com.google.firebase.database.*

import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


open class FbAccessDDBB {

    companion object
    {
        const val DDBBURL = "https://pieces-93657-default-rtdb.europe-west1.firebasedatabase.app/";
    }

    lateinit var ref : DatabaseReference
    private var databaseInstance: FirebaseDatabase = Firebase.database(DDBBURL)
    private var rootDatabaseRef: DatabaseReference = databaseInstance.reference


    fun GetDatabase(): FirebaseDatabase {
        return databaseInstance
    }

    fun setRef(key: String) {
        ref = getReferenciaClave(key)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                Log.d(TAG, "Value is: $value")

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun getReferenciaRaiz(): DatabaseReference {
        return rootDatabaseRef
    }

    fun getReferenciaClave(key: String): DatabaseReference {
        return GetDatabase().getReference(key)
    }
}