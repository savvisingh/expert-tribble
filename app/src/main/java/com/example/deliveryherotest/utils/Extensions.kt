package com.example.deliveryherotest.utils

fun Double.roundTo(n : Int) : Double {
    return "%.${n}f".format(this).toDouble()
}