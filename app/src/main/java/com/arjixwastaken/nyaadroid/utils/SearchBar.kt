package com.arjixwastaken.nyaadroid.utils

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.arjixwastaken.nyaadroid.R


// Stolen from https://github.com/pauloaapereira/Medium_JetpackCompose_AutoCompleteSearchBar/blob/b24bcc7d515b6b541aa99807812ed9976ec87729/app/src/main/java/com/example/androiddevchallenge/presentation/sample/AutoCompleteValueSample.kt
// Thank you Paulo Pereira ❤!!
@Composable
fun TextSearchBar(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    onDoneActionClick: () -> Unit = {},
    onClearClick: () -> Unit = {},
    onFocusChanged: (FocusState) -> Unit = {},
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(.9f)
            .onFocusChanged { onFocusChanged(it) },
        value = value,
        onValueChange = { query ->
            onValueChanged(query)
        },
        label = { Text(text = label) },
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        trailingIcon = {
            Row {
                IconButton(onClick = { onClearClick() }) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear")
                }
                IconButton(onClick = { onDoneActionClick() }) {
                    Icon(painter = painterResource(id = R.drawable.ic_baseline_search_24), contentDescription = "Search")
                }
            }
        },
        keyboardActions = KeyboardActions(onDone = { onDoneActionClick() }),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        )
    )
}