package com.example.basicscodelab

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicscodelab.ui.theme.BasicsCodelabTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                MyApp(modifier = Modifier.fillMaxSize())
                //Greeting("Android")
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    val TAG = "SearchForMe"
    /*remember is a keyword that guards against recomposition.
    When data is changed in a @compose functions, they are recomposed or reexecuted. Using the
    remember keyword means that the compiler will skip over the command.
    MutableStateOf is an interface that holds some value and notifies the UI of changes, which is
    what is going to trigger recomposition and allow our column to be resized.
    */

    //replaced with by  remember {mutableStateOf(false)}
    //val expanded = remember { mutableStateOf(false) }

    //replacing with animateDpAsState
    //val extraPadding = if (expanded.value) 48.dp else 0.dp

    var expanded by remember { mutableStateOf(false)} //Use rememberSaveable to prevent the
    // items from being redrawn when they are off the screen.
    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp,
        //If padding goes to negative, app will crash. The app loaded and the button
        //worked to expand and compress view but then crashed due to negative padding.
        //Prevention of negative padding occurs w .padding(bottom = extraPadding.coerceAtLeast(0.dp))
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    //This is how much total volume the Composable function will take up on the screen
    //note: Adjusting the nested components will increase the size of the surface
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ){
        Row (
            modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp)) ) {
                Text(
                    text = "Hello,"
                )
                Text(
                    text = name,
                    //example of using MaterialTheme to change text font (can make own typography)
                    //.copy allows modifying predefined style
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )

            }
            //ElevatedButton is replaced with IconButton for the final touch.
            ElevatedButton(
                //unlike in java, the expression below will check the value and then change it
                //to the opposite value. In other words, this is not setting it to false.
                //proof:
                //var x = true
                //x = !x
                //print(x) //false
                //x = !x
                //print(x) //true
                onClick = { expanded = !expanded }
            ) {//Sets Btn text
                Text(if (expanded) "Show less" else "Show more" )
            }
            //This is how to "console out" in Android studio. Note, the first parameter is created
            //at the top of the function. The second parameter is what you want to check.
            //After placing the line of code, you click the LogCat tab at the bottom then type in
            //the value of the first parameter (in this case SearchForMe) into right search bar, which
            //might have the value "package::mine" in it initially.
            //The purpose of this was to look at the mutable boolean changing between true/false
            Log.i(TAG, expanded.toString())

        }
    }
}
/*
* Steps to create the cards
* 1. A boolean is needed to check if the cards are expanded
* 2. An extraPadding val is created, more research needed.
* 3. Make a surface
* 4. Make a row
* 5. Make a Column
* 6. Place text inside
* 7. In this case, make a button on the row
 */
@Composable
private fun CardContent(name: String){
    var isExpanded by remember { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if (isExpanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium,
        )
    )
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp )
    ){
        Row(
            modifier = Modifier.padding(24.dp)
        ){
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(
                    text = "Hello, "
                )
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineMedium
                        .copy(fontWeight = FontWeight.ExtraBold
                        )
                )
            }
            IconButton(onClick = { isExpanded = !isExpanded }) {
                Icon(
                    imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (isExpanded) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }
                )
            }
        }

    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
private fun CardContentPreview(){
    BasicsCodelabTheme() {
        CardContent(name = "Hello World")
    }
}

//info on Modifier: https://proandroiddev.com/a-closer-look-at-modifier-in-jetpack-compose-fdf073df92bd
@Composable
private fun MyApp(modifier: Modifier = Modifier){
    //rememberSaveable prevents recomposition when onCreate is called.
    var shouldShowOnBoarding by rememberSaveable { mutableStateOf(true)}

    Surface(modifier){
        if (shouldShowOnBoarding){
            OnBoardingScreen(onContinueClicked = {shouldShowOnBoarding = false} )
        }else {
            Greetings()
            
        }
    }
}

@Composable
fun OnBoardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier){
    //Todo: This state should be hoisted (Completed by moving var shouldShowOnboarding to MyApp)
    //shouldShowOnboarding is using a by keyword instead of the =.
    //This is a property delegate that saves you from typing .value every time
    //Note to self: Figure out 'by', as clear as I can see its grabbing the result of the getter

    //var shouldShowOnboarding by remember { mutableStateOf(true) } //this is commented out because
    //we're using State hoisting, which means that a higher level function (MyApp) to control the state.

    //This is achieved by:
    //1. Adding a callback parameter into the lower function (OnBoard), which is  onContinueClicked: () -> Unit
    //Notice the OnContinueClicked is in the conditional logic of MyApp, and for some reason is blue.
    //2.

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Welcome to the Basics Codelab")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) {"$it"}
){
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(names) {name ->
            //Greeting(name = name)
            CardContent(name = name)
        }
    }
}


@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        //MyApp(modifier = Modifier.fillMaxSize())
        Greeting("Android")

    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnBoardingPreview(){
    BasicsCodelabTheme {
        OnBoardingScreen(onContinueClicked = {}) //Do nothing on click
    }
}


@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
private fun GreetingsPreview(){
    BasicsCodelabTheme {
        Greetings()
    }
}

@Preview
@Composable
fun MyAppPreview() {
    BasicsCodelabTheme {
        MyApp(Modifier.fillMaxSize())
    }
}