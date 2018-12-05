package com.messapps.aoapp.ui.friendslist

import android.arch.lifecycle.LifecycleOwner
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.messapps.aoapp.databinding.FriendsListFragmentBinding
import com.messapps.arch.ConcreteViewComponent
import com.messapps.arch.LiveData
import io.reactivex.Observer

class FriendsListView(
    viewData: FriendsListData,
    hostFragment: Fragment,
    parent: ViewGroup
) : ConcreteViewComponent {

    override val rootView: View

    override fun unbindData() {
        // nothing to do
    }

    init {
        val binding = FriendsListFragmentBinding.inflate(hostFragment.layoutInflater, parent, false)
        binding.setLifecycleOwner(hostFragment)
        binding.data = viewData
        rootView = binding.root

        binding.friendsListRecycler.let {
            it.adapter = StdListAdapter(viewData.friendsList, viewData.showFriendInfoWish)
            it.layoutManager = LinearLayoutManager(hostFragment.context)
        }
    }

    private class StdListAdapter(
        private val data: LiveData<List<String>>,
        private val wishes: Observer<Int>?
    ) : RecyclerView.Adapter<StdListAdapter.ViewHolder>() {

        override fun getItemCount(): Int = data.value.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
            val view: View = LayoutInflater.from(parent.context)
                    .inflate(android.R.layout.simple_list_item_1, parent, false)

            val textView: TextView = view.findViewById(android.R.id.text1)

            return ViewHolder(textView, view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = data.value[position]

            if (wishes != null) {
                holder.itemView.setOnClickListener {
                    wishes.onNext(position)
                }
            }
        }

        class ViewHolder(val text: TextView, itemRoot: View) : RecyclerView.ViewHolder(itemRoot)
    }

    private abstract class DataBindingListAdapter<ItemDataBindingType: ViewDataBinding, ItemDataType, ItemWishes>(
        private val lifecycleHolder: LifecycleOwner,
        @LayoutRes
        private val itemLayoutResource: Int,
        private val data: LiveData<List<ItemDataType>>,
        private val wishes: Observer<ItemWishes>
    ) : RecyclerView.Adapter<DataBindingListAdapter.ViewHolder<ItemDataBindingType>>() {

        abstract fun setDataForItem(itemBinding: ItemDataBindingType, data: ItemDataType, wishes: Observer<ItemWishes>)

        override fun getItemCount(): Int = data.value.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ItemDataBindingType> {
            val rawBinding: ViewDataBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), itemLayoutResource, parent, false)

            @Suppress("UNCHECKED_CAST")
            val binding: ItemDataBindingType? = rawBinding as? ItemDataBindingType

            if (binding == null) {
                throw IllegalArgumentException(
                    "itemLayoutResource was inflated into wrong data binding type: ${rawBinding::class.java.name}"
                )
            }

            binding.setLifecycleOwner(lifecycleHolder)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder<ItemDataBindingType>, position: Int) {
            setDataForItem(holder.binding, data.value[position], wishes)
        }

        class ViewHolder<ItemDataBindingType: ViewDataBinding>(
            val binding: ItemDataBindingType
        ) : RecyclerView.ViewHolder(binding.root)
    }
}