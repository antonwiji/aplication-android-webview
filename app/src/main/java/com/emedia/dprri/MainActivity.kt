package com.emedia.dprri

import CustomExpandableListAdapter
import ExpandableListData.data
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.penerbitan.dpr.R


class MainActivity : AppCompatActivity() {
    // Expandable List properti
    private var expandableListView: ExpandableListView? = null
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null

    private lateinit var webView: WebView
    private lateinit var bottomNavigationView: BottomNavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    var urlLink = "https://emedia.dpr.go.id/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        drawerLayout = findViewById(R.id.my_drawer_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout,
            R.string.nav_open,
            R.string.nav_close
        )
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false)

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        supportActionBar?.setIcon(R.drawable.logo_resize)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFFFF")))
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
                R.id.navigation_menu -> {
                    drawerLayout.openDrawer(Gravity.RIGHT)
                    true
                }

                else -> false
            }
        }
        expandableListView = findViewById(R.id.expendableList)
        if (expandableListView != null) {
            val listData = data
            titleList = ArrayList(listData.keys)
            adapter = CustomExpandableListAdapter(this, titleList as ArrayList<String>, listData)
            expandableListView!!.setAdapter(adapter)
            expandableListView!!.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                when(listData[(titleList as ArrayList<String>) [groupPosition]]!!.get(childPosition)){
                    "E-Book" -> {
                        onClickMenuChild(webView, "https://emedia.dpr.go.id/ebook/")
                    }
                    "E-Magazine" -> {
                        onClickMenuChild(webView, "https://emedia.dpr.go.id/digital-publishing/e-magazine/")
                    }
                    "E-Buletin" -> {
                        onClickMenuChild(webView, "https://emedia.dpr.go.id/digital-publishing/e-buletin/")
                    }
                    "TVR PARLEMEN" -> {
                        onClickMenuChild(webView, "https://tvrparlemen.dpr.go.id/")
                    }
                    "Instagram" -> {
                        onClickMenuChild(webView, "https://www.instagram.com/dpr_ri/?igshid=YmMyMTA2M2Y")
                    }
                    "Facebook" -> {
                        onClickMenuChild(webView, "https://m.facebook.com/DPRRI")
                    }
                    "Twitter" -> {
                        onClickMenuChild(webView, "https://twitter.com/DPR_RI")
                    }
                    "Tiktok" -> {
                        onClickMenuChild(webView, "https://www.tiktok.com/@dpr_ri")
                    }
                    "Youtube" -> {
                        onClickMenuChild(webView, "https://www.youtube.com/channel/UCejL25NjyNxlMR0JqlFX4Dg")
                    }
                    "Official Site" -> {
                        onClickMenuChild(webView, "https://www.dpr.go.id/")
                    }

                    else -> false
                }
                drawerLayout.closeDrawer(Gravity.RIGHT)
                false
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

            // handle onReceived yg bener

//            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
//                val builder = AlertDialog.Builder(this@MainActivity)
//                builder.setMessage(R.string.notification_error_ssl_cert_invalid)
//                builder.setPositiveButton("continue") { dialog, which ->
//                    handler?.proceed()
//                }
//                builder.setNegativeButton("cancel") { dialog, which ->
//                    handler?.cancel()
//                }
//                val dialog = builder.create()
//                dialog.show()
//            }


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
                val contain = "https://emedia.dpr.go.id/ebook/"
                if(url.contains(contain)) {
                    return
                }

                editHeight(webView)
                removeByQuerySelector(webView, ".header")
                removeByQuerySelector(webView, ".theme-switch-box-wrap")
                removeByQuerySelector(webView, "[data-id='afac484']")
                removeByQuerySelector(webView, "[data-id='8a15118']")
                removeByQuerySelector(webView, "[data-id='ac7ba22']")
                removeByQuerySelector(webView, ".footer__main")
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

//    action for AppBar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun loadingVisble() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
    }

    private fun removeByQuerySelector(webView: WebView, classHtml: String) {
        webView.loadUrl("javascript:document.querySelector(\"$classHtml\").remove();")
    }

    private fun editHeight(webView: WebView) {
        webView.evaluateJavascript("""javascript:(function f() {
            let banner = document.querySelector(".elementor-slides-wrapper")
            banner.style.height = "300px"
            let bannerTitle = document.querySelectorAll('.elementor-slide-heading')
            let button = document.querySelectorAll('.elementor-button')
            for (let i = 0; i < bannerTitle.length; i++) {
                bannerTitle[i].style.marginBottom = '30px'
                button[i].style.marginBottom = '120px'
            }
            })()""", null
        )
    }
    private fun onClickMenuChild(webView: WebView, url: String) {
        webView.loadUrl(url)
        loadingVisble()
    }

    private fun paddingByQuerySelector(webView: WebView, classHtml: String, padding: String) {
        webView.loadUrl(""" javascript:document.querySelector("$classHtml").style.paddingBottom = "$padding" """)
    }

    private fun validateUrl(url: String): Boolean {
        val validationResult = url.isNotEmpty() && url.isValidUrl()
        if (!validationResult)
            Toast.makeText(baseContext, "Please prove a valid URL", Toast.LENGTH_SHORT).show()
        return validationResult
    }

    private fun String.isValidUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()
}