package edu.tomerbu.tmdbkoin

import edu.tomerbu.tmdbkoin.ui.main.MainViewModel
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel


val appModule = module {

    // single instance of HelloRepository
    single<HelloRepository> { HelloRepositoryImpl() }

    // MyViewModel ViewModel
    viewModel { MainViewModel(get()) }
}