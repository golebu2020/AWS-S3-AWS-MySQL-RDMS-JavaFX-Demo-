package com.cloud.awsmanage;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.amazonaws.services.cognitoidp.model.UserType;
import com.amazonaws.services.cognitoidp.model.UsernameExistsException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Home extends Application {
    public static void main(String[] args) {
       launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox pane  = new VBox();
        Button btnAWS = new Button("Submit");
        Button btnDB = new Button("Create MySQLDatabase");
        Button connectDB = new Button("Connect to DB");
        Button createTable = new Button("Create Table");
        Button btnInsert = new Button("Insert Record");
        Button btnSelect = new Button("Select from Record");
        Button btnCreateClient = new Button("Create Cognito Client");
        Label labelStatus = new Label("Current Status:");
        btnCreateClient.setOnAction(e -> {
            Thread thread = new Thread(() -> {
                try {
                    UserType userType = AWSCognito.signUp();
                    System.out.println(userType.toString());
                }
                catch(UsernameExistsException ignored){
                    System.out.println("User Account already exists");
                    Platform.runLater(() -> labelStatus.setText("User Account already exists"));

                }
                catch(SdkClientException ignored){
                    Platform.runLater(() -> labelStatus.setText("Check your internet connection"));
                }
            });
            thread.start();
        });

        Label label = new Label("state");
        btnSelect.setOnAction(e -> {
            label.setText("Connecting");
            Thread taskThread = new Thread(() -> {
                MySQLDBHelper.selectRecord("STUDENTS");
                Platform.runLater(() -> label.setText("Done!"));
            });
            taskThread.start();
        });
        btnInsert.setOnAction(e -> MySQLDBHelper.insertRecord("STUDENTS"));
        connectDB.setOnAction(e -> MySQLDBHelper.selectDB("STUDENTS"));
        createTable.setOnAction(e -> MySQLDBHelper.createTable("STUDENTS"));

        btnDB.setOnAction(e -> {
            try {
                MySQLDBHelper.getConnection();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        });
        btnAWS.setOnAction(e -> {
            List<String> strings = AWS3Bucket.listBuckets();
            for(String string : strings){
                labelStatus.setText(labelStatus.getText()+"\n" + strings);
            }
        });
        pane.getChildren().addAll(btnAWS, btnDB, connectDB,
                createTable, btnInsert, btnSelect, new Button("Do nothing"), label, btnCreateClient,labelStatus);
        Scene scene = new Scene(pane,400,300);
        stage.setScene(scene);
        stage.show();
    }
}
