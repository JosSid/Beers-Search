package com.jossidfactory.beers.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jossidfactory.beers.R
import com.jossidfactory.beers.component.ButtonBase
import com.jossidfactory.beers.component.LogoApp
import com.jossidfactory.beers.component.TextFieldBase
import org.koin.androidx.compose.koinViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel(), onDetailClick: (Int) -> Unit
) {
    val state: HomeState by homeViewModel.state.observeAsState(HomeState())

    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                LogoApp()
                Spacer(modifier = Modifier.padding(10.dp))
                TextFieldBase(text = stringResource(R.string.search),
                    textValue = state.searchValue,
                    onTextValueChange = { homeViewModel.onSearchChange(it) })
                Spacer(modifier = Modifier.padding(10.dp))
                ButtonBase(text = stringResource(R.string.clear),
                    onClick = { homeViewModel.onSearchChange("") })
                Spacer(modifier = Modifier.padding(10.dp))
                if (state.filteredBeerDtos.isEmpty()) {
                    Text(text = stringResource(R.string.not_beers))
                }
                if (state.isLoading) {
                    Spacer(modifier = Modifier.padding(30.dp))
                    CircularProgressIndicator()
                }
            }
            items(state.filteredBeerDtos) { beer ->
                Text(
                    text = beer.name,
                    modifier = Modifier.clickable { onDetailClick.invoke(beer.id) },
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

    }
}