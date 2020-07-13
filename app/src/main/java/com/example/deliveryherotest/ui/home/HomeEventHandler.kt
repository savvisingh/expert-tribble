package com.example.deliveryherotest.ui.home

sealed class HomeEventHandler {
    data class OpenDetails(val id: Int): HomeEventHandler()
}