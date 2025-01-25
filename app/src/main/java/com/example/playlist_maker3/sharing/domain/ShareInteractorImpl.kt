package com.example.playlist_maker3.sharing.domain

import com.example.playlist_maker3.sharing.data.ExternalNavigator

class ShareInteractorImpl (private val externalNavigator: ExternalNavigator): ShareInteractor {
    override fun open() {
        externalNavigator.open()
    }

    override fun share() {
        externalNavigator.share()
    }

    override fun supportEmail() {
        externalNavigator.supportEmail()
    }
}