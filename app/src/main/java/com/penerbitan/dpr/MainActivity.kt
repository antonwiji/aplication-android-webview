package com.penerbitan.dpr

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.penerbitan.dpr.ui.theme.PenerbitanDPRTheme
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    // Tindakan ketika menu "Home" dipilih
                    true
                }
                R.id.navigation_search -> {
                    // Tindakan ketika menu "Search" dipilih
                    true
                }
                R.id.navigation_notifications -> {
                    // Tindakan ketika menu "Notifications" dipilih
                    true
                }
                else -> false
            }
        }
        setupViews()
    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupViews() {
        val urlLink = "https://penerbitan-dpr.id/"
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(urlLink);
        progressBar.visibility = View.VISIBLE
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        webView.settings.databaseEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true
        webView.webViewClient = object : WebViewClient() {

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                handler?.proceed()
            }
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

                return if (url.startsWith("tel:") || url.startsWith("mailto:") || url.startsWith("market://")) {
                    view.context.startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    )

                    true
                } else {
                    progressBar.visibility = View.VISIBLE
                    view.loadUrl(url)
                    true
                }
            }

            override fun onLoadResource(view: WebView, url: String) {
                webView.loadUrl("javascript:document.querySelector(\".header\").remove();")
            }

            override fun onPageFinished(view: WebView, url: String) {
                Log.e("URL onPageFinished", url)
                progressBar.visibility = View.GONE
                webView.loadUrl("javascript:document.querySelector(\".header\").remove();")
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        } else {
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun validateUrl(url: String): Boolean {
        val validationResult = url.isNotEmpty() && url.isValidUrl()
        if (!validationResult)
            Toast.makeText(baseContext, "Please prove a valid URL", Toast.LENGTH_SHORT).show()
        return validationResult
    }

    private fun String.isValidUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()
}