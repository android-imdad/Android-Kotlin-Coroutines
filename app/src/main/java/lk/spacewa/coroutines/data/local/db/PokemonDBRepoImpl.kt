package lk.spacewa.coroutines.data.local.db


import kotlinx.coroutines.flow.Flow
import lk.spacewa.coroutines.data.model.db.Pokemon
import javax.inject.Inject

/**
 * Created by Imdad on 7/27/2020.
 */
class PokemonDBRepoImpl @Inject constructor(private val mAppDatabase: AppDatabase) : PokemonDBRepo {

    override val pokemonsFlow: Flow<List<Pokemon>>
        get() = mAppDatabase.pokemonDao()?.getPokemons()!!

    override val pokemonsSortedByNumber: Flow<List<Pokemon>>
        get() = mAppDatabase.pokemonDao()?.getPokemonsByNumber()!!

    override suspend fun insertPokemons(pokemons: List<Pokemon>) {
        mAppDatabase.pokemonDao()?.insertAll(pokemons)
    }


}