package com.example.mymusicplayer.util

object TestData {

    val  successResponse =
        getInputStream("manifest.json")?.bufferedReader().use {
            it?.readText() ?: ""
        }

    val  malFormedErrorResponse =
        getInputStream("malformed_error.json")?.bufferedReader().use {
            it?.readText() ?: ""
        }

    val  errorResponse =
        getInputStream("error.json")?.bufferedReader().use {
            it?.readText() ?: ""
        }

    private fun getInputStream(file:String) = javaClass.classLoader?.getResourceAsStream(file)
}

