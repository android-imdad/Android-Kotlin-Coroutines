package lk.spacewa.coroutines.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import lk.spacewa.coroutines.R
import lk.spacewa.coroutines.data.model.db.Starwars
import lk.spacewa.coroutines.databinding.ItemStarwarsDetailsBinding


class HomeRvAdapter : ListAdapter<Starwars, HomeRvAdapter.ViewHolder?>(StarwarsDiffCallBack()) {


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create a new view
        val layoutInflater = LayoutInflater.from(parent.context)
        // set the view's size, margins, paddings and layout parameters
        val binding: ItemStarwarsDetailsBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_starwars_details, parent, false)
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        initRecyclerView(holder, position)
    }

    private fun initRecyclerView(holder: ViewHolder, position: Int) {
        val starwars: Starwars = getItem(position)

        holder.mBinding.tvStarwarsTitle.text = starwars.title
        holder.mBinding.tvStarwarsDesc.text = starwars.director


    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder(itemBinding: ItemStarwarsDetailsBinding) : RecyclerView.ViewHolder(itemBinding.getRoot()), View.OnClickListener {
        // each data item is just a string in this case
        val mBinding: ItemStarwarsDetailsBinding = itemBinding
        override fun onClick(v: View) {}

    }


    /**
     * Uses DiffUtil to compare items in a list and automatically make changes only to the items which differ
     */
    private class StarwarsDiffCallBack : DiffUtil.ItemCallback<Starwars>() {

        override fun areItemsTheSame(oldItem: Starwars, newItem: Starwars): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Starwars, newItem: Starwars): Boolean {
            return oldItem == newItem
        }
    }
}