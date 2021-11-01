package co.com.ceiba.mobile.pruebadeingreso.models

import com.google.gson.JsonObject

class Post {
    var id: Int = 0
    var title: String = ""
    var body: String = ""

    constructor(json: JsonObject) : super() {
        this.id = json.get("id").asInt
        this.title = json.get("title").asString
        this.body = json.get("body").asString
    }
}