package co.com.ceiba.mobile.pruebadeingreso.models

class User {
    var id: Int = 0
    var username: String = ""
    var email: String = ""
    var address: Address? = null
    var phone: String = ""
    var website: String = ""
    var company: Company? = null



    constructor(id: Int, username: String, email: String, address: Address?, phone: String, website: String, company: Company?) {
        this.id = id
        this.username = username
        this.email = email
        this.address = address
        this.phone = phone
        this.website = website
        this.company = company
    }

    constructor()
}