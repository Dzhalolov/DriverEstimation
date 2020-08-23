package ru.example.driverestimation.model

class User(
    val id: Long,
    var email: String,
    var name: String,
    var password: String,
    var car: String,
    var uri: String
) {
    override fun toString(): String {
        return "User" +
                "{" +
                "id= '$id', email= '$email', name= '$name'," +
                " password= '$password', car= '$car', uri= '$uri'" +
                "}"
    }

}
