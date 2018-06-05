package com.everis.authspike.view.activity

import android.content.Intent
import android.os.Bundle
import com.everis.authspike.R
import com.everis.authspike.presenter.ProfilePresenter
import com.everis.authspike.presenter.ProfilePresenterImpl
import com.everis.authspike.view.view.ProfileView

import kotlinx.android.synthetic.main.activity_profile.*
import java.io.InputStream

import android.app.Activity
import android.net.Uri
import com.everis.authspike.utils.GlideApp


class ProfileActivity : BaseActivity(), ProfileView {

    companion object {

        fun getCallingIntent(activity: BaseActivity, phone: String, url: String) : Intent {
            return Intent(activity,ProfileActivity::class.java).putExtra("EXTRA_PHONE",phone).putExtra("EXTRA_URL",url)
        }

    }

    private lateinit var phone : String

    private lateinit var presenter : ProfilePresenter

    private var uri : Uri? = null

    private var url : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        phone = intent.extras.getString("EXTRA_PHONE","")
        url = intent.extras.getString("EXTRA_URL","")
        presenter = ProfilePresenterImpl(this)

        iv_edit.setOnClickListener {
            navigator.navigateToGalley()
        }

        upload.setOnClickListener {
            presenter.uploadPicture()
        }

        GlideApp.with(this)
                .load(url)
                .circleCrop()
                .placeholder(R.drawable.ic_account_circle)
                .dontAnimate()
                .into(iv_profile)

    }

    override fun getUserId(): String = phone

    override fun getPicture(): InputStream? {
        return when (uri) {
            null -> null
            else -> contentResolver.openInputStream(uri)
        }
    }

    override fun displayPicture(url: String) {
       this.url = url
        presenter.updateUser()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun getUrl(): String {
        return url!!
    }

    override fun updateSuccessful() {
        finish()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            uri = data.data
            GlideApp.with(this)
                    .load(uri)
                    .circleCrop()
                    .into(iv_profile)
        }
    }


}
