package lk.spacewa.coroutines.data.local.db

import kotlinx.coroutines.flow.Flow
import lk.spacewa.coroutines.data.model.db.Pokemon

/**
 * Created by Imdad on 7/27/2020.
 */
interface PokemonDBRepo {

    val pokemonsFlow : Flow<List<Pokemon>>
    val pokemonsSortedByNumber : Flow<List<Pokemon>>
    suspend fun insertPokemons(pokemons : List<Pokemon>)
}