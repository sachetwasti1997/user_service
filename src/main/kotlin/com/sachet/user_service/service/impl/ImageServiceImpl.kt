package com.sachet.user_service.service.impl

import com.sachet.user_service.service.contract.ImageService
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileInputStream
import kotlin.io.path.Path

@Service
class ImageServiceImpl : ImageService {

    private val path = "./src/main/resources/uploads/"

    override suspend fun uploadImage(filePart: FilePart, userId: String) {
        val basePath = "$path$userId.png"
        checkFileExists(basePath)
        filePart.transferTo(Path(basePath)).awaitSingleOrNull()
    }

    private fun checkFileExists(basePath: String){
        val file = File(basePath)
        if (file.exists())file.delete()
    }

    override fun getImage(images: String): ByteArray? {
        val path = "$path$images"
        val file = File(path)
        println(file.exists())
        if (file.exists()){
            val b = ByteArray(file.length().toInt())
            val inStrm = FileInputStream(file)
            inStrm.read(b)
            return b
        }
        return null
    }
}