package com.starwarsapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.starwarsapp.android.ui.StarWarsScreen
import com.starwarsapp.android.ui.theme.StarWarsTheme
import com.starwarsapp.data.api.StarWarsApi
import com.starwarsapp.data.api.StarWarsApiImpl
import com.starwarsapp.presentation.StarWarsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val api: StarWarsApi = StarWarsApiImpl(StarWarsApiImpl.create())
        
        setContent {
            val viewModel: StarWarsViewModel = viewModel {
                StarWarsViewModel(api)
            }
            
            StarWarsTheme {
                Surface {
                    StarWarsScreen(viewModel = viewModel)
                }
            }
        }
    }
}

