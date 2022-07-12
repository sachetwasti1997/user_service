package com.sachet.user_service.service.contract

import org.springframework.http.codec.multipart.FilePart

interface ImageService {
    suspend fun uploadImage(filePart: FilePart, userId: String)

}