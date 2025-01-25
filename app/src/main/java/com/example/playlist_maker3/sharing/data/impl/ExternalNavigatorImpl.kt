package com.example.playlist_maker3.sharing.data.impl

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri

import com.example.playlist_maker3.R
import com.example.playlist_maker3.player.ui.PlayerActivity.Companion.startActivity
import com.example.playlist_maker3.sharing.data.ExternalNavigator


class ExternalNavigatorImpl(private val context: Context) : ExternalNavigator {
    override fun open() {
        val agreementIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.agreement_url)))
        agreementIntent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(agreementIntent)
    }

    override fun share() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/x-uri")
        Intent.createChooser(shareIntent, null)
        shareIntent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.android_url))
        shareIntent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(shareIntent)

    }

    override fun supportEmail() {
        val supportIntent = Intent(Intent.ACTION_SENDTO)
        supportIntent.data = Uri.parse("mailto:")
        supportIntent.putExtra(
            Intent.EXTRA_EMAIL,
            arrayOf(context.getString(R.string.support_email))
        )
        supportIntent.putExtra(
            Intent.EXTRA_SUBJECT,
            context.getString(R.string.support_subject)
        )
        supportIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        supportIntent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.support_text))
        context.startActivity(supportIntent)
    }
}