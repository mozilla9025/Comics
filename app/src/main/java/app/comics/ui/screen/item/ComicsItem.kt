package app.comics.ui.screen.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.comics.ui.model.DisplayComic
import coil.compose.AsyncImage

@Composable
fun ComicsItem(
    uiModel: DisplayComic,
    onFavClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .safeContentPadding(),
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .wrapContentHeight()
            ) {
                AsyncImage(
                    model = uiModel.thumbnail,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.75f),
                    contentDescription = null,
                )

                Text(
                    text = uiModel.prices.firstOrNull()?.let { "${it.price}" } ?: ""
                )

                Button(
                    onClick = onFavClick,
                    colors = ButtonDefaults.buttonColors(),
                    modifier = Modifier
                        .height(32.dp)
                        .width(32.dp),
                    shape = RoundedCornerShape(24.dp),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    Icon(
                        imageVector = if (uiModel.isFavorite) Icons.Outlined.Star else Icons.Outlined.StarOutline,
                        contentDescription = ""
                    )
                }
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = uiModel.title,
                    style = MaterialTheme.typography.subtitle2
                )
                Text(
                    text = uiModel.description ?: "",
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}
