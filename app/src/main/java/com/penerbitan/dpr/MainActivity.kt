package com.penerbitan.dpr

import CustomExpandableListAdapter
import ExpandableListData.data
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
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.compose.ui.text.toLowerCase
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle

class MainActivity : AppCompatActivity() {
    // Expandable List properti
    private var expandableListView: ExpandableListView? = null
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null

    private lateinit var webView: WebView
    private lateinit var bottomNavigationView: BottomNavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    var urlLink = "https://penerbitan-dpr.id/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        drawerLayout = findViewById(R.id.my_drawer_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
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
                        onClickMenuChild(webView, "https://penerbitan-dpr.id/ebook/")
                    }
                    "E-Magazine" -> {
                        onClickMenuChild(webView, "https://www.dpr.go.id/serba-serbi/majalah-parlementaria")
                    }
                    "E-Buletin" -> {
                        onClickMenuChild(webView, "https://www.dpr.go.id/serba-serbi/buletin-parlementaria")
                    }
                    "Warta Parlemen" -> {
                        onClickMenuChild(webView, "https://tvr.dpr.go.id/tv/program/index/id/PL1i5C6Kd5FQjHkoWG5UaO4jcNlkxGNRwp")
                    }
                    "Mutiara Parlemen" -> {
                        onClickMenuChild(webView, "https://tvr.dpr.go.id/tv/program/index/id/PL1i5C6Kd5FQjc-mj58EhTmYWAjC_9SRUW")
                    }
                    "PSA DPR RI" -> {
                        onClickMenuChild(webView, "https://tvr.dpr.go.id/tv/program/index/id/PL1i5C6Kd5FQiKLjuBRUh98TXIjJRCDfvJ")
                    }
                    "Perempuan Parlemen" -> {
                        onClickMenuChild(webView, "https://tvr.dpr.go.id/tv/program/index/id/PL1i5C6Kd5FQjrRZc626S7lfa6Pu6-CL8E")
                    }
                    "KURMA DPR" -> {
                        onClickMenuChild(webView, "https://tvr.dpr.go.id/tv/program/index/id/PL1i5C6Kd5FQj7TTivlTbj9HCCe3xHqp_M")
                    }
                    "EPN" -> {
                        onClickMenuChild(webView, "https://tvr.dpr.go.id/tv/program/index/id/PL1i5C6Kd5FQjgHkQDoSfn9XEHjhM9ttaG")
                    }
                    "Teras Parlemen" -> {
                        onClickMenuChild(webView, "https://tvr.dpr.go.id/tv/program/index/id/PL1i5C6Kd5FQj7TTivlTbj9HCCe3xHqp_M")
                    }
                    "Film DPR RI" -> {
                        onClickMenuChild(webView, "https://tvr.dpr.go.id/tv/program/index/id/PL1i5C6Kd5FQgQi3yAVSTuINcLDC1jZQey")
                    }
                    "Bedah RUU" -> {
                        onClickMenuChild(webView, "https://tvr.dpr.go.id/tv/program/index/id/PL1i5C6Kd5FQiW3WfTcw9Nwur7ceU08FAn")
                    }
                    "Liputan Khusus" -> {
                        onClickMenuChild(webView, "https://tvr.dpr.go.id/tv/program/index/id/PL1i5C6Kd5FQi5FRfZrqFPEU-DNLOlN3fr")
                    }
                    "Sidang Bersama DPR RI & DPD RI" -> {
                        onClickMenuChild(webView, "https://tvr.dpr.go.id/tv/program/index/id/PL1i5C6Kd5FQjxBJVDo1eTzjubas3zkRAf")
                    }
                    "ID Station" -> {
                        onClickMenuChild(webView, "https://tvr.dpr.go.id/tv/program/index/id/PL1i5C6Kd5FQiM3upbJqtD0mSLISwa1dyW")
                    }
                    "Serba - Serbi Parlemen" -> {
                        onClickMenuChild(webView, "https://tvr.dpr.go.id/tv/program/index/id/PL1i5C6Kd5FQhdUkn6xaO6F6W2JNfE377d")
                    }
                    "Paripurna" -> {
                        onClickMenuChild(webView, "https://tvr.dpr.go.id/tv/program/index/id/PL1i5C6Kd5FQiiPxEuab6MN5y0cnWhqJY6")
                    }
                    "Mutiara Ramadhan" -> {
                        onClickMenuChild(webView, "https://tvr.dpr.go.id/tv/program/index/id/PL1i5C6Kd5FQih9RXaMcdAsGM1Fsmr8F8t")
                    }
                    "Live Komisi" -> {
                        onClickMenuChild(webView, "https://tvr.dpr.go.id/tv/program/index/id/PL1i5C6Kd5FQi5AyGzdKsZaiJyBV828xUQ")
                    }
                    "Semangat Petang Parlemen" -> {
                        onClickMenuChild(webView, "https://tvr.dpr.go.id/tv/program/index/id/PL1i5C6Kd5FQj9iJKKgXsRKOO3MctSvf89")
                    }
                    "Semangat Pagi Parlemen" -> {
                        onClickMenuChild(webView, "https://tvr.dpr.go.id/tv/program/index/id/PL1i5C6Kd5FQilsvuyQZWaNoVKsBWWEttC")
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
//                removeByQuerySelector(webView, "[data-id='4ee4692']")
//                removeByQuerySelector(webView, "[data-id='e073c6e']")
//                removeByQuerySelector(webView, "[data-id='38a7f288']")
//                removeByQuerySelector(webView, "[data-id='31075929']")
//                removeByQuerySelector(webView, "[data-id='471b541a']")
//                removeByQuerySelector(webView, "[data-id='45a1d38']")
                removeByQuerySelector(webView, "[data-id='3dd7e947']")
                removeByQuerySelector(webView, "[data-id='442b8f5b']")
                removeByQuerySelector(webView, ".footer__main")
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

    private fun onClickMenuChild(webView: WebView, url: String) {
        webView.loadUrl(url)
        loadingVisble()
    }

    private fun paddingByQuerySelector(webView: WebView, classHtml: String, padding: String) {
        webView.loadUrl("javascript:document.querySelector(\"$classHtml\").style.paddingBottom = \"$padding\"")
    }

    private fun validateUrl(url: String): Boolean {
        val validationResult = url.isNotEmpty() && url.isValidUrl()
        if (!validationResult)
            Toast.makeText(baseContext, "Please prove a valid URL", Toast.LENGTH_SHORT).show()
        return validationResult
    }

    private fun String.isValidUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()
}