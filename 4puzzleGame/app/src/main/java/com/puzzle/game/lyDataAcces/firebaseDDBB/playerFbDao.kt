package com.puzzle.game.lyDataAcces.firebaseDDBB

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.puzzle.game.lyDataAcces.firebaseDDBB.Entities.PlayerFbEntity
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class playerFbDao : fbAccessDDBB() {

    companion object {
        const val tableName = PlayerFbEntity.TABLE_NAME;
        private var user: PlayerFbEntity? = null
    }


    val playerListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            user = dataSnapshot.getValue<PlayerFbEntity>()
            // ...
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }

    fun SetUser(usr: PlayerFbEntity)
    {
        user = usr
        //getReferenciaClave(tableName).child(usr.getPlayerId()).addValueEventListener(playerListener)
    }

    fun writeUser(userdata: PlayerFbEntity) {

        val userValues = userdata.toMap()
        getReferenciaClave(tableName).child(userdata.getPlayerId()).updateChildren(userValues)
        SetUser(userdata)
    }

}