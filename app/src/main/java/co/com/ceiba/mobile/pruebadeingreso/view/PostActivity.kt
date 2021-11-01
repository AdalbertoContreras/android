package co.com.ceiba.mobile.pruebadeingreso.view

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.adapter.PostAdapter
import co.com.ceiba.mobile.pruebadeingreso.helpers.MySocialMediaSingleton
import co.com.ceiba.mobile.pruebadeingreso.models.Post
import co.com.ceiba.mobile.pruebadeingreso.models.User
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints
import com.google.gson.JsonParser
import io.realm.Realm
import io.realm.RealmConfiguration


class PostActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    private var idUsuario: Int? = 0
    private var user: User? = null
    private lateinit var name: TextView
    private lateinit var phone: TextView
    private lateinit var email: TextView
    private var listaPost: ArrayList<Post> = ArrayList()
    private lateinit var recyclerViewPostsResults: RecyclerView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        super.onStart()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerViewPostsResults = findViewById(R.id.recyclerViewPostsResults);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);

        recyclerViewPostsResults.layoutManager = GridLayoutManager(this, 1)
        recyclerViewPostsResults.setHasFixedSize(true)
        val dividerItemDecoration = DividerItemDecoration(recyclerViewPostsResults.context,
                (recyclerViewPostsResults.layoutManager as LinearLayoutManager).orientation)
        recyclerViewPostsResults.addItemDecoration(dividerItemDecoration)

        val realmName = "prueba"
        val config = RealmConfiguration.Builder()
                .name(realmName)
                .allowWritesOnUiThread(true)
                .build()
        realm = Realm.getInstance(config)

        idUsuario = intent.getIntExtra("userId", 0)
        if(idUsuario != 0){
            user = realm.where(User::class.java).equalTo(User.idProperty, idUsuario).findFirst()
            if(user != null) {
                name.text = user?.name
                phone.text = user?.phone
                email.text = user?.email
                consultarPublicaciones();
            }
        }
    }

    fun consultarPublicaciones() {
        val stringRequest = MySocialMediaSingleton(this.baseContext ).consultaGet("${Endpoints.GET_POST_USER}$idUsuario", null, { response ->
            run {
                val json = JsonParser.parseString(response).asJsonArray;
                for (item in json){
                    val objecUser = item.asJsonObject
                    val post = Post(objecUser)
                    listaPost.add(post)
                }
                var adapterPost = PostAdapter(listaPost)
                recyclerViewPostsResults.adapter = adapterPost
            }
        },
                {
                    run {
                        Log.e("error", "Sin respuesta")
                    }
                }
        )
        if (stringRequest != null) {
            MySocialMediaSingleton(this.baseContext).getInstance(this.baseContext)?.addToRequestQueue(stringRequest, this.baseContext)
        }
    }

    override fun onStart() {
        super.onStart()
    }

}