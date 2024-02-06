package com.macroz.wordler.data

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("DATA", Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    fun setNumOfNewCards(deckName: String, numOfCards: Int) {
        val lastNumOfNewCards: Int = preferences.getInt("NUM_OF_NEW_CARDS_$deckName", 10)
        if (lastNumOfNewCards != numOfCards) {
            editor.remove("LAST_NUM_OF_NEW_CARDS_$deckName")
            editor.putInt("LAST_NUM_OF_NEW_CARDS_$deckName", lastNumOfNewCards)
            editor.remove("NUM_OF_NEW_CARDS_$deckName")
            editor.putInt("NUM_OF_NEW_CARDS_$deckName", numOfCards)
            setIsNumOfNewCardsChanged(deckName, true)
            editor.commit()
            setNumOfNewCardsLeft(deckName, numOfCards - lastNumOfNewCards + getNumOfNewCardsLeft(deckName))
            setNumOfCardsInSession(deckName, numOfCards - lastNumOfNewCards + getNumOfCardsInSession(deckName))
        }
    }

    fun setLastNumOfNewCards(deckName: String, value: Int) {
        editor.remove("LAST_NUM_OF_NEW_CARDS_$deckName")
        editor.putInt("LAST_NUM_OF_NEW_CARDS_$deckName", value)
    }

    fun getLastNumOfNewCards(deckName: String): Int {
        return preferences.getInt("LAST_NUM_OF_NEW_CARDS_$deckName", -1)
    }

    // returns true if num of new cards has been changed
    fun isNumOfNewCardsChanged(deckName: String): Boolean {
        return preferences.getBoolean("IS_NUM_OF_NEW_CARDS_CHANGED_$deckName", false)
    }

    fun setIsNumOfNewCardsChanged(deckName: String, value: Boolean) {
        editor.remove("IS_NUM_OF_NEW_CARDS_CHANGED_$deckName")
        editor.putBoolean("IS_NUM_OF_NEW_CARDS_CHANGED_$deckName", value)
        editor.commit()
    }

    fun setNumOfNewCardsLeft(deckName: String, numOfNewCardsLeft: Int) {
        editor.remove("NUM_OF_NEW_CARDS_LEFT_$deckName")
        editor.putInt("NUM_OF_NEW_CARDS_LEFT_$deckName", numOfNewCardsLeft)
        editor.commit()
    }

    fun changeNumOfNewCardsLeft(deckName: String, change: Int) {
        val numOfNewCardsLeft: Int = getNumOfNewCardsLeft(deckName)
        setNumOfNewCardsLeft(deckName, numOfNewCardsLeft + change)
    }

    fun getNumOfNewCards(deckName: String): Int {
        return preferences.getInt("NUM_OF_NEW_CARDS_$deckName", 10)
    }

    fun getNumOfNewCardsLeft(deckName: String): Int {
        return preferences.getInt("NUM_OF_NEW_CARDS_LEFT_$deckName", 10)
    }

    fun setNumOfCardsInSession(deckName: String, numOfCardsInSession: Int) {
        editor.remove("NUM_OF_CARDS_IN_SESSION_$deckName")
        editor.putInt("NUM_OF_CARDS_IN_SESSION_$deckName", numOfCardsInSession)
        editor.commit()
    }

    fun getNumOfCardsInSession(deckName: String): Int {
        return preferences.getInt("NUM_OF_CARDS_IN_SESSION_$deckName", 0)
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
        val numOfNewCards: Int = getNumOfNewCards(deckName)
        setNumOfNewCardsLeft(deckName, numOfNewCards)
        setLastNumOfNewCards(deckName, numOfNewCards)
        setIsNumOfNewCardsChanged(deckName, false)
        editor.remove("SESSION_NUM_$deckName")
        editor.putInt("SESSION_NUM_$deckName", pom)
        editor.commit()
    }
}