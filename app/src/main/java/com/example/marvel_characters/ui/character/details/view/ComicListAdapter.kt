package com.example.marvel_characters.ui.character.details.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.example.marvel_characters.R
import java.util.*
import com.example.marvel_characters.data.model.characterDetails.Comic
import kotlinx.android.synthetic.main.item_comic.view.*

class ComicListAdapter(list: List<Comic>, private val onItemClickListener: OnItemClickedListener) : RecyclerView.Adapter<ComicListAdapter.ComicHolder>() {

    private val comicList: MutableList<Comic>

    init {
        comicList = ArrayList(list)
    }

    fun addItems(list: List<Comic>) {
        comicList.addAll(list)
        notifyDataSetChanged()
    }

    fun replaceItems(list: List<Comic>) {
        comicList.clear()
        comicList.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        comicList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return comicList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_comic, parent, false)
        return ComicHolder(root)
    }

    override fun onBindViewHolder(holder: ComicHolder, position: Int) {
        holder.bind(if (comicList.size > position) comicList[position] else null)
    }

    /**
     * View Holder for a [Comic] RecyclerView list item.
     */
    inner class ComicHolder internal constructor(rootView: View) : RecyclerView.ViewHolder(rootView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        internal fun bind(item: Comic?) {

            item?.let { comic ->
                itemView.run {
                    tv_comic_title.text = comic.title
                    tv_comic_characters.text = itemView.context.getString(R.string.characters, comic.characters?.available.toString())
                    tv_comic_events.text = itemView.context.getString(R.string.events, comic.events?.available.toString())
                    tv_comic_stories.text = itemView.context.getString(R.string.stories, comic.stories?.available.toString())

                    if (comic.thumbnail == null || comic.thumbnail?.path!!.isEmpty())
                        iv_comic_image.setImageResource(R.drawable.character_temp_image)
                    else {
                        Picasso.get().load(comic.thumbnail?.getUrl())
                                .error(R.drawable.character_temp_image)
                                .placeholder(R.drawable.character_temp_image)
                                .into(iv_comic_image)
                    }
                    animateViewHolder()
                }
            }
        }

        private fun animateViewHolder() {
            val itemAnimator = AnimationUtils.loadLayoutAnimation(itemView.context, R.anim.partial_in_from_left_animation)
            itemView.startAnimation(itemAnimator.animation)
        }

        /**
         * Called when a view has been clicked.
         *
         * @param view The view that was clicked.
         */
        override fun onClick(view: View) {
            if (adapterPosition > RecyclerView.NO_POSITION && adapterPosition < comicList.size) {
                onItemClickListener.onItemClicked(view, comicList[adapterPosition])
            }
        }
    }


    interface OnItemClickedListener {
        fun onItemClicked(view: View, item: Comic)
    }
}