package com.example.recyclerviewwithtodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


/* [Adapter]
*   This class functions as an adapter, a bridge or connector between data and the view. So for example,
*   string taskTitle is the data. In order for this data to be shown or converted into a view, displayed
*   on the screen. An adapter is needed to o this job.
*
*
*   The [Adapter] class is of course extends a parent class [RecyclerView.Adapter] which needs
*   a [ViewHolder]. So, developers has done a great job in designing the adapter and we only need
*   to implement the methods from it. There are mainly 3:
*   - onCreateViewHolder: this basically called when the view (view holder) is firstly created, or when
*                         the entire view inside the [ViewHolder] is being rebuild (not the data).
*   - onBindViewHolder: this basically called when the content of a view (view holder) is about to be replaced
*                       (or which view is about to be updated). So, it will access the data from the cache and
*                       update the view inside the view holder).
*   - getItemCount: this is the simplest out of all, which is called to get the number of items/data.
*
*
*   The [ViewHolder] class is the view holder, a representation of a view inside of [RecyclerView], or
*   simply the cells. This class does the job for binding with views such as [ImageView] and [TextView].
*   Apart from extending [RecyclerView.ViewHolder], this class also implements [View.OnClickListener]
*   which handles clicks on the view. The [onClick] overridden method is the place where logic is being
*   placed and called/ran when a click action is done on that specific view holder. In this case,
*   the logic is being passed from outside of the class. There are 2 ways of doing this: using interface
*   (the Java way) and using lambdas. The latter is used in this example. The lambda will then be called
*   inside the [onClick] overridden method.
*
*   When we delete a certain view, it will be animated off the screen. During the animation, it is still
*   possible to click that view. However, that view no longer has any index. So, The 'index != [RecyclerView.NO_POSITION]'
*   makes sure that the item is valid within the array range, that is, >= 0.
*
* */
class Adapter(
    private val items: ArrayList<Item>,
    private val listener: (index: Int) -> Unit
) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_view,
            parent,
            false,
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.taskTitleTextView.text = currentItem.taskTitle
    }

    override fun getItemCount() = items.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val removeItemButton: ImageView = itemView.findViewById(R.id.removeItemButton)
        val taskTitleTextView: TextView = itemView.findViewById(R.id.taskTitleTextView)

        init {
            removeItemButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val index = adapterPosition
            if (index != RecyclerView.NO_POSITION) {
                listener(index)
            }
        }
    }
}