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
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.global.shop.entity.Customer;
import com.global.shop.error.FileStorageException;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.util.IOUtils;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileUploadService {

	private Path fileStorageLocation;

//	@Value("${file.upload.base-path}")
	private final String basePath = "F:\\Global\\brand\\";

	@Autowired
	private CustomerService customerService;

//	@Value("${google.storage.bucket-name}")
	private String googleBucketName = "";

//	@Value("${google.storage.project-id}")
	private String projectId = "";

//	@Value("${google.storage.credentials.path}")
	private String credentialPath = "";

//	endpointUrl: https://s3.us-east-2.amazonaws.com

//	@Value("${aws.s3.bucket}")
	private String awsBucketName;

//	private final AmazonS3 amazonS3;

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

			updateImagePath(id, pathType, pathType + "/" + fileName);

			return fileName;
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	/**
	 * 
	 * @param id
	 * @param pathType
	 * @param imagePath
	 */
	private void updateImagePath(Long id, String pathType, String imagePath) {

		if (pathType.contains("customers")) {
			// update author image path
			Customer customer = customerService.findById(id);
			customer.setImagePath(imagePath);
			customerService.updateImagePath(customer);

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

//	public String cloudUploadFile(MultipartFile file, Long id, String pathType) throws FileStorageException {
//
//		String fileName = null;
//
//		if (file.getContentType().contains("image")) {
//			fileName = id + "_" + UUID.randomUUID() + ".jpg";
//		} else {
//			fileName = id + file.getOriginalFilename();
//		}
//		String uniqueFileName = pathType + fileName;
//		try {
//
//			awsUploadObject(uniqueFileName, file);
//
//			updateImagePath(id, pathType, pathType + "/" + fileName);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new FileStorageException("Error converting the multi-part file to file= ", e);
//		}
//
//		return fileName;
//	}

	/**
	 * 
	 * @param uniqueFileName
	 * @param multipartFile
	 */
//	public void awsUploadObject(final String uniqueFileName, final MultipartFile multipartFile) {
//
//		log.info("Uploading file with name= " + uniqueFileName);
//
//		try {
//
//			ObjectMetadata meta = new ObjectMetadata();
//			meta.setContentLength(IOUtils.toByteArray(multipartFile.getInputStream()).length);
//
//			final PutObjectRequest putObjectRequest = new PutObjectRequest(awsBucketName, uniqueFileName,
//					multipartFile.getInputStream(), meta).withCannedAcl(CannedAccessControlList.PublicRead);
//
//			PutObjectResult result = amazonS3.putObject(putObjectRequest);
//			log.info("File uploaded successfully result" + result.toString());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	/**
	 * 
	 * @param fileUrl
	 */
//	public void awsDeleteObject(String fileUrl) {
//		final DeleteObjectRequest req = new DeleteObjectRequest(awsBucketName, fileUrl);
//		amazonS3.deleteObject(req);
//		log.info("File deleted from bucket " + awsBucketName + " as " + fileUrl);
//	}

	public void googelUploadObject(String projectId, String objectName, MultipartFile file) throws IOException {
		// The ID of your GCP project
		// String projectId = "your-project-id";

		// The ID of your GCS bucket
		// String bucketName = "your-unique-bucket-name";

		// The ID of your GCS object
		// String objectName = "your-object-name";

		// The path to your file to upload
		// String filePath = "path/to/your/file"
		InputStream inputStream = getClass().getResourceAsStream(this.credentialPath);
		Credentials credentials = GoogleCredentials.fromStream(inputStream);

		Storage storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId(projectId).build()
				.getService();
		BlobId blobId = BlobId.of(googleBucketName, objectName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();

//			    storage.create(blobInfo,Files.readAllBytes(Paths.get(filePath)));

		storage.create(blobInfo, file.getInputStream());
//			    storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

		log.info("File uploaded to bucket " + googleBucketName + " as " + objectName);

	}

	public void googleDeleteObject(String imagePath) throws IOException {
		// The ID of your GCP project
		// String projectId = "your-project-id";

		// The ID of your GCS bucket
		// String bucketName = "your-unique-bucket-name";

		// The ID of your GCS object
		// String objectName = "your-object-name";

		// The path to your file to upload
		// String filePath = "path/to/your/file"
		InputStream inputStream = getClass().getResourceAsStream(this.credentialPath);
		Credentials credentials = GoogleCredentials.fromStream(inputStream);

		Storage storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId(projectId).build()
				.getService();
		BlobId blobId = BlobId.of(googleBucketName, imagePath);
		storage.delete(blobId);

		log.info("File deleted from bucket " + googleBucketName + " as " + imagePath);

	}

}