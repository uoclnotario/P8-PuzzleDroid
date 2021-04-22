package com.puzzle.game.lyLogicalBusiness

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import android.provider.CalendarContract.Calendars
import android.provider.CalendarContract.Events
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class Calendario(context: Context) : AppCompatActivity() {

    companion object {
        const val MY_ACCOUNT_NAME =  "Game 4 Puzzle"
        const val MY_DISPLAY_NAME =  "Game: 4 Puzzle"
    }

    fun checkCalendarId(): Long {
        var calendarId = getCalendarioId()
        if(calendarId < 0L)
        {
            println("No hay Calendario ID así que lo creamos")
            createCalendar()
            println("Volvemos a buscar el Calendario ID")
            calendarId = getCalendarioId()
            println("Calendario ID: $calendarId")
        }
        return calendarId
    }

    fun getCalendarioId(): Long {
        var idFinal: Long = -1
        try {

            val projection = arrayOf(Calendars._ID)
            val selection = Calendars.ACCOUNT_NAME +
                    " = ? AND " +
                    Calendars.ACCOUNT_TYPE +
                    " = ? "
            // use the same values as above:
            val selArgs = arrayOf(
                    MY_ACCOUNT_NAME,
                    CalendarContract.ACCOUNT_TYPE_LOCAL)
            val cursor = contentResolver.query(
                    Calendars.CONTENT_URI,
                    projection,
                    selection,
                    selArgs,
                    null)

            if (cursor!!.moveToFirst()) {
                idFinal = cursor.getLong(0)
            }

        }catch (e:Exception)
        {
            println("Get Calendar ID: $e")
        }finally {
            return idFinal
        }
    }

    fun createCalendar()
    {
        try {

            val values = ContentValues()
            values.put(
                    Calendars.ACCOUNT_NAME,
                    MY_ACCOUNT_NAME)
            values.put(
                    Calendars.ACCOUNT_TYPE,
                    CalendarContract.ACCOUNT_TYPE_LOCAL)
            values.put(
                    Calendars.NAME,
                    MY_DISPLAY_NAME)
            values.put(
                    Calendars.CALENDAR_DISPLAY_NAME,
                    MY_DISPLAY_NAME)
            values.put(
                    Calendars.CALENDAR_COLOR,
                    -0x10000)
            values.put(
                    Calendars.CALENDAR_ACCESS_LEVEL,
                    Calendars.CAL_ACCESS_OWNER)
            values.put(
                    Calendars.OWNER_ACCOUNT,
                    "account@4puzzle.local")
            values.put(
                    Calendars.CALENDAR_TIME_ZONE,
                    TimeZone.getDefault().displayName)
            values.put(
                    Calendars.SYNC_EVENTS,
                    1)
            val builder: Uri.Builder = Calendars.CONTENT_URI.buildUpon()
            builder.appendQueryParameter(
                    Calendars.ACCOUNT_NAME,
                    "com.4puzzle")
            builder.appendQueryParameter(
                    Calendars.ACCOUNT_TYPE,
                    CalendarContract.ACCOUNT_TYPE_LOCAL)
            builder.appendQueryParameter(
                    CalendarContract.CALLER_IS_SYNCADAPTER,
                    "true")
            val uri: Uri? = contentResolver.insert(builder.build(), values)

        }catch (e:Exception)
        {
            println("Create Calendar: $e")
        }
    }

     fun addEvento(savedGame: SavedGame) {
        var calendarId = checkCalendarId()
        println("AddEvent => Calendar ID: $calendarId")
        if (calendarId == -1L) {
            // no calendar account; react meaningfully
            return;
        }

        try {
            val startMillis: Long = savedGame.fechaInicio.time
            println("startMillis = $startMillis")
            val endMillis: Long = savedGame.fechaFin.time
            println("startMillis = $endMillis")
            val titulo: String = "Puzzle Completed" //getString(R.string.completePuzzle)
            println("titulo = $titulo")
            val description: String = "Puzzle Score: " + savedGame.score.toString() + "\r Time Spend: "+ savedGame.tiempo//getString(R.string.finishScore)+": "+savedGame.score.toString()
            println("description = $description")

            val values = ContentValues()
            values.put(Events.DTSTART, startMillis)
            values.put(Events.DTEND, endMillis)
            //values.put(Events.RRULE,
            //        "FREQ=DAILY;COUNT=20;BYDAY=MO,TU,WE,TH,FR;WKST=MO")
            values.put(Events.TITLE, titulo)
            values.put(Events.CALENDAR_ID, calendarId)
            values.put(Events.EVENT_TIMEZONE, TimeZone.getDefault().displayName)
            values.put(Events.DESCRIPTION, description)

            values.put(Events.ACCESS_LEVEL, Events.ACCESS_PRIVATE)
            values.put(Events.SELF_ATTENDEE_STATUS,
                    Events.STATUS_CONFIRMED)
            values.put(Events.ALL_DAY, 0)
            values.put(Events.GUESTS_CAN_INVITE_OTHERS, 0)
            values.put(Events.GUESTS_CAN_MODIFY, 0)
            values.put(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
            val uri = contentResolver.insert(Events.CONTENT_URI, values)

        }catch (e:Exception)
        {
            println("Create Calendar: $e")
        }
    }
}
