package com.florindanciu.opherUpbackend.aws;

public enum BucketName {

    IMAGE("ophferup-image-upload-v1");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
