package com.example.basicstatecodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.basicstatecodelab.ui.theme.BasicStateCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicStateCodelabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    WellnessScreen()
                }
            }
        }
    }
}

/*
* 1. Main Activity calls WellnessScreen()
* 2. WellnessScreen() calls StatefulCounter(modifier) & WellnessTaskList().
*   They are aligned in a column
* 3. StatefulCounter is a class that contains a button to increment an Int and output a
*   Text.
*   3a. When StatefulCounter is called, it passes into to StatelessCounter. Which:
*       1. Receives state from StatefulCounter
*       2. Draws Button
*       3. Somehow when the button is pressed, count is incremented the the function and recomposition takes place.
*           Note: Current theory is based on the {count++} being passed to the StatelessCounter
* 4. WellnessTaskList() is fed by a data class (WellnessTask) and fed via
*   WellnessTaskList()
* 5. A list is generated in WellnessTaskList() which is sent to WellnessTaskItem and that
* is where the list is drawn.
* */

/*Reasoning for using an Observable MutableList:
* Using mutable objects for this, such as ArrayList<T> or mutableListOf, won't work. These types
* won't notify Compose that the items in the list have changed and schedule a recomposition of the
* UI. You need a different API.

* You need to create an instance of MutableList that is observable by Compose. This structure lets
* Compose track changes to recompose the UI when items are added or removed from the list.
* */