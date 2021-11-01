package co.com.ceiba.mobile.pruebadeingreso.models
import com.google.gson.JsonObject
import io.realm.annotations.PrimaryKey
import io.realm.RealmObject;
import io.realm.annotations.Required


open class User: RealmObject {

        companion object{
                val idProperty = "id";
                val nameProperty = "name";
                val emailProperty = "email";
                val phoneProperty = "phone";
        }
        @PrimaryKey
        var id: Int = 0
        @Required
        var name: String = ""
        @Required
        var email: String = ""
        @Required
        var phone: String = ""


        constructor() : super()

        constructor(json: JsonObject) : super() {
                this.id = json.get(idProperty).asInt
                this.name = json.get(nameProperty).asString
                this.email = json.get(emailProperty).asString
                this.phone = json.get(phoneProperty).asString
        }


}