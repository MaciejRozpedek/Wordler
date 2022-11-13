package com.macroz.wordler

import android.content.Context
import kotlin.math.round
import kotlin.math.sqrt

class Utensils(context: Context) {
    fun nextFibonacci(num: Int): Int {
        val ret: Double = num * (1 + sqrt(5.0)) / 2.0
        return round(ret).toInt()
    }
}