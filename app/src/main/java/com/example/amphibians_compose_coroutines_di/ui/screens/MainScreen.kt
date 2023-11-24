package com.example.amphibians_compose_coroutines_di.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibians_compose_coroutines_di.R
import com.example.amphibians_compose_coroutines_di.network.Amphibians
import com.example.amphibians_compose_coroutines_di.ui.theme.Amphibians_Compose_Coroutines_DITheme

@Composable
fun MainScreen(
    amphibiansUiState: AmphibiansUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (amphibiansUiState) {
        is AmphibiansUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is AmphibiansUiState.Success -> AmphibiansListScreen(amphibiansUiState.amphibiansInfo, modifier)
        is AmphibiansUiState.Error -> ErrorScreen( retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun AmphibiansListScreen(listAmphibians: List<Amphibians>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items = listAmphibians) {amphibian ->
            AmphibiansPhotoCard(
                amphibian,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 24.dp
                    )
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun AmphibiansPhotoCard(info: Amphibians, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            Text(
                text = stringResource(R.string.name_and_type,info.name,info.type),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(info.imgSrc)
                    .crossfade(true)//плавное затухание
                    .build(),
                contentScale = ContentScale.FillWidth ,
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = stringResource(R.string.amphibians),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(
                text = info.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AmphibiansListScreenPreview() {
    Amphibians_Compose_Coroutines_DITheme {
        val mock = listOf(
            Amphibians("Pacific Chorus Frog", "Frog","Also known as the Pacific Treefrog, it is the most common frog on the Pacific Coast of North America. These frogs can vary in color between green and brown and can be identified by a brown stripe that runs from their nostril, through the eye, to the ear.",""),
            Amphibians("Pacific Chorus Frog", "Frog","Also known as the Pacific Treefrog, it is the most common frog on the Pacific Coast of North America. These frogs can vary in color between green and brown and can be identified by a brown stripe that runs from their nostril, through the eye, to the ear.","")
        )
        AmphibiansListScreen(
            listAmphibians = mock
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AmphibiansPhotoCardPreview() {
    Amphibians_Compose_Coroutines_DITheme {
        val mock = Amphibians("Pacific Chorus Frog", "Frog","Also known as the Pacific Treefrog, it is the most common frog on the Pacific Coast of North America. These frogs can vary in color between green and brown and can be identified by a brown stripe that runs from their nostril, through the eye, to the ear.","")
        AmphibiansPhotoCard(
            info = mock
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    Amphibians_Compose_Coroutines_DITheme {
        ErrorScreen({})
    }
}
