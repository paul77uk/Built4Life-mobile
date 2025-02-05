package com.example.built4life.data

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.built4life.R
import com.example.built4life.data.entities.Program
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

class PrepopulateRoomCallback(private val context: Context) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO).launch {
            prePopulatePrograms(context)
        }
    }

    private suspend fun prePopulatePrograms(context: Context) {
        try {
            val programDao = Built4LifeDatabase.getDatabase(context).programDao()

            val programList: JSONArray =
                context.resources.openRawResource(R.raw.programs).bufferedReader().use {
                    JSONArray(it.readText())
                }

            programList.takeIf { it.length() > 0 }?.let { list ->
                for (index in 0 until list.length()) {
                    val programObj = list.getJSONObject(index)
                    programDao.insertProgram(
                        Program(
                            title = programObj.getString("title")
                        )
                    )

                }
                Log.e("Built4Life App", "successfully pre-populated programs into database")
            }
        } catch (exception: Exception) {
            Log.e(
                "Program App",
                exception.localizedMessage ?: "failed to pre-populate programs into database"
            )
        }

    }
}