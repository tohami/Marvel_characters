package com.example.marvel_characters.ui.character.list.view

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.marvel_characters.R
import com.example.marvel_characters.data.model.DataModel
import com.example.marvel_characters.data.model.charchters.Character
import com.example.marvel_characters.ui.base.BaseFragment
import com.example.marvel_characters.ui.character.list.viewModel.CharacterListViewModel
import com.example.marvel_characters.ui.character.list.viewModel.CharacterListViewModelFactory
import com.example.marvel_characters.utilities.Constants
import io.reactivex.rxjava3.functions.Consumer
import kotlinx.android.synthetic.main.fragment_character_list.*
import java.util.*
import javax.inject.Inject


class CharacterListFragment : BaseFragment(), CharacterListAdapter.OnItemClickedListener {

    private lateinit var characterListAdapter: CharacterListAdapter
    private lateinit var mViewModel: CharacterListViewModel

    @Inject
    lateinit var viewModelFactory: CharacterListViewModelFactory

    override val layoutId: Int
        get() = R.layout.fragment_character_list


    private val getCharacterConsumer = Consumer<DataModel<List<Character>>> { listDataModel ->
        when (listDataModel.status) {
            Constants.Status.SUCCESS -> {
                rv_character.visibility = View.VISIBLE
                msg_container.visibility = View.GONE
                refresh_layout.isRefreshing = false
                progress_layout.visibility = View.GONE
                characterListAdapter.replaceItems(listDataModel.responseBody?: emptyList())
            }
            Constants.Status.FAIL -> {
                rv_character.visibility = View.GONE
                msg_container.visibility = View.VISIBLE
                refresh_layout.isRefreshing = false
                progress_layout.visibility = View.GONE
                tv_msg.text = listDataModel.errorMsg
            }
            Constants.Status.NO_NETWORK -> {
                rv_character.visibility = View.GONE
                msg_container.visibility = View.VISIBLE
                refresh_layout.isRefreshing = false
                progress_layout.visibility = View.GONE
                tv_msg.setText(R.string.error_no_internet_connection)
            }
            Constants.Status.LOADING -> {
                if (!refresh_layout.isRefreshing) {
                    progress_layout.visibility = View.VISIBLE
                }
                msg_container.visibility = View.GONE
                characterListAdapter.replaceItems(emptyList())
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_character_list, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    if (menuItem.itemId == R.id.menu_filter) {
                        showSimpleSnack(getString(R.string.filter_clicked))
                        return true
                    }
                    return false
                }

            }, viewLifecycleOwner, Lifecycle.State.RESUMED)


        mViewModel = ViewModelProvider(requireActivity(), viewModelFactory)[CharacterListViewModel::class.java]

        mViewModel.characterSubject.subscribe(getCharacterConsumer)
    }

    override fun initializeViews(view: View) {
        characterListAdapter = CharacterListAdapter(ArrayList(), this)
        rv_character.adapter = characterListAdapter
    }

    override fun setListeners() {
        refresh_layout.setOnRefreshListener { mViewModel.refreshCharacterList() }

    }


    override fun onItemClicked(view: View, item: Character) {
        val args = Bundle().apply {
            putString(Constants.Character.ARGS_NEWS_ID, item.id.toString())
        }
        Navigation
                .findNavController(view)
                .navigate(R.id.action_fragmentCharacterList_to_fragmentCharacterDetails, args)
    }

}
