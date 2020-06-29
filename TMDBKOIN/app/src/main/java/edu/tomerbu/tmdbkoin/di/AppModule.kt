
package edu.tomerbu.tmdbkoin.di


import edu.tomerbu.tmdbkoin.data.database.MovieDatabase
import edu.tomerbu.tmdbkoin.repository.MovieRepository
import edu.tomerbu.tmdbkoin.repository.MovieRepositoryImpl
import edu.tomerbu.tmdbkoin.ui.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun appModule() = module {
  //get<MovieDatabase>()
  single { MovieDatabase.create(androidContext()) } // database

  //get<MovieDao>()
  single { get<MovieDatabase>().movieDao() } // dao

  //get<MovieRepositoryImpl>()
  single { MovieRepositoryImpl(get(), get()) as MovieRepository } // repository

  // MyViewModel ViewModel

  //get<MainViewModel>(), pass it the MovieRepository
  viewModel { MainViewModel(get()) }


}