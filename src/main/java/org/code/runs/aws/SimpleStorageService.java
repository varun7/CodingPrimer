package org.code.runs.aws;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SimpleStorageService {

    private AmazonS3 s3Client;
    private static final String BUCKET_NAME = "varunshr-test-bucket";

    public SimpleStorageService(AWSCredentialsProvider credentialsProvider) {
        s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_SOUTH_1).withCredentials(credentialsProvider).build();
    }

    public void uploadObject(String fileName) {
        File file = new File(fileName);
        String[] keys = fileName.split("/");
        PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, keys[keys.length-1], file);
        s3Client.putObject(request);
    }

    public void printBucketList() {
        s3Client.listBuckets().stream().forEach(b -> System.out.println(b.getName()));
    }

    public void printObjectsInTheBucket() {
        String continuationToken = null;
        ListObjectsV2Request request;
        boolean hasMoreKeys = true;

        while (hasMoreKeys) {
            System.out.println("Retrieving keys from S3...");
            request = new ListObjectsV2Request()
                    .withBucketName(BUCKET_NAME)
                    .withMaxKeys(2);

            if (continuationToken != null) {
                request.withContinuationToken(continuationToken);
            }

            ListObjectsV2Result result = s3Client.listObjectsV2(request);
            continuationToken = result.getNextContinuationToken();
            hasMoreKeys = result.isTruncated();

            if (result.getKeyCount() == 0) {
                return;
            }

            result.getObjectSummaries().forEach( o -> System.out.println("\t" + o.getKey()));
        }
        System.out.println("Done!");
    }

    public void downloadObject() {
        S3Object s3Object = s3Client.getObject(BUCKET_NAME, "Google Wallet.docx");
        S3ObjectInputStream s3InputStream = s3Object.getObjectContent();
        byte[] readingBuffer = new byte[1024];
        int readLength;
        try {
            FileOutputStream fos = new FileOutputStream(new File("Google Wallet Downloaded.docx"));
            while ((readLength = s3InputStream.read(readingBuffer)) > 0) {
                fos.write(readingBuffer, 0, readLength);
            }
            s3InputStream.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
