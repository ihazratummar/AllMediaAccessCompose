package com.hazrat.mediastorge

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

/**
 * @author Hazrat Ummar Shaikh
 */

class MainViewModel(
    private val mediaReader: MediaReader
) : ViewModel() {

    val files = mutableStateListOf<MediaFile>()


    init {
        loadMediaFiles()
    }

    fun loadMediaFiles() {
        files.clear()
        files.addAll(mediaReader.getAllMediaFiles())

    }

}