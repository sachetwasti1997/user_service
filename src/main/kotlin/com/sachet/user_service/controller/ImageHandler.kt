package com.sachet.user_service.controller

import com.sachet.user_service.service.contract.ImageService
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import javax.imageio.ImageIO

@RestController
@RequestMapping("/images")
class ImageHandler(
    private val imageService: ImageService
) {
    @GetMapping("/get/{images}")
    fun getImage(@PathVariable images: String) = ResponseEntity.ok().body(imageService.getImage(images))

    @PutMapping("/upload/{id}")
    suspend fun uploadProfileImage(@PathVariable id: String, @RequestPart("user_image")file: Mono<FilePart>){
        val userImage = file.awaitSingleOrNull()
        userImage?.let {
            imageService.uploadImage(it, id)
        }
    }
}