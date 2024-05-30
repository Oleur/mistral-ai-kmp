import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import di.mistralViewModel
import kotlinx.coroutines.launch
import mistral_ai_kmp.composeapp.generated.resources.Res
import mistral_ai_kmp.composeapp.generated.resources.ic_mistral_ai
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.*

@Composable
@Preview
fun App() {
    MaterialTheme {
        val coroutineScope = rememberCoroutineScope()

        val modelViewState by mistralViewModel.models.collectAsState()
        val historyViewState by mistralViewModel.history.collectAsState()
        val chatViewState by mistralViewModel.chat.collectAsState()
        val key by mistralViewModel.key.collectAsState()

        var expanded by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            mistralViewModel.init()
            mistralViewModel.fetchModels()
        }

        BoxWithConstraints(modifier = Modifier.background(color = Color.White)) {
            val density = LocalDensity.current
            val threshold = density.run { LARGE_SCREEN_THRESHOLD.dp.toPx() }
            if (maxWidth.value < threshold) {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopAppBar(
                            backgroundColor = Color(0xFFffd0b5),
                            title = { Text(text = "ZeChat KMP") },
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            scaffoldState.drawerState.open()
                                        }
                                    },
                                ) {
                                    Icon(
                                        modifier = Modifier.size(24.dp),
                                        painter = painterResource(Res.drawable.ic_mistral_ai),
                                        contentDescription = "Send",
                                    )
                                }
                            },
                            actions = {
                                IconButton(onClick = { expanded = !expanded }) {
                                    Icon(
                                        modifier = Modifier.size(24.dp),
                                        painter = rememberVectorPainter(Icons.Filled.Settings),
                                        contentDescription = "Settings",
                                    )
                                }
                                ModelDropDown(
                                    expanded = expanded,
                                    viewState = modelViewState,
                                    onDismissRequest = { expanded = false },
                                    onItemClick = {
                                        mistralViewModel.setSelectedModel(it)
                                        expanded = false
                                    }
                                )
                            }
                        )
                    },
                    drawerShape = RoundedCornerShape(
                        topEnd = 16.dp,
                        bottomEnd = 16.dp,
                    ),
                    drawerContent = {
                        HistoryScreen(
                            historyViewState = historyViewState,
                            onCreateNewChat = mistralViewModel::newChat,
                        )
                    }
                ) {
                    ChatScreen(
                        modifier = Modifier.fillMaxSize(),
                        chatViewState = chatViewState,
                        onChat = mistralViewModel::chat,
                    )
                }
            } else {
                Row(modifier = Modifier.fillMaxWidth()) {
                    HistoryScreen(
                        modifier = Modifier.fillMaxWidth(0.3f),
                        historyViewState = historyViewState,
                        onCreateNewChat = mistralViewModel::newChat,
                    )
                    Column(modifier = Modifier.weight(1f, false)) {
                        HeaderScreen(
                            modifier = Modifier.padding(16.dp),
                            text = modelViewState.selectedModelId,
                            onModelClicked = { expanded = !expanded },
                        ) {
                            ModelDropDown(
                                expanded = expanded,
                                viewState = modelViewState,
                                onDismissRequest = { expanded = false },
                                onItemClick = {
                                    mistralViewModel.setSelectedModel(it)
                                    expanded = false
                                }
                            )
                        }
                        ChatScreen(chatViewState = chatViewState, onChat = mistralViewModel::chat)
                    }
                }
            }
            // If needed, launch dialog to set up the key
            key?.let {
                AnimatedVisibility(visible = it.isBlank()) {
                    LandingDialog { newKey ->
                        mistralViewModel.updateKey(newKey)
                    }
                }
            }
        }
    }
}

internal const val LARGE_SCREEN_THRESHOLD = 400
