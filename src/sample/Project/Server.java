package sample.Project;

import java.io.*;
import java.net.*;
import java.util.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Server extends Application {

    private int clientNo = 0;
    private TextArea TA = new TextArea();

    @Override
    public void start (Stage primaryStage) throws Exception {
        TA.setEditable(false);

        Scene scene = new Scene(new ScrollPane(TA), 450, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Server");
        primaryStage.show();

        new Thread( () -> {
            try {
                ServerSocket serverSocket = new ServerSocket(7575);

                Platform.runLater( () -> {
                    TA.appendText("Server started time " + new Date()+ '\n');
                    TA.appendText("_________________________________________________" + '\n');
                });

                while (true) {
                    Socket socket = serverSocket.accept();
                    clientNo++;

                    Platform.runLater( () -> {
                        TA.appendText(clientNo + " Starting client " +  " at " + new Date() + '\n');
                        TA.appendText("Client " + " IP Address is " + socket.getInetAddress().getHostAddress() + '\n'+'\n');

                    });

                    new Thread(new ThreadClient(socket)).start();
                }
            } catch (Exception e) {
                TA.appendText(e.toString() + '\n');
            }
        }).start();
    }

    class ThreadClient implements Runnable {

        private Socket socket;

        public ThreadClient(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run () {
            try {
                DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
              //  DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

                while (true) {
                    String normal = inputFromClient.readUTF();
                    Platform.runLater( () -> {
                        TA.appendText("Message received from client: " + normal + '\n');

                    });
                }
            } catch (Exception e) {
                TA.appendText(e.toString() + '\n');
            }
        }
    }
    public static void main (String[] args) {
        Application.launch(args);
    }
}