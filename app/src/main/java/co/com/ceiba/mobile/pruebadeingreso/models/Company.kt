package co.com.ceiba.mobile.pruebadeingreso.models

class Company {
    var name: String = ""
    var catchPhrase: String = ""
    var bs: String = ""

    constructor(name: String, catchPhrase: String, bs: String) {
        this.name = name
        this.catchPhrase = catchPhrase
        this.bs = bs
    }

    constructor()
}