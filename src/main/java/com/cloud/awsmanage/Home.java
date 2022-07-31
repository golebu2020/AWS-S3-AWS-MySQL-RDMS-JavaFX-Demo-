package com.cloud.awsmanage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        Label label = new Label("state");
        btnSelect.setOnAction(e -> {
            MySQLDBHelper.selectRecord("STUDENTS");

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
        btnAWS.setOnAction(e -> CreateBucket.listBuckets());
        pane.getChildren().addAll(btnAWS, btnDB, connectDB,
                createTable, btnInsert, btnSelect, new Button("Do nothing"), label);
        Scene scene = new Scene(pane,400,300);
        stage.setScene(scene);
        stage.show();
    }

}
