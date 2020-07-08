package org.code.runs.aws;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public class Dynamo {

    public static final String KEY_INVOICE_ID = "InvoiceId";
    public static final String KEY_INVOICE_DATE = "InvoiceDate";
    public static final String KEY_INVOICE_DUE_DATE = "InvoiceDueDate";
    public static final String KEY_INVOICE_BALANCE = "InvoiceBalance";
    public static final String KEY_INVOICE_STATUS = "InvoiceStatus";
    public static final String KEY_BILL_ID = "BillId";
    public static final String KEY_BILL_AMOUNT = "BillAmount";
    public static final String KEY_BILL_BALANCE = "BillBalance";
    public static final String KEY_BILL_DUE_DATE = "BillDueDate";
    public static final String KEY_CUSTOMER_ID = "CustomerId";
    public static final String KEY_CUSTOMER_NAME = "CustomerName";
    public static final String KEY_CUSTOMER_STATE = "CustomerState";
    public static final String TABLE_NAME = "InvoiceandBilling";

    private AmazonDynamoDB dynamoDBClient;

    public Dynamo(AWSCredentialsProvider credentialsProvider) {
        dynamoDBClient = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.AP_SOUTH_1)
                .withCredentials(credentialsProvider).build();
    }

    public void printTableList() {
        System.out.println("Printing all tables:");
        ListTablesRequest request;
        ListTablesResult result;

        String lastTableName = null;
        while (true) {

            request = new ListTablesRequest().withLimit(2);

            if (lastTableName != null) {
                request.withExclusiveStartTableName(lastTableName);
            }

            result = dynamoDBClient.listTables(request);
            result.getTableNames().forEach(t -> System.out.println("\t" + t));

            if (result.getLastEvaluatedTableName() == null) {
                break;
            }
            lastTableName = result.getLastEvaluatedTableName();
        }
        System.out.println("Done!");
    }

    public void addItem(Map<String, String> input) {
        System.out.println("Adding an item to InvoiceBilling Table");

        Map<String, AttributeValue> invoiceItem = new HashMap<>();
        invoiceItem.put("PK", new AttributeValue().withS(input.get(KEY_INVOICE_ID)));
        invoiceItem.put("SK", new AttributeValue().withS("master"));
        invoiceItem.put("InvoiceDate", new AttributeValue().withS(input.get(KEY_INVOICE_DATE)));
        invoiceItem.put("InvoiceBalance", new AttributeValue().withS(input.get(KEY_INVOICE_BALANCE)));
        invoiceItem.put("InvoiceStatus", new AttributeValue().withS(input.get(KEY_INVOICE_STATUS)));
        invoiceItem.put("InvoiceDueDate", new AttributeValue().withS(input.get(KEY_INVOICE_DUE_DATE)));
        dynamoDBClient.putItem(TABLE_NAME, invoiceItem);

        Map<String, AttributeValue> invoiceCustomerItem = new HashMap<>();
        invoiceCustomerItem.put("PK", new AttributeValue().withS(input.get(KEY_INVOICE_ID)));
        invoiceCustomerItem.put("SK", new AttributeValue().withS(input.get(KEY_CUSTOMER_ID)));
        dynamoDBClient.putItem(TABLE_NAME, invoiceCustomerItem);

        Map<String, AttributeValue> billInvoiceItem = new HashMap<>();
        billInvoiceItem.put("PK", new AttributeValue().withS(input.get(KEY_INVOICE_ID)));
        billInvoiceItem.put("SK", new AttributeValue().withS(input.get(KEY_BILL_ID)));
        billInvoiceItem.put("BillAmount", new AttributeValue().withS(input.get(KEY_BILL_AMOUNT)));
        billInvoiceItem.put("BillBalance", new AttributeValue().withS(input.get(KEY_BILL_BALANCE)));
        dynamoDBClient.putItem(TABLE_NAME, billInvoiceItem);

        Map<String, AttributeValue> customerItem = new HashMap<>();
        customerItem.put("PK", new AttributeValue().withS(input.get(KEY_CUSTOMER_ID)));
        customerItem.put("SK", new AttributeValue().withS(input.get(KEY_CUSTOMER_ID)));
        customerItem.put("CustomerName", new AttributeValue().withS(input.get(KEY_CUSTOMER_NAME)));
        customerItem.put("CustomerState", new AttributeValue().withS(input.get(KEY_CUSTOMER_STATE)));
        dynamoDBClient.putItem(TABLE_NAME, customerItem);

        Map<String, AttributeValue> billItem = new HashMap<>();
        billItem.put("PK", new AttributeValue().withS(input.get(KEY_BILL_ID)));
        billItem.put("SK", new AttributeValue().withS(input.get(KEY_BILL_ID)));
        billItem.put("BillDueDate", new AttributeValue().withS(input.get(KEY_BILL_DUE_DATE)));
        billItem.put("BillDueAMount", new AttributeValue().withS(input.get(KEY_BILL_DUE_DATE)));
        dynamoDBClient.putItem(TABLE_NAME, billItem);
    }

    public Map<String, AttributeValue> getItem(String pValue, String sValue) {
        Map<String, AttributeValue> keyMap = ImmutableMap.of(
                "PK", new AttributeValue().withS(pValue),
                "SK", new AttributeValue().withS(sValue)
        );
        return dynamoDBClient.getItem(new GetItemRequest().withTableName(TABLE_NAME).withKey(keyMap)).getItem();
    }
}
