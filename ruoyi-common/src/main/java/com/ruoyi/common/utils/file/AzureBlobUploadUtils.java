package com.ruoyi.common.utils.file;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.models.BlobStorageException;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.exception.file.FileNameLengthLimitExceededException;
import com.ruoyi.common.exception.file.FileSizeLimitExceededException;
import com.ruoyi.common.exception.file.InvalidExtensionException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.Seq;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

public class AzureBlobUploadUtils {
    private static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024L;
    private static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 获取 Blob 容器客户端
     */
    private static BlobContainerClient getBlobContainerClient() {
        String connectionString = RuoYiConfig.getAzureConnectionString();
        String containerName = RuoYiConfig.getAzureContainerName();
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
        return blobServiceClient.getBlobContainerClient(containerName);
    }

    /**
     * 上传文件到 Azure Blob Storage
     *
     * @param file 上传的文件
     * @return 文件的 URL
     * @throws IOException
     */
    public static String upload(MultipartFile file) throws IOException {
        try {
            return upload(file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION, "");
        } catch (InvalidExtensionException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 上传文件到指定文件夹的 Azure Blob Storage
     *
     * @param file   上传的文件
     * @param folder 文件夹路径
     * @return 文件的 URL
     * @throws IOException
     */
    public static String upload(MultipartFile file, String folder) throws IOException {
        try {
            return upload(file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION, folder);
        } catch (InvalidExtensionException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 上传文件到指定文件夹的 Azure Blob Storage
     *
     * @param file             上传的文件
     * @param allowedExtension 允许的文件扩展名
     * @param folder           文件夹路径
     * @return 文件的 URL
     * @throws IOException
     * @throws FileSizeLimitExceededException
     * @throws FileNameLengthLimitExceededException
     * @throws InvalidExtensionException
     */
    public static String upload(MultipartFile file, String[] allowedExtension, String folder) throws IOException, FileSizeLimitExceededException, FileNameLengthLimitExceededException, InvalidExtensionException {
        int fileNamelength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (fileNamelength > DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(DEFAULT_FILE_NAME_LENGTH);
        }

        assertAllowed(file, allowedExtension);

        String fileName = extractFilename(file, folder);
        BlobContainerClient blobContainerClient = getBlobContainerClient();
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);

        try {
            blobClient.upload(file.getInputStream(), file.getSize(), true);
            BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(file.getContentType());
            blobClient.setHttpHeaders(headers);
        } catch (BlobStorageException e) {
            throw new IOException("Failed to upload file to Azure Blob Storage", e);
        }

        return fileName;
    }

    /**
     * 提取文件名
     *
     * @param file   上传的文件
     * @param folder 文件夹路径
     * @return 文件名
     */
    private static String extractFilename(MultipartFile file, String folder) {
        String baseName = FilenameUtils.getBaseName(file.getOriginalFilename());
        String extension = getExtension(file);
        String datePath = DateUtils.datePath();
        String uniqueId = Seq.getId(Seq.uploadSeqType);
        return StringUtils.format("{}/{}/{}_{}.{}", folder, datePath, baseName, uniqueId, extension);
    }

    /**
     * 校验文件是否允许上传
     *
     * @param file             上传的文件
     * @param allowedExtension 允许的文件扩展名
     * @throws FileSizeLimitExceededException
     * @throws InvalidExtensionException
     */
    private static void assertAllowed(MultipartFile file, String[] allowedExtension) throws FileSizeLimitExceededException, InvalidExtensionException {
        long size = file.getSize();
        if (size > DEFAULT_MAX_SIZE) {
            throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
        }

        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
            if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION) {
                throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension, fileName);
            } else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION) {
                throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension, fileName);
            } else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION) {
                throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension, fileName);
            } else if (allowedExtension == MimeTypeUtils.VIDEO_EXTENSION) {
                throw new InvalidExtensionException.InvalidVideoExtensionException(allowedExtension, extension, fileName);
            } else {
                throw new InvalidExtensionException(allowedExtension, extension, fileName);
            }
        }
    }

    /**
     * 判断文件扩展名是否允许
     *
     * @param extension        文件扩展名
     * @param allowedExtension 允许的文件扩展名
     * @return 是否允许
     */
    private static boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件扩展名
     *
     * @param file 上传的文件
     * @return 文件扩展名
     */
    private static String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        return extension;
    }
}
