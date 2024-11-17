package comeayoua.growthspace.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    hintColor: Color = Color.Gray,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    hint: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    query: State<String>,
    onValueChanged: (String) -> Unit,
    singleLine: Boolean = true
){
    BasicTextField(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(12.dp))
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp),
        value = query.value,
        onValueChange = {
            onValueChanged(it)
        },
        singleLine = singleLine,
        visualTransformation = visualTransformation,
        textStyle = TextStyle().copy(
            color = textColor
        ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.CenterStart
            ){
                if (query.value.isEmpty()){
                    Text(
                        text = hint,
                        color = hintColor
                    )
                }
                innerTextField()
            }
        }
    )
}