package com.example.playlist_maker3.settings.ui
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlist_maker3.settings.domain.DarkThemeState
import com.example.playlist_maker3.settings.domain.interactor.DarkThemeInteractor
import com.example.playlist_maker3.sharing.domain.ShareInteractor

class SettingsActivityViewModel(
    private val shareInteractor: ShareInteractor,
    private val darkThemeInteractor: DarkThemeInteractor
) : ViewModel() {


    private val stateLiveData = MutableLiveData<DarkThemeState>()
    fun observeState(): LiveData<DarkThemeState> = stateLiveData


    private fun updateThemeSetting(settings: Boolean) {
        darkThemeInteractor.updateThemeSetting(settings)
    }
    fun makeItDark(){
        updateThemeSetting(true)
        stateLiveData.postValue(DarkThemeState.Dark)
    }
    fun makeItBright(){
        updateThemeSetting(false)
        stateLiveData.postValue(DarkThemeState.Bright)
    }

    fun getTheme() {
       val theme = darkThemeInteractor.getThemeSettings()
        if(theme){
            stateLiveData.postValue(DarkThemeState.Dark)
        }
    }


    fun open() {
        shareInteractor.open()
    }

    fun share() {
        shareInteractor.share()
    }

    fun support() {
        shareInteractor.supportEmail()
    }
}