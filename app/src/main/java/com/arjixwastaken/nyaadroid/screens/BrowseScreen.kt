package com.arjixwastaken.nyaadroid.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arjixwastaken.nyaadroid.api.TorrentResult
import com.arjixwastaken.nyaadroid.components.TorrentItem
import com.arjixwastaken.nyaadroid.utils.TextSearchBar
import kotlin.concurrent.thread


val BrowseItems by lazy { mutableStateListOf<TorrentResult>() }

@Composable
fun Browse(Items: SnapshotStateList<TorrentResult>){
    var previousValue by remember { mutableStateOf("") }
    var currentPage by remember { mutableStateOf(1) }
    var value by remember { mutableStateOf("") }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            TextSearchBar(
                Modifier.fillMaxWidth(.9f),
                value=value,
                label="Search nyaa",
                onClearClick = { value = "" },
                onFocusChanged = {},
                onValueChanged = { value = it },
                onDoneActionClick = {
                    if (previousValue != value) {
                        previousValue = value
                        currentPage = 1
                        BrowseItems.clear()

                        thread {
                            // TODO: Actually do the search
                        }
                    }
                }
            )
            // TODO: Add a menu to edit the search parameters (eg category)
        }
        LazyColumn {
            itemsIndexed(Items) { index, item ->
                Column(modifier = Modifier.clickable { BrowseItems.removeAt(index) }) {
                    TorrentItem(item)
                }
                // TODO: When the user scrolls to the last item, fetch the next page.
            }
        }
    }
}
