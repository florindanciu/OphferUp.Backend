package com.florindanciu.opherUpbackend.aws;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageStore {

    private final AmazonS3 s3;

    @Autowired
    public ImageStore(AmazonS3 s3) {
        this.s3 = s3;
    }

    public void save(String path,
                     String imageName,
                     Long fileLength,
                     InputStream inputStream) {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileLength);

        try {
            s3.putObject(path, imageName, inputStream, metadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to store image to AWS S3", e);
        }
    }

    public byte[] download(String path, String key) {
        try {
            S3Object object = s3.getObject(path, key);
            return IOUtils.toByteArray(object.getObjectContent());
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download image from AWS S3", e);
        }
    }

    public void deleteImage(String path, String key) {
        try {
            s3.deleteObject(path, key);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to delete image", e);
        }
    }
}
