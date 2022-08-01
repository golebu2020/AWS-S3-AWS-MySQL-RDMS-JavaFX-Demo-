package com.cloud.awsmanage;


import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

import java.util.List;

public class AWS3Bucket {
    public static final String AWS_ACCESS_KEY = System.getenv("ACCESS_KEY_ID");
    public static final String AWS_SECERT_KEY = System.getenv("SECRET_ACCESS_KEY");
    private static final AwsCredentials credentials = AwsBasicCredentials.create(AWS_ACCESS_KEY, AWS_SECERT_KEY);
    private static final S3Client s3Client = S3Client.builder().region(Region.of("us-east-1")).credentialsProvider(
            StaticCredentialsProvider.create(credentials)
    ).build();

    private static final CreateBucketRequest bucketRequest = CreateBucketRequest.builder().bucket("crayon-carbon-aws").build();
    public static void create(){

        try {
            s3Client.createBucket(bucketRequest);
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        System.out.println("Bucket Created");
    }

    public static void listBuckets(){
        ListBucketsResponse buckets = s3Client.listBuckets();
        System.out.println("Your {S3} buckets are:");
        List<Bucket> buckets1 = buckets.buckets();
        for(Bucket b: buckets1){
            System.out.println(b.name());
        }
    }
}
