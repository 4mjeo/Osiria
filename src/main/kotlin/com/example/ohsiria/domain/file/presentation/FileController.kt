package com.example.ohsiria.domain.file.presentation

import com.example.ohsiria.domain.file.service.UploadFileService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/file")
class FileController(
    private val uploadFileService: UploadFileService,
) {
    @PostMapping("/upload")
    fun uploadFile(
        @RequestPart("files", required = true) files: List<MultipartFile>,
        @RequestParam("roomNo", required = true) roomNo: String,
    ) = uploadFileService.uploadFiles(files, roomNo)
}
