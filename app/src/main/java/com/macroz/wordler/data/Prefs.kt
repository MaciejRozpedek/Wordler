package com.macroz.wordler.data

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("DATA", Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    fun setNumOfNewCards(deckName: String, numOfCards: Int) {
        val pom: Int = preferences.getInt(deckName, -1)
        editor.remove("NUM_OF_NEW_CARDS_$deckName")
        editor.putInt("NUM_OF_NEW_CARDS_$deckName", numOfCards)
        editor.commit()
        if (pom == -1) {
            setNumOfNewCardsLeft(deckName, numOfCards)
        } else {
            setNumOfNewCardsLeft(deckName, numOfCards - pom + getNumOfNewCardsLeft(deckName))
        }
    }

    private fun setNumOfNewCardsLeft(deckName: String, numOfNewCardsLeft: Int) {
        editor.remove("NUM_OF_NEW_CARDS_$deckName")
        editor.putInt("NUM_OF_NEW_CARDS_$deckName", numOfNewCardsLeft)
        editor.commit()
    }

    fun getNumOfNewCards(deckName: String): Int {
        return preferences.getInt("NUM_OF_NEW_CARDS_$deckName", -1)
    }

    fun getNumOfNewCardsLeft(deckName: String): Int {
        return preferences.getInt("NUM_OF_NEW_CARDS_LEFT_$deckName", 10)
    }

    fun getSessionNum(deckName: String): Int {
        val pom: Int = preferences.getInt("SESSION_NUM_$deckName", 0)
        if (pom == 0) {
            editor.putInt("SESSION_NUM_$deckName", 0)
            editor.commit()
        }
        return pom
    }

    fun nextSession(deckName: String) {
        var pom: Int = preferences.getInt("SESSION_NUM_$deckName", -1)
        pom++
        editor.remove("SESSION_NUM_$deckName")
        editor.putInt("SESSION_NUM_$deckName", pom)
        editor.commit()
    }
}