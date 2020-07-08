package org.code.runs.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class SimpleStorageServiceTest {

    private final String ACCESS_KEY = "***";
    private final String SECRET_KEY = "***";
    private final AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
    private final AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
    private final SimpleStorageService s3 = new SimpleStorageService(credentialsProvider);


    @Test
    public void testPrintBucketList() {
        s3.printBucketList();
    }

    @Test
    public void testPrintObjectList() {
        s3.printObjectsInTheBucket();
    }

    @Test
    public void testDownloadObject() {
        s3.downloadObject();
    }

    @Test
    public void testUploadObject() {
        s3.uploadObject("/Users/varunshr/Documents/Docs/blah.docx");
    }

}