package co.com.ceiba.mobile.pruebadeingreso.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.models.User

import co.com.ceiba.mobile.pruebadeingreso.interfaces.AbrirActivity


class UserAdapter(private var lista: List<User>, var abrirActivity: AbrirActivity) : RecyclerView.Adapter<UserAdapter.Companion.ViewHolderDatos>() {

    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDatos {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item, null, false)
        return ViewHolderDatos(view)
    }

    override fun onBindViewHolder(holder: ViewHolderDatos, position: Int) {
        holder.setDatos(lista.get(position), abrirActivity)
    }

    fun setUsuarios(lista: List<User>) {
        this.lista = lista;
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    companion object{

        class ViewHolderDatos(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private var name: TextView
            private var phone: TextView
            private var email: TextView
            private var btn_view_post: Button

            init {
                name = itemView.findViewById(R.id.name)
                phone = itemView.findViewById(R.id.phone)
                email = itemView.findViewById(R.id.email)
                btn_view_post = itemView.findViewById(R.id.btn_view_post)
            }

            fun setDatos(user: User, abrirActivity: AbrirActivity){
                name.text = user.name
                phone.text = user.phone
                email.text = user.email
                btn_view_post.setOnClickListener {
                    abrirActivity.openPostActivity(user.id)
                }
            }
        }
    }

}