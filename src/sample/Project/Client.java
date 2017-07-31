package sample.Project;

import java.io.*;
import java.net.*;
import java.util.*;

import javafx.application.Application;
import javafx.stage.Stage;

public class Client extends Application {

    DataOutputStream toServer = null;
    DataInputStream fromServer = null;

    public void start (Stage primaryStage) throws Exception {
        try {
            Socket socket = new Socket("localhost", 7575);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
            System.out.println("Client is started " +  " at  "+ new Date() + '\n');
        } catch (Exception e) {
        }
    }

}