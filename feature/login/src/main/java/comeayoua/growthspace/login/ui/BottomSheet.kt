package comeayoua.growthspace.login.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import comeayoua.growthspace.ui.widgets.DefaultBottomSheetDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessBottomSheet(
    title: String?,
    message: String?,
    onDismissRequest: () -> Unit,
    sheetState: SheetState
){
    DefaultBottomSheetDialog(
        title = title,
        subtitle = message,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            modifier = Modifier
                .size(64.dp) ,
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Box{
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center),
                    imageVector = Icons.Default.Close,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = ""
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorBottomSheet(
    title: String?,
    message: String?,
    onDismissRequest: () -> Unit,
    sheetState: SheetState
) {
    DefaultBottomSheetDialog(
        title = title,
        subtitle = message,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            modifier = Modifier
                .size(64.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.errorContainer
        ) {
            Box {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center),
                    imageVector = Icons.Default.Close,
                    tint = MaterialTheme.colorScheme.error,
                    contentDescription = ""
                )
            }
        }
    }
}