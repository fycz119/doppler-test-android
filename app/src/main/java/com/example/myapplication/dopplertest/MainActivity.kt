
package com.example.myapplication.dopplertest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.dopplertest.jasperlu.doppler.Doppler
import com.example.myapplication.dopplertest.ui.theme.DopplerTestTheme

class MainActivity : ComponentActivity() {
    private val TAG: String = "DopplerTestMainActivity";
    private lateinit var mDoppler: Doppler;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DopplerTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    ShowDopplerText()
                }
            }
        }


        mDoppler = Doppler();
        mDoppler.start()
        mDoppler.setOnGestureListener(object : Doppler.OnGestureListener{
            override fun onPush() {
                Log.d(TAG, "onPush: ");
            }

            override fun onPull() {
                Log.d(TAG, "onPull: ");

            }

            override fun onTap() {
                Log.d(TAG, "onTap: ");
            }

            override fun onDoubleTap() {
                Log.d(TAG, "onDoubleTap: ");
            }

            override fun onNothing() {
                // Log.d(TAG, "onNothing: ");
            }

        });
        mDoppler.setOnReadCallback(object : Doppler.OnReadCallback{
            override fun onBandwidthRead(leftBandwidth: Int, rightBandwidth: Int) {
                // Log.d(TAG, "leftBandwidth: $leftBandwidth");
                // Log.d(TAG, "rightBandwidth: $rightBandwidth");
            }

            override fun onBinsRead(bins: DoubleArray?) {
                // Log.d(TAG, "bins: $bins");
            }

        })
    }

    override fun onStop() {
        super.onStop()
        mDoppler.pause();
    }
}

@Composable
fun ShowDopplerText(dopplerMessages: MutableList<String>) {
    LazyColumn {
        items() {

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