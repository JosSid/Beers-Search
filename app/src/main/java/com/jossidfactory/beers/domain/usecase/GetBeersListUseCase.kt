package com.jossidfactory.beers.domain.usecase

import com.jossidfactory.beers.data.BeersRepository
import com.jossidfactory.beers.domain.model.Beer

class GetBeersListUseCase (
    private val beersRepository: BeersRepository
) {
    suspend fun invoke(): List<Beer> = beersRepository.getBeers()
}