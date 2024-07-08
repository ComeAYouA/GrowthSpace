package comeayoua.growthspace.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    onSearchQueryChanged: (String) -> Unit = {},
    hint: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    query: MutableState<String> = rememberSaveable { mutableStateOf("") }
){
    BasicTextField(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(30.dp))
            .height(42.dp)
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        value = query.value,
        onValueChange = {
            query.value = it
            onSearchQueryChanged(it)
        },
        singleLine = true,
        visualTransformation = visualTransformation,
        textStyle = TextStyle().copy(
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ){
                if (query.value.isEmpty()){
                    Text(
                        text = hint,
                        color = Color.Gray
                    )
                }
                innerTextField()
            }
        }
    )
}