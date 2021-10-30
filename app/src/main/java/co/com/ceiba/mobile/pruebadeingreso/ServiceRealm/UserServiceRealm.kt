package co.com.ceiba.mobile.pruebadeingreso.ServiceRealm

import co.com.ceiba.mobile.pruebadeingreso.modelsRealm.UserRealm
import io.realm.Realm

class UserServiceRealm {

    fun getUsers(realm: Realm): List<UserRealm> {
        return realm.where(UserRealm::class.java).findAll()
    }

    fun agregarUsuarios(userList: ArrayList<UserRealm>, realm: Realm){
        for(item in  userList) {
            agregarUsuario(item, realm)
        }
    }

    private fun agregarUsuario(user: UserRealm, realm: Realm): Boolean{
        return try {
            realm.executeTransaction { transactionRealm: Realm -> transactionRealm.insert(user) }
            true;
        } catch(e: Throwable) {
            false;
        }
    }
}