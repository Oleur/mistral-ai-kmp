import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import di.mistralViewModel
import kotlinx.coroutines.launch
import mistral_ai_kmp.composeapp.generated.resources.Res
import mistral_ai_kmp.composeapp.generated.resources.ic_mistral_ai
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.ChatScreen
import ui.HeaderScreen
import ui.HistoryScreen
import ui.ModelDropDown

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        val coroutineScope = rememberCoroutineScope()

        val modelViewState by mistralViewModel.models.collectAsState()
        val historyViewState by mistralViewModel.history.collectAsState()
        val chatViewState by mistralViewModel.chat.collectAsState()

        var expanded by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
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
        }
    }
}

internal const val LARGE_SCREEN_THRESHOLD = 400
