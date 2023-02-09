package com.example.basicstatecodelab

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel


//BIG NOTE: TO USE VIEWMODEL ADD THIS DEPENDENCY:
//implementation "androidx.lifecycle:lifecycle-viewmodel-compose:{latest_version}"
//Location: app/build.gradle
class WellnessViewModel: ViewModel() {
    private val _tasks = getWellnessTask().toMutableStateList()
    val tasks: List<WellnessTask>
        get() = _tasks

    fun remove(item: WellnessTask){
        _tasks.remove(item)
    }

    fun changeTaskChecked(item: WellnessTask, checked: Boolean) =
        tasks.find {it.id == item.id}?.let { task ->
            task.checked = checked
        }
}