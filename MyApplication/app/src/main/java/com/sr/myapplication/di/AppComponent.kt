package com.sr.myapplication.di

import com.sr.myapplication.network.DataRepository
import com.sr.myapplication.viewmodel.CardsListViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun inject(dataRepository: DataRepository?)
    fun inject(cardsListViewModel: CardsListViewModel?)
}