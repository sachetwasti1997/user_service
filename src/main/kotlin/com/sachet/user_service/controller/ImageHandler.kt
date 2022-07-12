package com.sachet.user_service.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import javax.imageio.ImageIO

//@RestController
//@RequestMapping("/images")
//class ImageHandler {
//    @GetMapping("/get/{images}")
//    fun getImage(@PathVariable images: String): ResponseEntity<ByteArray>? {
//        val path = "./src/main/resources/uploads/$images"
//        val file = File(path)
//        if (file.exists()){
////            throw Exception()
//            val b = ByteArray(file.length().toInt())
//            val inStrm = FileInputStream(file)
//            inStrm.read(b)
//            return ResponseEntity.ok()
//                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .body(b)
//        }
//        return null
//    }
//}