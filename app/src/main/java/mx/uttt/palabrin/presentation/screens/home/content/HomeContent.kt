package mx.uttt.palabrin.presentation.screens.home.content

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import mx.uttt.palabrin.R
import java.nio.file.WatchEvent

@Composable
fun HomeContent(
    padding: PaddingValues
) {
    Box(

    ){

    }
}

@Preview(showBackground = true)
@Composable
fun FourOptions() {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OptionCards(
                    icon = R.drawable.ic_abc,
                    title = R.string.val_game_abcd_title,
                    description = R.string.val_game_abcd_description,
                    modifier = Modifier.weight(1f),
                    color = Color.Blue
                )
                OptionCards(
                    icon = R.drawable.ic_write,
                    title = R.string.val_game_write_title,
                    description = R.string.val_game_write_description,
                    modifier = Modifier.weight(1f),
                    color = Color.Green
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OptionCards(
                    icon = R.drawable.ic_tales,
                    title = R.string.val_game_tales_title,
                    description = R.string.val_game_tales_description,
                    modifier = Modifier.weight(1f),
                    color = Color.Yellow
                )
                OptionCards(
                    icon = R.drawable.ic_games,
                    title = R.string.val_game_title,
                    description = R.string.val_game_description,
                    modifier = Modifier.weight(1f),
                    color = Color.Red
                )
            }
        }
    }
}

@Composable
fun OptionCards(
    @DrawableRes icon: Int,
    @StringRes title: Int,
    @StringRes description: Int,
    modifier: Modifier = Modifier,
    color: Color
) {
    Card(
        modifier = modifier
            .heightIn(min = 180.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                modifier = Modifier.clip(RoundedCornerShape(10.dp)).background(color = color).padding(5.dp),
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(id = description),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}
