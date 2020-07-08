package org.code.runs.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

@Ignore
public class DynamoTest {

    private final String ACCESS_KEY = "****";
    private final String SECRET_KEY = "*****";
    private final AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
    private final AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
    private final Dynamo dynamo = new Dynamo(credentialsProvider);

    private final String VALUE_INVOICE_ID = "I-12345";
    private final String VALUE_INVOICE_DATE = "3/28/29";
    private final String VALUE_INVOICE_DUE_DATE = "5/28/19";
    private final String VALUE_INVOICE_BALANCE = "$ 1000";
    private final String VALUE_INVOICE_STATUS = "Closed";
    private final String VALUE_CUSTOMER_ID = "C-12345";
    private final String VALUE_CUSTOMER_NAME = "Varun";
    private final String VALUE_CUSTOMER_STATE = "MD";
    private final String VALUE_BILL_ID = "B-12345";
    private final String VALUE_BILL_AMOUNT = "$10";
    private final String VALUE_BILL_BALANCE = "$20";
    private final String VALUE_BILL_DUE_DATE = "5/28/19";

    @Test
    public void testPrintTableList() {
        dynamo.printTableList();
    }

    @Test
    public void testInsertTable() {
        Map<String, String> input =  ImmutableMap.<String, String>builder()
                .put(Dynamo.KEY_INVOICE_ID, VALUE_INVOICE_ID)
                .put(Dynamo.KEY_INVOICE_DATE, VALUE_INVOICE_DATE)
                .put(Dynamo.KEY_INVOICE_DUE_DATE, VALUE_INVOICE_DUE_DATE)
                .put(Dynamo.KEY_INVOICE_BALANCE, VALUE_INVOICE_BALANCE)
                .put(Dynamo.KEY_INVOICE_STATUS, VALUE_INVOICE_STATUS)
                .put(Dynamo.KEY_BILL_ID, VALUE_BILL_ID)
                .put(Dynamo.KEY_BILL_AMOUNT, VALUE_BILL_AMOUNT)
                .put(Dynamo.KEY_BILL_BALANCE, VALUE_BILL_BALANCE)
                .put(Dynamo.KEY_BILL_DUE_DATE, VALUE_BILL_DUE_DATE)
                .put(Dynamo.KEY_CUSTOMER_ID, VALUE_CUSTOMER_ID)
                .put(Dynamo.KEY_CUSTOMER_NAME, VALUE_CUSTOMER_NAME)
                .put(Dynamo.KEY_CUSTOMER_STATE, VALUE_CUSTOMER_STATE)
                .build();
        dynamo.addItem(input);

        Assert.assertFalse(dynamo.getItem(VALUE_INVOICE_ID, "master").isEmpty());
        Assert.assertFalse(dynamo.getItem(VALUE_INVOICE_ID, VALUE_CUSTOMER_ID).isEmpty());
        Assert.assertFalse(dynamo.getItem(VALUE_INVOICE_ID, VALUE_BILL_ID).isEmpty());
        Assert.assertFalse(dynamo.getItem(VALUE_CUSTOMER_ID, VALUE_CUSTOMER_ID).isEmpty());
        Assert.assertFalse(dynamo.getItem(VALUE_BILL_ID, VALUE_BILL_ID).isEmpty());
    }

    @Test
    public void testGetItem() {
        Map<String, AttributeValue> item = dynamo.getItem(VALUE_INVOICE_ID, "master");
        item.entrySet().stream().forEach(e -> System.out.println("\t" + e.getKey() + " \t: " + e.getValue()));
    }
}
