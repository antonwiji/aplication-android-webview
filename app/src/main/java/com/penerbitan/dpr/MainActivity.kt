package com.penerbitan.dpr

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.Menu
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
import androidx.appcompat.widget.SearchView
import androidx.compose.ui.text.toLowerCase
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Locale
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var bottomNavigationView: BottomNavigationView
    var urlLink = "https://penerbitan-dpr.id/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    // Tindakan ketika menu "Home" dipilih
                    webView.loadUrl(urlLink)
                    loadingVisble()
                    true
                }

                R.id.navigation_berita -> {
                    // Tindakan ketika menu "Berita" dipilih
                    webView.loadUrl(urlLink+"berita")
                    loadingVisble()
                    true
                }

                else -> false
            }
        }
        setupViews()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupViews() {
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
                removeByQuerySelector(webView, ".header")
                removeByQuerySelector(webView, ".theme-switch-box-wrap")
                removeByQuerySelector(webView, "[data-id='67360ee5']")
                removeByQuerySelector(webView, "[data-id='4ee4692']")
                removeByQuerySelector(webView, "[data-id='e073c6e']")
                removeByQuerySelector(webView, ".apps-img")

            }

            override fun onPageFinished(view: WebView, url: String) {
                Log.e("URL onPageFinished", url)
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            loadingVisble()
            webView.goBack()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_action_bar, menu)

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.navigation_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                loadingVisble()
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchItem.collapseActionView()

                var searchUrl = "$urlLink?s=$query"
                webView.loadUrl(searchUrl)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    private fun loadingVisble() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
    }

    private fun removeByQuerySelector(webView: WebView, classHtml: String) {
        webView.loadUrl("javascript:document.querySelector(\"$classHtml\").remove();")
    }

    private fun validateUrl(url: String): Boolean {
        val validationResult = url.isNotEmpty() && url.isValidUrl()
        if (!validationResult)
            Toast.makeText(baseContext, "Please prove a valid URL", Toast.LENGTH_SHORT).show()
        return validationResult
    }

    private fun String.isValidUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()
}