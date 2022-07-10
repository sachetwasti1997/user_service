package com.sachet.user_service.model

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class LogInRequest(
    @field:NotNull(message = "User Name cannot be null")
    @field:NotEmpty(message = "Please Enter a valid User Name")
    val userName: String ?= null,
    @field:NotNull(message = "Password cannot be null!")
    @field:Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,255}$",
        message = "Password must contain a small letter, a capital letter and a special character,"
                + " and must be 8 characters long and not more than 255 characters!"
    )
    val password: String ?= null
)
