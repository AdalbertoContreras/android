package co.com.ceiba.mobile.pruebadeingreso.ServiceRealm

import co.com.ceiba.mobile.pruebadeingreso.models.User
import io.realm.Realm

class UserServiceRealm {

    fun getUsers(realm: Realm): List<User> {
        return realm.where(User::class.java).findAll()
    }

    fun agregarUsuarios(userList: ArrayList<User>, realm: Realm){
        for(item in  userList) {
            agregarUsuario(item, realm)
        }
    }

    private fun agregarUsuario(user: User, realm: Realm): Boolean{
        return try {
            realm.executeTransaction { transactionRealm: Realm -> transactionRealm.insert(user) }
            true;
        } catch(e: Throwable) {
            false;
        }
    }
}