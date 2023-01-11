package com.example.marvel_characters.ui.character.details.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.app.ShareCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.squareup.picasso.Picasso
import com.example.marvel_characters.R
import com.example.marvel_characters.data.model.DataModel
import com.example.marvel_characters.data.model.charchters.Character
import com.example.marvel_characters.ui.base.BaseFragment
import com.example.marvel_characters.ui.character.details.viewModel.CharacterDetailsViewModel
import com.example.marvel_characters.ui.character.details.viewModel.CharacterDetailsViewModelFactory
import com.example.marvel_characters.utilities.Constants
import com.example.marvel_characters.utilities.ifNull
import com.example.marvel_characters.data.model.characterDetails.Comic
import io.reactivex.rxjava3.functions.Consumer
import kotlinx.android.synthetic.main.fragment_character_details.*
import kotlinx.android.synthetic.main.fragment_character_details.msg_container
import kotlinx.android.synthetic.main.fragment_character_details.progress_layout
import kotlinx.android.synthetic.main.fragment_character_details.tv_msg
import java.util.ArrayList
import javax.inject.Inject


class CharacterDetailsFragment : BaseFragment(), ComicListAdapter.OnItemClickedListener {
    
    @Inject
    lateinit var characterDetailsViewModelFactory: CharacterDetailsViewModelFactory

    private var shareIntent: ShareCompat.IntentBuilder? = null
    private lateinit var comicListAdapter: ComicListAdapter

    override val layoutId: Int
        get() = R.layout.fragment_character_details

    private val getCharacterDetailsConsumer = Consumer<DataModel<Character>> { detailsDataModel ->
        when (detailsDataModel.status) {
            Constants.Status.SUCCESS -> {
                character_details_container.visibility = View.VISIBLE
                msg_container.visibility = View.GONE
                progress_layout.visibility = View.GONE
                updateDetails(detailsDataModel.responseBody)
            }
            Constants.Status.FAIL -> {
                character_details_container.visibility = View.GONE
                msg_container.visibility = View.VISIBLE
                progress_layout.visibility = View.GONE
                tv_msg.text = detailsDataModel.errorMsg
            }
            Constants.Status.NO_NETWORK -> {
                character_details_container.visibility = View.GONE
                msg_container.visibility = View.VISIBLE
                progress_layout.visibility = View.GONE
                tv_msg.setText(R.string.error_no_internet_connection)
            }
            Constants.Status.LOADING -> {
                msg_container.visibility = View.GONE
                progress_layout.visibility = View.VISIBLE
            }
        }
    }

    private val getCharacterComicsConsumer = Consumer<DataModel<List<Comic>>> { comicsDataModel ->
        when (comicsDataModel.status) {
            Constants.Status.SUCCESS -> {
                rv_comics.visibility = View.VISIBLE
                msg_container.visibility = View.GONE
                progress_layout.visibility = View.GONE
                comicListAdapter.replaceItems(comicsDataModel.responseBody?: emptyList())
            }
            Constants.Status.FAIL -> {
                character_details_container.visibility = View.GONE
                msg_container.visibility = View.VISIBLE
                progress_layout.visibility = View.GONE
                tv_msg.text = comicsDataModel.errorMsg
            }
            Constants.Status.NO_NETWORK -> {
                character_details_container.visibility = View.GONE
                msg_container.visibility = View.VISIBLE
                progress_layout.visibility = View.GONE
                tv_msg.setText(R.string.error_no_internet_connection)
            }
            Constants.Status.LOADING -> {
                msg_container.visibility = View.GONE
                progress_layout.visibility = View.VISIBLE
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_character_details, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    if (menuItem.itemId == R.id.menu_share) {
                        shareIntent?.startChooser()
                        return true
                    }
                    return false
                }

            }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val characterId = getCharacterIdFromBundle(arguments)

        val mViewModel:CharacterDetailsViewModel by viewModels{characterDetailsViewModelFactory}

        mViewModel.getCharacterDetails(characterId).subscribe(getCharacterDetailsConsumer)
        mViewModel.getCharacterComics(characterId).subscribe(getCharacterComicsConsumer)
    }

    private fun getCharacterIdFromBundle(args: Bundle?): String {
        return args?.getString(Constants.Character.ARGS_NEWS_ID , "-1")?: "-1"
    }

    private fun updateDetails(details: Character?) {
        details?.run {
            tv_character_description.text = description
            tv_character_comics.text = getString(R.string.comics, comics?.available.toString())
            tv_character_events.text = getString(R.string.events, events?.available.toString())
            tv_character_stories.text = getString(R.string.stories, stories?.available.toString())
            if (thumbnail?.path.isNullOrEmpty())
                iv_character_image.setImageResource(R.drawable.details_placeholder)
            else {
                Picasso.get().load(thumbnail?.getUrl())
                        .error(R.drawable.character_temp_image)
                        .placeholder(R.drawable.character_temp_image)
                        .into(iv_character_image)
            }
            shareIntent = ShareCompat.IntentBuilder(requireActivity())
                    .setType("text/plain")
                    .setChooserTitle(getString(R.string.share_article_chooser_header))
                    .setText(resourceURI)
        }.ifNull {
            character_details_container.visibility = View.GONE
            msg_container.visibility = View.VISIBLE
            tv_msg.setText(R.string.somethingWrong)
        }
    }


    override fun initializeViews(view: View) {
        comicListAdapter = ComicListAdapter(ArrayList(),this )
        rv_comics.adapter = comicListAdapter
    }

    override fun setListeners() {
    }

    override fun onItemClicked(view: View, item: Comic) {

    }

}
