package ru.example.driverestimation

class User(
    var id: Long,
    var login: String,
    var name: String,
    var password: String,
    var car: String,
    var uri: String
) {

    override fun toString(): String {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", car='" + car + '\'' +
                ", uri='" + uri + '\'' +
                '}'
    }

}