package com.sachet.user_service.controller

import com.sachet.user_service.service.contract.ImageService
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.security.Principal

@RestController
@RequestMapping("/images")
class ImageHandler(
    private val imageService: ImageService
) {
    @PutMapping("/upload/{userId}")
    suspend fun upload(@PathVariable userId: String,@RequestPart("user_image") filePart: Mono<FilePart>){
        val file = filePart.awaitSingle()
        return imageService.uploadImage(file, userId)
    }
}