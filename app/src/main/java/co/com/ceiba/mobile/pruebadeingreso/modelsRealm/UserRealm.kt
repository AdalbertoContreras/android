package co.com.ceiba.mobile.pruebadeingreso.modelsRealm
import com.google.gson.JsonObject
import io.realm.annotations.PrimaryKey
import io.realm.RealmObject;
import io.realm.annotations.Required


open class UserRealm: RealmObject {

        companion object{
                val idProperty = "id";
                val usernameProperty = "username";
                val emailProperty = "email";
                val phoneProperty = "phone";
                val websiteProperty = "website";
        }
        @PrimaryKey
        var id: Int = 0
        @Required
        var username: String = ""
        @Required
        var email: String = ""
        @Required
        var phone: String = ""
        @Required
        var website: String = ""


        constructor() : super()
        constructor(id: Int, username: String, email: String, phone: String, website: String) : super() {
                this.id = id
                this.username = username
                this.email = email
                this.phone = phone
                this.website = website
        }

        constructor(json: JsonObject) : super() {
                this.id = json.get(idProperty).asInt
                this.username = json.get(usernameProperty).asString
                this.email = json.get(emailProperty).asString
                this.phone = json.get(phoneProperty).asString
                this.website = json.get(websiteProperty).asString
        }


}