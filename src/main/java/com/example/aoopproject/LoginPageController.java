package com.example.aoopproject;

import Classes.PrivateUser;
import Classes.PublicUser;
import Classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class LoginPageController
{
    @FXML
    private Button btn_login;
    @FXML
    private Button btn_login_to_register;
    @FXML
    private TextField txt_password;
    @FXML
    private TextField txt_username;
    @FXML
    private Label lbl_login_error;


    @FXML
    public void btn_click_login_to_register(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(AoopApplication.class.getResource("RegisterPageFxml.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Register Page");
        stage.setScene(scene);
        stage.show();



    }
    @FXML
    void btn_click_login(ActionEvent event) throws IOException {

        String filePath = "Users.txt";

        boolean control = false;
        
        User user = null;

        // Users.txt den girilen kullanıcı adına göre gerekli veriler çekilir ve user oluşturulur
        try (BufferedReader reader = new BufferedReader((new FileReader(filePath)))) {

            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] info = line.split(" ");

                if(info[2].equals(txt_username.getText()) && info[3].equals(txt_password.getText()))
                {
                    control = true;

                    String[] friendList = {};

                    if(!(info[5].equals("null"))){
                        friendList = info[5].split(",");
                    }

                    ArrayList<String> friends = new ArrayList<>(Arrays.asList(friendList));
                    
                    if(info[4].equals("Private"))
                    {
                        user = new PrivateUser(info[0],info[1],info[2],info[3]);
                        user.setFriends(friends);
                    }
                    else
                    {
                        user = new PublicUser(info[0],info[1],info[2],info[3]);
                        user.setFriends(friends);
                    }
                }

            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }


        // Eğer kullanıcı adı dosyada varsa ve şifre doğruysa bu if bloğu çalışır aksi takdirde else'e düşer
        if(control)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(AoopApplication.class.getResource("AccountPageFxml.fxml"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle("Account Page");

            AccountPageController controller = fxmlLoader.getController();
            controller.createAccountPage(user);

            stage.setScene(scene);
            stage.show();
        }
        else
        {
            lbl_login_error.setText("Check the username or password!");
        }


    }

}