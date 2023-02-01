package com.ebookfrenzy.composetutorial


import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ebookfrenzy.composetutorial.ui.theme.ComposeTutorialTheme
import com.ebookfrenzy.composetutorial.ui.theme.Message
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import com.ebookfrenzy.composetutorial.ui.theme.SampleData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {  //setContent is used to display UI data
            ComposeTutorialTheme {
                Conversation(SampleData.conversationSample)
               //below is part of the tutorial, being replaced with above line.
               //below shows how I can use Row() to separate data outputs.
                /*Surface {
                    Row() {
                        MessageCard(msg = Message("Android", "Jetpack Compose"))
                        Text("What does this do?")
                    }
                }*/
            }
        }
    }
}

@Composable
fun MessageCard(msg: Message) {
            // Add padding around message
            Row(modifier = Modifier.padding(all = 8.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.skype_picture_2022_06_25t21_31_11_430z),
                    contentDescription = "Contact profile picture",
                    modifier = Modifier
                        // Set image size to 40 dp
                        .size(40.dp)
                        // Clip image to be shaped as a circle
                        .clip(CircleShape)
                        // Add border color to Image
                        .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
                )

                // Add a horizontal space between the image and the column
                // Spacer components are used to display an empty space.
                Spacer(modifier = Modifier.width(8.dp))

                // We keep track if the message is expanded or not in this variable
                //var isExpanded by remember {mutableStateOf(false)}
                /*
                * Explanation from the site:
                * To store this local UI state, you need to keep track of whether a message has been
                * expanded or not. To keep track of this state change, you have to use the functions
                * remember and mutableStateOf.
                * Composable functions can store local state in memory by using remember, and track
                * changes to the value passed to mutableStateOf. Composables (and their children)
                * using this state will get redrawn automatically when the value is updated. This is
                * called recomposition.*/
                //https://dev.to/zachklipp/remember-mutablestateof-a-cheat-sheet-10ma
                var isExpanded by remember { mutableStateOf(false) }

                // surfaceColor will be updated gradually from one color to the other
                val surfaceColor by animateColorAsState(
                    if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
                )

                Column(modifier = Modifier.clickable { isExpanded = !isExpanded}) {
                    Text(
                        text = msg.author,
                        color = MaterialTheme.colors.secondaryVariant,
                        style = MaterialTheme.typography.subtitle2,
                    )
                    // Add a vertical space between the author and message texts
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        elevation = 1.dp,
                        // surfaceColor color will be changing gradually from primary to surface
                        color = surfaceColor,
                        // animateContentSize will change the Surface size gradually
                        modifier = Modifier.animateContentSize().padding(1.dp)
                    ) {
                        Text(
                            text = msg.body,
                            modifier = Modifier.padding(all = 4.dp),
                            // If the message is expanded, we display all its content
                            // otherwise we only display the first line
                            maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
            }
        }

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessageCard() {
    ComposeTutorialTheme {
        Surface {
            MessageCard(
                msg = Message("Colleague", "Hey, take a look at Jetpack Compose," +
                        " it's great!")
            )
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    ComposeTutorialTheme {
        Conversation(SampleData.conversationSample)
    }
}






