package com.example.aoopproject;

import Classes.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class RegisterPageController {

    @FXML
    private ToggleGroup Privacy;
    @FXML
    private Button btn_register_to_login;
    @FXML
    private Button btn_register;
    @FXML
    private RadioButton rb_private;
    @FXML
    private RadioButton rb_public;
    @FXML
    private TextField txt_name;
    @FXML
    private TextField txt_password;
    @FXML
    private TextField txt_surname;
    @FXML
    private TextField txt_username;
    @FXML
    private Label lbl_acc_created;
    @FXML
    private Label lbl_username_used;


    @FXML
    void rb_click_privacy(ActionEvent event) {

    }
    @FXML
    void btn_click_register(ActionEvent event) {

        // Proxy sınıfımız aynı kullanıcı adına sahip bir kullanıcı oluşturulmasını engeller

        UserServiceProxy usp = new UserServiceProxy();

        for(String str : usernamesInFile()){
            usp.userService.addUser(str);
        }

        usp.addUser(txt_username.getText());

        if(usp.control)
        {
            lbl_username_used.setText("Already username is used!");
        }
        else
        {
            if(rb_private.isSelected())
            {
                UserFactory factory = new PrivateUserFactory();
                User user = factory.createUser(txt_name.getText(),txt_surname.getText(),txt_username.getText(),txt_password.getText());
                writeToFile(user);
                writeToFilePosts(user);


            } else if (rb_public.isSelected())
            {
                UserFactory factory = new PublicUserFactory();
                User user = factory.createUser(txt_name.getText(),txt_surname.getText(),txt_username.getText(),txt_password.getText());
                writeToFile(user);
                writeToFilePosts(user);
            }
        }
    }
    @FXML
    void btn_click_register_to_login(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(AoopApplication.class.getResource("LoginPageFxml.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();

    }
    void writeToFile(User user) {
        // Bu metot yeni oluşturulan bir kullanıcıyı users.txt 'ye ekler

        String filePath = "Users.txt";
        boolean control = true;

        // Aynı kullanıcı adından başka kişi eklememek için
        try (BufferedReader reader = new BufferedReader((new FileReader(filePath)))) {

            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] info = line.split(" ");

                if(Objects.equals(info[2], user.getUserName()))
                {
                    control = false;
                }

            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }


        if(control)
        {

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {


                String privacy;
                if(user instanceof PrivateUser){
                    privacy = "Private";
                }
                else {
                    privacy = "Public";
                }

                String line = String.format("%s %s %s %s %s null\n", user.getName(), user.getSurname(), user.getUserName(), user.getPassword(), privacy);
                writer.write(line);

            } catch (IOException e) {
                System.out.println("Dosya yazma hatası: " + e.getMessage());
            }

        }
    }
    void writeToFilePosts(User user) {
        // Bu metot yeni eklenen kullanıcıyı posts.txt'ye kaydeder

        String filePath = "Posts.txt";
        boolean control = true;

        // Aynı kullanıcı adından başka kişi eklememek için
        try (BufferedReader reader = new BufferedReader((new FileReader(filePath)))) {

            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] info = line.split("/");

                if(Objects.equals(info[0], user.getUserName()))
                {
                    control = false;
                }

            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }


        if(control)
        {

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true)))
            {

                String line = String.format("%s/null/null\n", user.getUserName());
                writer.write(line);

            } catch (IOException e) {
                System.out.println("Dosya yazma hatası: " + e.getMessage());
            }

            lbl_acc_created.setText("Account has been created!");

        }
    }
    ArrayList<String> usernamesInFile(){

        // Bu metot users.txt deki kullanıcı adlarının bir listesini döndürür

        String filePath = "Users.txt";
        ArrayList<String> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader((new FileReader(filePath)))) {

            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] info = line.split(" ");
                list.add(info[2]);

            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return list;
    }
}
