package com.example.marvel_characters.ui.character.list.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.example.marvel_characters.R
import kotlinx.android.synthetic.main.item_character.view.*
import java.util.*
import com.example.marvel_characters.data.model.charchters.Character

class CharacterListAdapter(list: List<Character>, private val onItemClickListener: OnItemClickedListener) : RecyclerView.Adapter<CharacterListAdapter.CharacterHolder>() {

    private val CharacterList: MutableList<Character>

    init {
        CharacterList = ArrayList(list)
    }

    fun addItems(list: List<Character>) {
        CharacterList.addAll(list)
        notifyDataSetChanged()
    }

    fun replaceItems(list: List<Character>) {
        CharacterList.clear()
        CharacterList.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        CharacterList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return CharacterList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return CharacterHolder(root)
    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        holder.bind(if (CharacterList.size > position) CharacterList[position] else null)
    }

    /**
     * View Holder for a [Character] RecyclerView list item.
     */
    inner class CharacterHolder internal constructor(rootView: View) : RecyclerView.ViewHolder(rootView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        internal fun bind(item: Character?) {

            item?.let { character ->
                itemView.run {
                    tv_character_title.text = character.name
                    tv_character_description.text = character.description
                    tv_character_comics.text = itemView.context.getString(R.string.comics, character.comics?.available.toString())
                    tv_character_events.text = itemView.context.getString(R.string.events, character.events?.available.toString())
                    tv_character_stories.text = itemView.context.getString(R.string.stories, character.stories?.available.toString())

                    if (character.thumbnail == null || character.thumbnail?.path!!.isEmpty())
                        iv_character_image.setImageResource(R.drawable.character_temp_image)
                    else {
                        Picasso.get().load(character.thumbnail?.getUrl())
                                .error(R.drawable.character_temp_image)
                                .placeholder(R.drawable.character_temp_image)
                                .into(iv_character_image)
                    }
                    animateViewHolder()
                }
            }
        }

        private fun animateViewHolder() {
            val itemAnimator = AnimationUtils.loadLayoutAnimation(itemView.context, R.anim.partial_in_from_left_animation)
            val labelAnimator = AnimationUtils.loadAnimation(itemView.context, R.anim.partial_in_from_right)
            itemView.startAnimation(itemAnimator.animation)
            itemView.iv_character_type_label.startAnimation(labelAnimator)
        }

        /**
         * Called when a view has been clicked.
         *
         * @param view The view that was clicked.
         */
        override fun onClick(view: View) {
            if (adapterPosition > RecyclerView.NO_POSITION && adapterPosition < CharacterList.size) {
                onItemClickListener.onItemClicked(view, CharacterList[adapterPosition])
            }
        }
    }


    interface OnItemClickedListener {
        fun onItemClicked(view: View, item: Character)
    }
}