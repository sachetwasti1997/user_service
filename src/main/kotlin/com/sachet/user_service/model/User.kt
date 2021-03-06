package com.sachet.user_service.model

import org.springframework.data.annotation.Id
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class User(
    @Id
    val userId: String ?= null,
    @field:NotNull(message="Please Enter a valid first name")
    @field:NotEmpty(message="Please Enter a valid first name")
    val firstName: String ?= null,
    @field:NotNull(message="Please enter a valid last name")
    @field:NotEmpty(message="Please enter a valid last name")
    val lastName: String ?= null,
    @field:NotNull(message="Please enter a valid email")
    @field:NotEmpty(message="Please enter a valid email")
    val email: String ?= null,
    @field:NotNull(message = "Password cannot be null!")
    @field:Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,255}$",
        message = "Password must contain a small letter, a capital letter and a special character,"
                + " and must be 8 characters long and not more than 255 characters!"
    )
    var password: String ?= null,
    @field:NotNull(message = "User Name cannot be null!")
    @field:NotEmpty(message = "User Name cannot be null!")
    @field:Size(min = 4, max = 255, message = "User Name must be 4 characters long and not more than 255 characters!")
    val userName: String ?= null,
    val imageLocation: String ?= null,
    val roles: List<String> ?= null
)
