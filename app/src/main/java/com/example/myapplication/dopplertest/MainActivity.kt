
package com.example.myapplication.dopplertest

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.dopplertest.jasperlu.doppler.Doppler
import com.example.myapplication.dopplertest.ui.theme.DopplerTestTheme
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    private val TAG: String = "DopplerTestMainActivity";
    private lateinit var mDoppler: Doppler;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDoppler = Doppler();
        mDoppler.start()

        enableEdgeToEdge()
        setContent {
            DopplerTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    // ShowDopplerText(dopplerText)
                    PlayVideoCompose(mDoppler)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        mDoppler.pause();
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}

@Composable
fun ShowDopplerText(dopplerText: SnapshotStateList<String>) {
    LazyColumn {
        items(dopplerText) { text ->
            Text(text = text)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DopplerTestTheme {
        Greeting("Android")
    }
}