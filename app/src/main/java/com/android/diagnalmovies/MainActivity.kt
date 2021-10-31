package com.android.diagnalmovies

import android.os.Bundle
import androidx.core.view.isVisible
import com.android.core.platform.BaseActivity
import com.android.core.platform.PageAppearanceConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        if (savedInstanceState == null) {
            navigateToHome()
        }
    }

    override fun configurePageAppearance(configuration: PageAppearanceConfiguration) {
        with(configuration) {
            toolbar_title?.text = pageTitle
            if (isToolbarRequired) {
                viewToolbarShadow?.isVisible = true
                supportActionBar?.show()
            } else {
                viewToolbarShadow?.isVisible = false
                supportActionBar?.hide()
            }
        }
    }

    private fun initUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        imageBack?.setOnClickListener { onBackPressed() }
        imageSearch?.setOnClickListener { navigationManager.navigateToSearch(this) }
    }

    private fun navigateToHome() {
        navigationManager.navigateToHome(this)
    }

    override fun getFrameContainer() = R.id.container
}
