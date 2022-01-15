package com.naga.super_heroes.ui.base

import androidx.lifecycle.ViewModel
import com.naga.super_heroes.data.repository.BaseRepository

abstract class BaseViewModel(
    private val repository: BaseRepository
): ViewModel() {

    //suspend fun logout(api: AuthApi) = withContext(Dispatchers.IO) { repository.logout(api) }

}