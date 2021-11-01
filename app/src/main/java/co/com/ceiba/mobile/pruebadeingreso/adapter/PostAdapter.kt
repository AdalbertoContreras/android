package co.com.ceiba.mobile.pruebadeingreso.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.R

import co.com.ceiba.mobile.pruebadeingreso.models.Post


class PostAdapter(private var lista: List<Post>) : RecyclerView.Adapter<PostAdapter.Companion.ViewHolderDatos>() {

    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDatos {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_list_item, null, false)
        return ViewHolderDatos(view)
    }

    override fun onBindViewHolder(holder: ViewHolderDatos, position: Int) {
        holder.setDatos(lista.get(position))
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    companion object{

        class ViewHolderDatos(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private var title: TextView
            private var body: TextView

            init {
                title = itemView.findViewById(R.id.title)
                body = itemView.findViewById(R.id.body)
            }

            fun setDatos(user: Post){
                title.text = user.title
                body.text = user.body
            }
        }
    }

}