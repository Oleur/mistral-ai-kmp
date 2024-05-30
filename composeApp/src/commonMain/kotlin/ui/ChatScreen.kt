package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mistral_ai_kmp.composeapp.generated.resources.Res
import mistral_ai_kmp.composeapp.generated.resources.ic_mistral_ai
import org.jetbrains.compose.resources.painterResource
import ui.state.ChatViewState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    chatViewState: ChatViewState,
    onChat: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
            .background(color = Color.Transparent)
            .padding(horizontal = 8.dp)
            .then(modifier),
    ) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(
                items = chatViewState.conversation,
                key = { index, item -> index.toString() + item },
            ) { _, item ->
                Row(
                    modifier = Modifier.animateItemPlacement(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Box {
                        if (item.isMistral == true) {
                            Image(
                                modifier = Modifier
                                    .size(32.dp)
                                    .drawBehind {
                                        drawCircle(
                                            color = Color(0x66ffd0b5),
                                            radius = 16.dp.toPx(),
                                        )
                                    }
                                    .padding(4.dp),
                                painter = painterResource(Res.drawable.ic_mistral_ai),
                                contentScale = ContentScale.Inside,
                                contentDescription = "",
                            )
                        } else {
                            Text(
                                modifier = Modifier
                                    .size(32.dp)
                                    .border(2.dp, Color.LightGray, CircleShape)
                                    .padding(top = 2.dp)
                                    .clip(CircleShape)
                                    .align(Alignment.Center),
                                text = "U",
                                color = Color.LightGray,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                    SelectionContainer {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = item.content,
                            fontSize = 16.sp,
                            fontWeight = if (item.isMistral == true) {
                                FontWeight.Normal
                            } else {
                                FontWeight.SemiBold
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
        val textState = remember { mutableStateOf(TextFieldValue()) }
        val buttonEnabled = remember { mutableStateOf(false) }
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, Color.LightGray, RoundedCornerShape(50.dp)),
            value = textState.value,
            onValueChange = {
                textState.value = it
                buttonEnabled.value = it.text.isNotBlank()
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
                    text = "Talk to ZeChat powered by Mistral AI...",
                    color = Color(0xFFA0A0A0),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                )
            },
            trailingIcon = {
                IconButton(
                    modifier = Modifier.clip(shape = CircleShape),
                    enabled = buttonEnabled.value,
                    onClick = {
                        onChat(textState.value.text)
                        textState.value = TextFieldValue()
                        buttonEnabled.value = false
                    },
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = rememberVectorPainter(Icons.AutoMirrored.Filled.Send),
                        contentDescription = "Send",
                        tint = if (buttonEnabled.value) {
                            Color(0xFFFD4A02)
                        } else {
                            Color.LightGray
                        },
                    )
                }
            }
        )
    }
}