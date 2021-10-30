package co.com.ceiba.mobile.pruebadeingreso.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.ServiceRealm.UserServiceRealm
import co.com.ceiba.mobile.pruebadeingreso.helpers.MySocialMediaSingleton
import co.com.ceiba.mobile.pruebadeingreso.modelsRealm.UserRealm
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints
import com.google.gson.JsonParser
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.mongodb.sync.SyncConfiguration
import java.lang.Exception

class MainActivity : Activity() {


    private lateinit var userList: List<UserRealm>
    private lateinit var realm: Realm;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this)

        val realmName = "prueba"
        val config = RealmConfiguration.Builder()
                .name(realmName)
                .allowWritesOnUiThread(true)
                .build()

        realm = Realm.getInstance(config)

        comprobarUsuarios()

    }

    fun comprobarUsuarios() {
        userList = UserServiceRealm().getUsers(realm)
        if(userList.size == 0) {
            consultarUsuarioWebService();
        }
    }

    fun consultarUsuarioWebService() {
        val stringRequest = MySocialMediaSingleton(this.baseContext ).consulta(Endpoints.GET_USERS, null, { response ->
            run {
                val json = JsonParser.parseString(response).asJsonArray;
                val userArray = ArrayList<UserRealm>()
                for (item in json){
                    val objecUser = item.asJsonObject
                    val user = UserRealm(objecUser)

                    userArray.add(user)
                }
                UserServiceRealm().agregarUsuarios(userArray, realm)
                userList = UserServiceRealm().getUsers(Realm.getDefaultInstance())
            }
        },
                { error ->{
                    Log.e("error", "Sin respuesta")
                }  }
        )
        if (stringRequest != null) {
            MySocialMediaSingleton(this.baseContext).getInstance(this.baseContext)?.addToRequestQueue(stringRequest, this.baseContext)
        }
    }


    override fun onResume() {

        super.onResume();
    }

    override fun onStart() {
        super.onStart()
    }
}