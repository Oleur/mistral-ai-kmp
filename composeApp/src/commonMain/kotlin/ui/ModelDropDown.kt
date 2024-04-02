package ui

import ui.state.ModelViewState
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import org.mistral.ai.kmp.domain.Model

@Composable
fun ModelDropDown(
    expanded: Boolean,
    viewState: ModelViewState,
    onDismissRequest: () -> Unit = {},
    onItemClick: (Model) -> Unit,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        offset = DpOffset(0.dp, 8.dp),
        modifier = Modifier.wrapContentWidth().fillMaxHeight(0.4f)
    ) {
        viewState.models.forEach { model ->
            DropdownMenuItem(
                onClick = { onItemClick(model) },
            ) {
                Text(
                    text = if (viewState.selectedModelId == model.id) {
                        "✔ ${model.id}"
                    } else {
                        model.id
                    },
                    fontWeight = if (viewState.selectedModelId == model.id) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    }
                )
            }
        }
    }
}