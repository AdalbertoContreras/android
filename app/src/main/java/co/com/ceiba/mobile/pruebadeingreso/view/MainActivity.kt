package co.com.ceiba.mobile.pruebadeingreso.view

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.interfaces.AbrirActivity
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.ServiceRealm.UserServiceRealm
import co.com.ceiba.mobile.pruebadeingreso.adapter.UserAdapter
import co.com.ceiba.mobile.pruebadeingreso.helpers.MySocialMediaSingleton
import co.com.ceiba.mobile.pruebadeingreso.models.User
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints
import com.google.gson.JsonParser
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.realm.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private lateinit var userList: List<User>
    private lateinit var realm: Realm;
    private lateinit var recyclerViewSearchResults: RecyclerView;
    private lateinit var editTextSearch: TextView;
    private lateinit var adapterUser: UserAdapter;
    private lateinit var abrirActivity: AbrirActivity;
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressDialog = ProgressDialog(this)
        progressDialog!!.setIcon(R.mipmap.ic_launcher)
        progressDialog.setMessage("Por favor espere..")
        progressDialog.show()

        recyclerViewSearchResults = findViewById(R.id.recyclerViewSearchResults)
        editTextSearch = findViewById(R.id.editTextSearch)

        recyclerViewSearchResults.layoutManager = GridLayoutManager(this, 1)
        recyclerViewSearchResults.setHasFixedSize(true)
        val dividerItemDecoration = DividerItemDecoration(recyclerViewSearchResults.context,
                (recyclerViewSearchResults.layoutManager as LinearLayoutManager).orientation)
        recyclerViewSearchResults.addItemDecoration(dividerItemDecoration)

        abrirActivity = object : AbrirActivity {
            override fun openPostActivity(userId: Int) {
                val intent = Intent(applicationContext, PostActivity::class.java)
                intent.putExtra("userId", userId)
                startActivity(intent)
            }
        }

        editTextSearch.doOnTextChanged { text, start, before, count ->
            run {
                userList = realm.where(User::class.java)
                        .contains(User.nameProperty, text.toString(), Case.INSENSITIVE)
                        .findAll()
                    adapterUser.setUsuarios(userList)
                if(userList.isEmpty()) {
                    //Toast.makeText(this, "List is empty", Toast.LENGTH_SHORT).show()
                    val snackbar = Snackbar
                            .make(this.content, "List is empty", Snackbar.LENGTH_LONG)
                    snackbar.show()
                }
            }
        }

    }

    fun mostrarUsuarios() {
        adapterUser = UserAdapter(userList, abrirActivity)
        recyclerViewSearchResults.adapter = adapterUser
    }

    fun comprobarUsuarios() {
        userList = UserServiceRealm().getUsers(realm)
        if(userList.isEmpty()) {
            consultarUsuarioWebService();
        } else {
            mostrarUsuarios()
            progressDialog.dismiss()
        }
    }

    fun consultarUsuarioWebService() {
        val stringRequest = MySocialMediaSingleton(this.baseContext ).consultaGet(Endpoints.GET_USERS, null, { response ->
            run {
                val json = JsonParser.parseString(response).asJsonArray;
                val userArray = ArrayList<User>()
                for (item in json){
                    val objecUser = item.asJsonObject
                    val user = User(objecUser)
                    userArray.add(user)
                }
                UserServiceRealm().agregarUsuarios(userArray, realm)
                userList = UserServiceRealm().getUsers(realm)
                mostrarUsuarios()
                progressDialog.dismiss()
            }
        },
                {
                    run {
                        Log.e("error", "Sin respuesta")
                        progressDialog.dismiss()
                    }
                }
        )
        if (stringRequest != null) {
            MySocialMediaSingleton(this.baseContext).getInstance(this.baseContext)?.addToRequestQueue(stringRequest, this.baseContext)
        }
    }

    override fun onStart() {
        super.onStart()

        val realmName = "prueba"
        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .name(realmName)
                .allowWritesOnUiThread(true)
                .build()
        realm = Realm.getInstance(config)
        comprobarUsuarios()
    }
}