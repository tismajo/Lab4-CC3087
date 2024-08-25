package com.composecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composecalculator.ui.theme.ComposeCalculatorTheme
import com.composecalculator.Calculadora

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeCalculatorTheme {
                Calculator()
            }
        }
    }
}

@Composable
fun Calculator(
    modifier: Modifier = Modifier
    ) {
    var title by remember { mutableStateOf("0") }
    var subtitle by remember { mutableStateOf("0") }
    val calculator = Calculadora()

    Column {
        DisplayCalc(title, subtitle)

        Spacer(modifier = Modifier.height(16.dp))

        // Fila de botones 1-2-3-+-...
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxWidth()
        ) {
            CalculatorButton(onClickedValue = { title = if (title == "0") "1" else title + "1" }, text = "1")
            CalculatorButton(onClickedValue = { title = if (title == "0") "2" else title + "2" }, text = "2")
            CalculatorButton(onClickedValue = { title = if (title == "0") "3" else title + "3" }, text = "3")
            CalculatorButton(onClickedValue = { title = if (title == "0") "+" else title + "+" }, text = "+")
            CalculatorButton(onClickedValue = { title = if (title == "0") "-" else title + "-" }, text = "-")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Fila de botones 4-5-6-*-/...
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxWidth()
        ) {
            CalculatorButton(onClickedValue = { title = if (title == "0") "4" else title + "4" }, text = "4")
            CalculatorButton(onClickedValue = { title = if (title == "0") "5" else title + "5" }, text = "5")
            CalculatorButton(onClickedValue = { title = if (title == "0") "6" else title + "6" }, text = "6")
            CalculatorButton(onClickedValue = { title = if (title == "0") "*" else title + "*" }, text = "*")
            CalculatorButton(onClickedValue = { title = if (title == "0") "/" else title + "/" }, text = "/")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Fila de botones 7-8-9-(-)...
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxWidth()
        ) {
            CalculatorButton(onClickedValue = { title = if (title == "0") "7" else title + "7" }, text = "7")
            CalculatorButton(onClickedValue = { title = if (title == "0") "8" else title + "8" }, text = "8")
            CalculatorButton(onClickedValue = { title = if (title == "0") "9" else title + "9" }, text = "9")
            CalculatorButton(onClickedValue = { title = if (title == "0") "(" else title + "(" }, text = "(")
            CalculatorButton(onClickedValue = { title = if (title == "0") ")" else title + ")" }, text = ")")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Fila de botones .-0-<-C=...
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxWidth()
        ) {
            CalculatorButton(onClickedValue = { title = if (title == "0") "." else title + "." }, text = ".")
            CalculatorButton(onClickedValue = { title = if (title == "0") "0" else title + "0" }, text = "0")
            CalculatorButton(onClickedValue = {
                if (title.isNotEmpty() && title != "0") {
                    title = title.substring(0, title.length - 1)
                }
                if (title.isEmpty()) {
                    title = "0"
                }
            }, text = "<-")
            CalculatorButton(onClickedValue = { title = "0" }, text = "C")
            CalculatorButton(onClickedValue = {
                try {
                    subtitle = calculator.evaluar(title).toString() // Evaluar la expresión
                } catch (e: Exception) {
                    subtitle = "Error"
                }
            }, text = "=")
        }
    }
}

@Composable
fun CalculatorButton(
    onClickedValue: () -> Unit,
    text: String = "",
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClickedValue,
        modifier = modifier.padding(end = 10.dp)
    ) {
        Text(text = text)
    }
}

@Composable
fun DisplayCalc(title: String, subtitle: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RightAlignedCardPreview() {
    Calculator()
}

@Preview(showBackground = true)
@Composable
fun DisplayCalcPreview() {
    DisplayCalc("0", "0")
}

@Preview(showBackground = true)
@Composable
fun CalculatorButtonPreview() {
    CalculatorButton({}, "0")
}
