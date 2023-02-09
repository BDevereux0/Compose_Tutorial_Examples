package com.example.basicstatecodelab

import android.annotation.SuppressLint
import android.inputmethodservice.Keyboard
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

    /*
    * Key thing to note on this page:
    * 1. IDK what is happenning with onCLose and why it works the way it does
    * but I do know that when showTask condition is met, the WellnessTaskItem is drawn
    * and when the x button is pressed, showTask is set to false. Which prevents
    * WellnessTaskItem from being called. Therefore, when the Add one button is pressed
    * again, the counter goes up but the WellnessTaskItem is not recomposed.
    * AND because count is > 0, recomposition takes place and the count is remembered.
    *
    * 2. However, when the clear water count is pressed, count is set to 0 the code in the condition
    * statement is not executed and therefore state is lost allowing the program to run as before.
    *
    * 3. I commented out a WaterCounter method that shows the above, but the codelab has me
    * revert. :/
    *
    * 4. Notice the stateHoisting taking place here. I've created two methods, one to maintain the state
    * the other executes the code I need. I could now use another method similar to StatelessCounter
    * that could react to the state from StatefulCounter.
    *
    * From the codeLab:
    * Key Point: When hoisting state, there are three rules to help you figure out where state should go:
    *
    * 1. State should be hoisted to at least the lowest common parent of all composables that use the state (read).
    * 2. State should be hoisted to at least the highest level it may be changed (write).
    * 3. If two states change in response to the same events they should be hoisted to the same level.
    *
    * You can hoist the state higher than these rules require, but if you don't hoist the state high enough,
    * it might be difficult or impossible to follow unidirectional data flow.
    * */

    @Composable
    fun StatelessCounter(count: Int, onIncrement: ()-> Unit, modifier: Modifier = Modifier){
        Column(modifier = modifier.padding(16.dp)) {
            if (count > 0){
                Text("You've had $count glasses.")
            }
            Button(
                onClick = onIncrement,
                Modifier.padding(top = 8.dp),
                enabled = count < 10
            ){
                Text("Add one")
            }
        }
    }

    @Composable
    fun StatefulCounter(modifier: Modifier = Modifier){
        var count by rememberSaveable{ mutableStateOf(0) }
            StatelessCounter(count, {count++}, modifier)
    }

    @Composable
    fun WaterCounter(modifier: Modifier = Modifier) {
        Column(modifier = modifier.padding(16.dp)) {
            var count by rememberSaveable { mutableStateOf(0) }
            if (count > 0) {
                Text("You've had $count glasses.")
            }
            Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
                Text("Add one")
            }
        }
    }

   /* @Composable
    fun WaterCounter(modifier: Modifier = Modifier) {
        //this allows tracking by compose, triggering recomposition when this value changes.
        //remember API is used to track value changes. In this case, incrementing count.
        //val count: MutableState<Int> = remember { mutableStateOf(0) }


        Column(modifier = Modifier.padding(16.dp)) {
            //this is equivalent to val count: MutableState<Int> = remember { mutableStateOf(0) }
            var count by remember { mutableStateOf(0) }
            if (count > 0) {
                var showTask by remember { mutableStateOf(true)}
                if (showTask){
                    WellnessTaskItem(
                        taskName = "Have you taken your 15 minute walk today?",
                        onClose = { showTask = false})
                }

                Text(
                    text = "You've had ${count} glasses."
                )
            }
            Row(Modifier.padding(top = 8.dp)) {
                Button(
                    onClick = { count++ },
                    Modifier.padding(top = 8.dp),
                    //setting enabled to false will prevent the Button and presumably other objects from
                    //being recomposed. When the condition is not met, set to true. When met, set to
                    //false and no longer functional.
                    enabled = count < 10
                ) {
                    Text("Add one")
                }
                Button(
                    onClick = {count = 0},
                    Modifier.padding(top = 8.dp).padding(horizontal = 5.dp)
                ){
                    Text("Clear water count")
                }
            }
        }
    }*/

