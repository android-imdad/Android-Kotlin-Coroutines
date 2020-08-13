package lk.spacewa.coroutines.data.local.db

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import lk.spacewa.coroutines.GetPokemonsQuery
import lk.spacewa.coroutines.data.model.db.Pokemon

/**
 * Created by Imdad on 7/27/2020.
 */
interface PokemonDBRepo {


    val pokemonLiveData : LiveData<List<Pokemon>>
    val pokemonLiveDataSortedByNumber : LiveData<List<Pokemon>>

    suspend fun insertPokemons(pokemons : List<GetPokemonsQuery.Pokemon>)
}