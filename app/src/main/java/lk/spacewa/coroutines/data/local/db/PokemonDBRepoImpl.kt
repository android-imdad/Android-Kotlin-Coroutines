package lk.spacewa.coroutines.data.local.db


import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import lk.spacewa.coroutines.GetPokemonsQuery
import lk.spacewa.coroutines.data.model.db.Pokemon
import javax.inject.Inject

/**
 * Created by Imdad on 7/27/2020.
 */
class PokemonDBRepoImpl @Inject constructor(private val mAppDatabase: AppDatabase) : PokemonDBRepo {

    private val defaultDispatcher : CoroutineDispatcher = Dispatchers.Default

    override val pokemonsFlow: Flow<List<Pokemon>>
        get() = mAppDatabase.pokemonDao()?.getPokemons()!!

    override val pokemonsSortedByNumber: Flow<List<Pokemon>>
        get() = mAppDatabase.pokemonDao()?.getPokemonsByNumber()!!

    override suspend fun insertPokemons(pokemons: List<GetPokemonsQuery.Pokemon>) {
        mAppDatabase.pokemonDao()?.insertAll(convertModels(pokemons))
    }

    /**
     * Main-safe function running on Dispatchers.Default as to not block the main thread
     */
    private suspend fun convertModels(pokemons : List<GetPokemonsQuery.Pokemon>) : List<Pokemon> = coroutineScope{
        withContext(defaultDispatcher) {
            val pokemonRoomList : ArrayList<Pokemon> = arrayListOf()
            for (pokemon in pokemons) {
                pokemonRoomList.add(Pokemon(pokemon.id(), pokemon.name()!!, pokemon.image()!!, pokemon.number()!!))
            }
            pokemonRoomList
        }
    }



}