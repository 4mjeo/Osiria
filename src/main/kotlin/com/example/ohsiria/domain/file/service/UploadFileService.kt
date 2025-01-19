package com.example.ohsiria.domain.file.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.util.IOUtils
import com.example.ohsiria.domain.file.entity.FileType
import com.example.ohsiria.domain.file.exception.InvalidFileExtensionException
import com.example.ohsiria.domain.file.presentation.dto.FileUrlListResponse
import com.example.ohsiria.domain.file.presentation.dto.FileUrlResponse
import com.example.ohsiria.domain.room.entity.RoomAttachment
import com.example.ohsiria.domain.room.exception.RoomNotFoundException
import com.example.ohsiria.domain.room.repository.RoomAttachmentRepository
import com.example.ohsiria.domain.room.repository.RoomRepository
import com.example.ohsiria.global.env.s3.S3Property
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream

@Service
class UploadFileService(
    private val s3Client: AmazonS3Client,
    private val s3Property: S3Property,
    private val roomRepository: RoomRepository,
    private val roomAttachmentRepository: RoomAttachmentRepository
) {
    companion object {
        const val URL_PREFIX = "https://%s.s3.%s.amazonaws.com/%s"
    }

    @Transactional
    fun uploadFiles(files: List<MultipartFile>, roomNo: Long): FileUrlListResponse {
        val room = roomRepository.findByRoomNo(roomNo) ?: throw RoomNotFoundException

        val uploadedFiles = files.map { uploadFile(it, roomNo.toString()) }

        uploadedFiles.forEach { fileResponse ->
            val roomAttachment = RoomAttachment(
                room = room,
                imageUrl = fileResponse.url
            )
            roomAttachmentRepository.save(roomAttachment)
        }

        return FileUrlListResponse(uploadedFiles.toMutableList())
    }

    private fun uploadFile(file: MultipartFile, roomNo: String): FileUrlResponse {
        val bytes: ByteArray = IOUtils.toByteArray(file.inputStream)
        val objectMetadata = ObjectMetadata().apply {
            this.contentType = file.contentType
            this.contentLength = bytes.size.toLong()
        }
        var fileName: String = file.originalFilename ?: file.name
        val ext = fileName.substringAfterLast('.', "").lowercase()
        if (ext.isEmpty() || FileType.values().none { it.extension == ext }) {
            throw InvalidFileExtensionException
        }

        fileName = "$roomNo/" + fileName

        val putObjectRequest = PutObjectRequest(
            s3Property.bucket,
            fileName,
            ByteArrayInputStream(bytes),
            objectMetadata,
        )

        s3Client.putObject(putObjectRequest)

        return FileUrlResponse(URL_PREFIX.format(
            s3Property.bucket,
            s3Property.region,
            fileName
        ))
    }
}
