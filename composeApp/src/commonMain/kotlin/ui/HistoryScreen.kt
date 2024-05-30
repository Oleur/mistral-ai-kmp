package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mistral_ai_kmp.composeapp.generated.resources.Res
import mistral_ai_kmp.composeapp.generated.resources.ic_mistral_ai_logo
import org.jetbrains.compose.resources.painterResource
import ui.state.HistoryViewState

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    historyViewState: HistoryViewState,
    onCreateNewChat: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = Color(0xFFF7F7F7))
            .padding(16.dp)
            .then(modifier),
    ) {
        Row {
            Image(
                painter = painterResource(Res.drawable.ic_mistral_ai_logo),
                contentScale = ContentScale.Fit,
                contentDescription = "Mistral AI logo",
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        if (historyViewState.queries.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "No History 👻\nStart writing something...",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
            ) {
                itemsIndexed(
                    items = historyViewState.queries,
                    key = { _, item -> item },
                ) { _, item ->
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { },
                        text = item,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.size(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFFD4A02),
            ),
            onClick = onCreateNewChat,
        ) {
            Text(text = "New chat", fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.size(4.dp))
            Icon(
                modifier = Modifier.size(24.dp),
                painter = rememberVectorPainter(Icons.Filled.Create),
                contentDescription = ""
            )
        }
    }
}