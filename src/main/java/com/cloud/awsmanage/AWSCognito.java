package com.cloud.awsmanage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.*;
import software.amazon.awssdk.regions.Region;

public class AWSCognito {
    public static final String AWS_ACCESS_KEY = System.getenv("ACCESS_KEY_ID");
    public static final String AWS_SECRET_KEY = System.getenv("SECRET_ACCESS_KEY");
    public static final String AWS_POOL_ID = "us-east-1_BLppFlzgu";

    private static AWSCognitoIdentityProvider getAmazonCognitoIdentityClient() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        AWSCredentialsProvider credProvider = new AWSStaticCredentialsProvider(basicAWSCredentials);
        return AWSCognitoIdentityProviderClientBuilder.standard()
                .withCredentials(credProvider)
                .withRegion(String.valueOf(Region.US_EAST_1))
                .build();
    }

    public static UserType signUp(){
        AWSCognitoIdentityProvider cognitoClient = getAmazonCognitoIdentityClient();
        AdminCreateUserRequest cognitoRequest = new AdminCreateUserRequest()
                .withUserPoolId(AWS_POOL_ID)
                .withUsername("golebu")
                .withUserAttributes(
                        new AttributeType()
                                .withName("email")
                                .withValue("cgolebu@gmail.com"),
                        new AttributeType()
                                .withName("name")
                                .withValue("Chinedu Olebu")
                       )
                .withTemporaryPassword("!j8fkxv2oTjLEMd")
                .withMessageAction("SUPPRESS")
                .withDesiredDeliveryMediums(DeliveryMediumType.EMAIL)
                .withForceAliasCreation(Boolean.FALSE);
        AdminCreateUserResult createUserResult =  cognitoClient.adminCreateUser(cognitoRequest);

        return createUserResult.getUser();
    }
}
