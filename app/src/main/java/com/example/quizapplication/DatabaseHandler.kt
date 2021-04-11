package com.example.quizapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "Quiz"

        private val CHAPITRE = "chapitre"
        private val ID1 = "_id"
        private val NOM = "nom"
        private val DESCRIPTION = "description"

        private val QUESTIONS = "question"
        private val ID2 = "_id"
        private val QUESTION = "question"
        private val OPTION1 = "option1"
        private val OPTION2 = "option2"
        private val OPTION3 = "option3"
        private val OPTION4 = "option4"
        private val CORRECT = "correct"
        private val CHAPITRE_ID = "chapitre_id"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val CREATE_CHAPITRE_TABLE = ("CREATE TABLE " + CHAPITRE + "(" +
                ID1 + " INTEGER PRIMARY KEY," +
                NOM + " TEXT," +
                DESCRIPTION + " TEXT" + ")")
        val CREATE_QUESTIONS_TABLE = ("CREATE TABLE " + QUESTIONS + "(" +
                ID2 + " INTEGER PRIMARY KEY," +
                QUESTION + " TEXT," +
                OPTION1 + " TEXT," +
                OPTION2 + " TEXT," +
                OPTION3 + " TEXT," +
                OPTION4 + " TEXT," +
                CORRECT + " INTEGER," +
                CHAPITRE_ID + " INTEGER" + ")")

        db?.execSQL(CREATE_CHAPITRE_TABLE)
        db?.execSQL(CREATE_QUESTIONS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $CHAPITRE")
        db!!.execSQL("DROP TABLE IF EXISTS $QUESTIONS")
        onCreate(db)
    }

    fun addChapitre(ch: Chapitre): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID1, ch.id)
        contentValues.put(NOM, ch.nom)
        contentValues.put(DESCRIPTION, ch.description)

        val success = db.insert(CHAPITRE, null, contentValues)

        db.close() // Closing database connection
        return success
    }

    fun addQuestion(qst: Question): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()

        contentValues.put(ID2, qst.id)
        contentValues.put(QUESTION, qst.question)
        contentValues.put(OPTION1, qst.optionOne)
        contentValues.put(OPTION2, qst.optionTwo)
        contentValues.put(OPTION3, qst.optionThree)
        contentValues.put(OPTION4, qst.optionFour)
        contentValues.put(CORRECT, qst.correctAnswer)
        contentValues.put(CHAPITRE_ID, 2)

        val success = db.insert(QUESTIONS, null, contentValues)

        db.close() // Closing database connection
        return success
    }

    fun viewChapitre(): ArrayList<Chapitre> {

        val chList: ArrayList<Chapitre> = ArrayList<Chapitre>()
        val titres: ArrayList<String> = ArrayList()

        // Query to select all the records from the table.
        val selectQuery = "SELECT  * FROM $CHAPITRE"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var nom: String
        var description: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(ID1))
                nom = cursor.getString(cursor.getColumnIndex(NOM))
                description = cursor.getString(cursor.getColumnIndex(DESCRIPTION))

                val ch = Chapitre(id = id, nom = nom, description = description)
                chList.add(ch)
            } while (cursor.moveToNext())
        }
        return chList
    }

    fun viewQuestion(id: Int): ArrayList<Question> {

        val qstList: ArrayList<Question> = ArrayList<Question>()

        // Query to select all the records from the table.
        val selectQuery = "SELECT  * FROM $QUESTIONS WHERE $CHAPITRE_ID=$id"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var question: String
        var option1: String
        var option2: String
        var option3: String
        var option4: String
        var correctAnswer: Int
        var chosenAnswer: Int

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(ID1))
                question = cursor.getString(cursor.getColumnIndex(QUESTION))
                option1 = cursor.getString(cursor.getColumnIndex(OPTION1))
                option2 = cursor.getString(cursor.getColumnIndex(OPTION2))
                option3 = cursor.getString(cursor.getColumnIndex(OPTION3))
                option4 = cursor.getString(cursor.getColumnIndex(OPTION4))
                correctAnswer = cursor.getInt(cursor.getColumnIndex(CORRECT))
                chosenAnswer = -1

                val qst = Question(id, question, option1, option2, option3, option4, correctAnswer, chosenAnswer)
                qstList.add(qst)
            } while (cursor.moveToNext())
        }
        return qstList
    }

}