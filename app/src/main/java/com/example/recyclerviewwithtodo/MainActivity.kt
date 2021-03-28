package com.example.recyclerviewwithtodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/* [MainActivity] class
*
*   More info about Recycler View and Adapter on:
*   - https://developer.android.com/guide/topics/ui/layout/recyclerview
*   - https://medium.com/@kish.imss/listview-vs-recyclerview-2965d50b363
*   - https://stackoverflow.com/questions/26728651/recyclerview-vs-listview
*   - https://developer.android.com/reference/android/widget/Adapter
* */
class MainActivity : AppCompatActivity() {
    private val items: ArrayList<Item> = itemsGenerator(50)
    private val adapter = Adapter(items, {index -> onItemDelete(index)})


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView) // Referencing te recycler view
        recyclerView.adapter = adapter // Specify the adapter
        recyclerView.layoutManager = LinearLayoutManager(this) // Specify the layout manager
        recyclerView.setHasFixedSize(true) // Set it to has fixed size (this is a kind of optimizer used when the view is confirmed to have fixed size)
    }


    /* [itemGenerator]
    *   This method is called to generate a list of items. The number of items depend on the size parameter.
    *   Then a loop will be done to create the items and add then to items [ArrayList].
    * */
    fun itemsGenerator(size: Int): ArrayList<Item> {
        val items = ArrayList<Item>()

        for (i in 0 .. size) {
            val item = Item("Task ${i}")
            items.add(item)
        }

        return items
    }



    /* [onAddItemFloatingButtonPressed]
    *
    *   This method is called when onAddItemFloatingButtonPressed [FloatingButton] is pressed.
    *   It will show an alert Dialog which has a [TextEdit] in which the task or to do title will be
    *   filled. The [builder] is an instance of [AlertDialog.Builder] which builds the [AlertDialog]
    *   The [dialogView] is basically the view inside the [AlertDialog] which content is defined by
    *   programmer. The builder [setPositiveButton] sets the button in case an agreement is made whilst
    *   The builder [setNegativeButton] sets the button if a cancelation is made, which usually exits
    *   the alert dialog. Last but not least, the [setView] is called from [builder] to input the
    *   content created by programmer before shown by using [show] method.
    * */
    fun onAddItemFloatingButtonPressed(view: View) {
        val builder = AlertDialog.Builder(this)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.add_item_dialog, null)
        val dialogTextField: EditText = dialogView.findViewById(R.id.dialogTextField) // Gets the [TextEdit] with id dialogTextField inside of dialogView ([addItemDialog] view)
        builder.setTitle("Add Task")
        builder.setPositiveButton("Add") {
            dialog, which ->
            val taskTitle = dialogTextField.text.toString()
            if (taskTitle.isNotEmpty()) {
                val newItem = Item(taskTitle)
                this.items.add(0, newItem)
                this.adapter.notifyItemInserted(0)
            }
        }

        builder.setNegativeButton("Cancel") {
            dialog, which -> Log.d("Main", "Negative Button Clicked!")
        }

        builder.setView(dialogView)
        builder.show()
    }


    /* [onItemDelete]
    *   This method is called when a certain item in the list is about to be deleted. It needs an
    *   index which specify which view or which item is going to be deleted. This method is called
    *   by a lambda expression inside [Adapter] class' [onClick] method inside [ViewHolder] class.
    * */
    fun onItemDelete(index: Int) {
        this.items.removeAt(index) // Remove the item from the list at index
        this.adapter.notifyItemRemoved(index) // Notify the Recycler View to update the view at index
        Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show() // Show a notification using toast
    }
}