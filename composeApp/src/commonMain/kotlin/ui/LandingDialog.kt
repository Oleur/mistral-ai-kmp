package ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties

@Composable
fun LandingDialog(onClick: (String) -> Unit) {
    val textState = remember { mutableStateOf(TextFieldValue()) }
    val showDialog = rememberSaveable { mutableStateOf(true) }
    if (showDialog.value) {
        AlertDialog(
            title = {
                Text(
                    text = "No API key detected",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Provide your own key to continue!",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(2.dp, Color.LightGray, RoundedCornerShape(50.dp)),
                        value = textState.value,
                        onValueChange = {
                            textState.value = it
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            cursorColor = Color(0xFFFDBA94),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                        ),
                        placeholder = {
                            Text(
                                text = "Enter your Mistral AI API key...",
                                color = Color(0xFFA0A0A0),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light,
                            )
                        },
                    )
                }
            },
            buttons = {
                Box(modifier = Modifier.fillMaxWidth().padding(end = 16.dp)) {
                    Button(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFFD4A02),
                        ),
                        onClick = {
                            if (textState.value.text.isNotBlank()) {
                                onClick(textState.value.text)
                                showDialog.value = false
                            }
                        }
                    ) {
                        Text(text = "Go!", fontWeight = FontWeight.Medium)
                    }
                }

            },
            onDismissRequest = { },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        )
    }
}