 package com.global.shop.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.global.shop.error.FileStorageException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileUploadService {

	private Path fileStorageLocation;

//	@Value("${file.upload.base-path}")
	private final String basePath = "D:\\Global\\book\\";


	public String storeFile(File file, Long id, String pathType) throws FileStorageException {

		// create uploaded path
		this.fileStorageLocation = Paths.get(basePath + pathType).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}

		String fileName = StringUtils.cleanPath(id + "-" + file.getName());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			InputStream targetStream = new FileInputStream(file);
			Files.copy(targetStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);

			

			return fileName;
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}




	/**
	 * 
	 * @param multipartFile
	 * @return
	 */
	public File convertMultiPartFileToFile(final MultipartFile multipartFile) {
		final File file = new File(multipartFile.getOriginalFilename());
		try (final FileOutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(multipartFile.getBytes());
		} catch (final IOException ex) {
			log.error("Error converting the multi-part file to file= ", ex.getMessage());
		}
		return file;
	}

	}