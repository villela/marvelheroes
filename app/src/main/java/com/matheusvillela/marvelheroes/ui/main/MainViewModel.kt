package com.matheusvillela.marvelheroes.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matheusvillela.marvelheroes.model.ApiCharacterDataResult
import com.matheusvillela.marvelheroes.shared.Api
import com.matheusvillela.marvelheroes.shared.ViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(private val api: Api) : ViewModel() {
    val characters = MutableLiveData<List<ApiCharacterDataResult>>()
    val character = MutableLiveData<ApiCharacterDataResult>()
    val status = MutableLiveData<ViewState>()
    private val disposables = CompositeDisposable()
    private var currentIndex = 0

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        if (status.value == ViewState.LOADING) {
            return
        }
        status.value = ViewState.LOADING
        disposables.add(api.characters(currentIndex, 50)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ it ->
                    it?.let { result ->
                        val list = characters.value?.toMutableList() ?: mutableListOf()
                        list.addAll(result.data.results)
                        currentIndex = result.data.offset + result.data.count
                        characters.value = list
                        status.value = ViewState.LOADED
                    }
                }, {
                    Timber.d(it, "error getting characters")
                    status.value = ViewState.ERROR
                }))
    }

    override fun onCleared() {
        disposables.dispose()
    }

    fun setCharacter(character: ApiCharacterDataResult) {
        this.character.value = character
    }

}
