package br.com.ramonmluz.moviehub.di

import br.com.ramonmluz.moviehub.data.repository.MovieRepositoryImpl
import br.com.ramonmluz.moviehub.domain.business.MovieBusiness
import br.com.ramonmluz.moviehub.domain.repository.MovieRepository
import br.com.ramonmluz.moviehub.presentation.ui.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

class MovieModules {

    fun load() = loadKoinModules(module {
        viewModel()
        business()
        repository()
    })

    private fun Module.viewModel() {
        viewModel {
            MovieViewModel(
                movieBusiness = get()
            )
        }
    }

    private fun Module.business() {
        factory { MovieBusiness(movieRepository = get()) }
    }

    private fun Module.repository() {
        single<MovieRepository> { MovieRepositoryImpl(api = get()) }
    }
}