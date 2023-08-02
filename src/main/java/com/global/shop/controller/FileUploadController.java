package com.global.shop.controller;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.global.shop.error.FileStorageException;
import com.global.shop.service.FileUploadService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileUploadController {
	
	private final FileUploadService fileUploadService;
	
	@Operation(summary = "upload any file ")
	@PostMapping("/upload")
	public ResponseEntity<Object> uploadFile (@RequestParam Long id,@RequestParam String pathType
			,@RequestParam MultipartFile file) throws FileStorageException{
		
		String fileName = fileUploadService.storeFile(fileUploadService.convertMultiPartFileToFile(file), id, pathType);
		
		return ResponseEntity.ok(fileName);
	}
	
}