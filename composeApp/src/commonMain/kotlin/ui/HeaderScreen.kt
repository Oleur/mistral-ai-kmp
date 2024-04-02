package ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderScreen(
    modifier: Modifier = Modifier,
    text: String = "",
    onModelClicked: () -> Unit,
    dropDownPlaceholder: @Composable () -> Unit = {},
) {
    Row(
        modifier = Modifier.fillMaxWidth().then(modifier),
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            text = "Mistral AI ZeChat - KMP",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
        Box(
            modifier = Modifier.align(Alignment.CenterVertically),
        ) {
            if (text.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .border(2.dp, Color.LightGray, RoundedCornerShape(8.dp))
                        .padding(
                            vertical = 4.dp,
                            horizontal = 8.dp,
                        )
                        .clickable {
                            onModelClicked()
                        },
                    text = "✔ $text",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                )
                dropDownPlaceholder()
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color(0xFFFD4A02),
                    strokeWidth = 2.dp,
                )
            }
        }
    }
}