package com.matheusvillela.marvelheroes.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matheusvillela.marvelheroes.model.ApiCharacter
import com.matheusvillela.marvelheroes.model.ApiCharacterDataResults
import com.matheusvillela.marvelheroes.shared.Api
import com.matheusvillela.marvelheroes.shared.ViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(private val api: Api) : ViewModel() {
    val teste = MutableLiveData<String>()
    private val disposables = CompositeDisposable()
    private val characters = MutableLiveData<List<ApiCharacterDataResults>>()
    private val status = MutableLiveData<ViewState>()
    private var currentIndex = 0

    init {
        teste.value = "Teste"
        loadNextPage()
    }

    fun loadNextPage() {
        if (status.value == ViewState.LOADING) {
            return
        }
        status.value = ViewState.LOADING
        disposables.add(api.characters(currentIndex, 20)
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

}
