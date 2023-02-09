package com.example.artizen.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploadService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String dirName, String titleName) throws IOException {

        File uploadFile = convert(multipartFile).orElseThrow(() -> new IllegalArgumentException("파일전환 실패"));
        return upload(uploadFile, dirName, titleName);
    }

    private String upload(File uploadFile, String dirName, String titleName) {

        String beforeFileName = dirName + "/" + titleName;
        String fileName = filenameReplaceAll(beforeFileName);
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return "https://artizen-image.s3.ap-northeast-2.amazonaws.com/" + fileName;
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    private String filenameReplaceAll(String beforeFileName){
        String match = "[^\uAC00-\uD7A30-9a-zA-Z]";
        beforeFileName = beforeFileName.replaceAll(match, "");
        String fileName = beforeFileName.contains(" ")? beforeFileName.trim() : beforeFileName;
        return fileName;
    }
}