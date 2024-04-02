import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import mistral_ai_kmp.composeapp.generated.resources.Res
import mistral_ai_kmp.composeapp.generated.resources.ic_mistral_ai
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
fun main() = application {
    val state = rememberWindowState(size = DpSize(width = 900.dp, height = 600.dp))
    Window(
        onCloseRequest = ::exitApplication,
        title = "Mistral AI ZeChat KMP",
        icon = painterResource(Res.drawable.ic_mistral_ai),
        state = state,
    ) {
        App()
    }
}
