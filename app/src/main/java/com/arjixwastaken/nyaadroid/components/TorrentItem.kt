package com.arjixwastaken.nyaadroid.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arjixwastaken.nyaaapi.models.TorrentPreview
import com.arjixwastaken.nyaadroid.NyaaAPI
import com.arjixwastaken.nyaadroid.R

@DrawableRes
fun categoryToRes(cat: String):  Int {
    return when (cat) {
        "1_1" -> R.drawable.cat_1_1
        "1_2" -> R.drawable.cat_1_2
        "1_3" -> R.drawable.cat_1_3
        "1_4" -> R.drawable.cat_1_4
        "2_1" -> R.drawable.cat_2_1
        "2_2" -> R.drawable.cat_2_2
        "3_1" -> R.drawable.cat_3_1
        "3_2" -> R.drawable.cat_3_2
        "3_3" -> R.drawable.cat_3_3
        "4_1" -> R.drawable.cat_4_1
        "4_2" -> R.drawable.cat_4_2
        "4_3" -> R.drawable.cat_4_3
        "4_4" -> R.drawable.cat_4_4
        "5_1" -> R.drawable.cat_5_1
        "5_2" -> R.drawable.cat_5_2
        "6_1" -> R.drawable.cat_6_1
        "6_2" -> R.drawable.cat_6_2
        else -> throw Exception("The `$cat` category does not exist.")
    }
}

@Composable
fun TorrentItem(torrent: TorrentPreview) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, Color.Gray)) {
        Row {
            Image(
                painter = painterResource(id = categoryToRes(torrent.category)),
                contentDescription = NyaaAPI.categoryIdToString(torrent.category),
                modifier = Modifier.scale(2f, 3.3f).padding(top = 7.dp)
            )
            Text(torrent.title, fontSize = 15.sp, modifier = Modifier.padding(start = 17.dp))
            // TODO: Actually make this pretty and more informative.
        }
    }
}