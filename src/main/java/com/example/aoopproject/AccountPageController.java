package com.example.aoopproject;

import Classes.Post;
import Classes.PrivateUser;
import Classes.PublicUser;
import Classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class AccountPageController{

    private User user;

    @FXML
    private Button btn_share_post;
    @FXML
    private Button btn_logout;
    @FXML
    private Button btn_find_account;
    @FXML
    private TextArea txt_area_shared_post;
    @FXML
    private TextField txt_share_post;
    @FXML
    private Label lbl_name_surname;
    @FXML
    private Label lbl_username;
    @FXML
    private ListView<String> lv_friends_list;
    @FXML
    private TextField txt_find_account_name;
    @FXML
    private Label lbl_account_not_found;
    @FXML
    private TextArea txt_area_home_page;


    @FXML
    void btn_click_find_account(ActionEvent event) throws IOException {

        // Aranan kullanıcı adına göre bu kullanıcıyı bulur, önce nesnesini sonra da profil sayfasını oluşturur

        String filePath = "Users.txt";

        boolean control = false;

        User searchedUser = null;

        try (BufferedReader reader = new BufferedReader((new FileReader(filePath)))) {

            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] info = line.split(" ");

                if(info[2].equals(txt_find_account_name.getText()))
                {
                    control = true;

                    String[] friendList = {};

                    if(!(info[5].equals("null"))){
                        friendList = info[5].split(",");
                    }


                    ArrayList<String> friends = new ArrayList<>(Arrays.asList(friendList));

                    if(info[4].equals("Private"))
                    {
                        searchedUser = new PrivateUser(info[0],info[1],info[2],info[3]);
                        searchedUser.setFriends(friends);
                    }
                    else
                    {
                        searchedUser = new PublicUser(info[0],info[1],info[2],info[3]);
                        searchedUser.setFriends(friends);
                    }
                }

            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }



        if(control)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(AoopApplication.class.getResource("FoundAccountPageFxml.fxml"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle("Searched Account Page");

            FoundAccountPageController controller = fxmlLoader.getController();
            controller.createSearchedAccountPage(searchedUser);
            controller.mainUser(this.user);

            stage.setScene(scene);
            stage.show();
        }
        else
        {
            lbl_account_not_found.setText("Account not found!");
        }
    }
    @FXML
    void btn_click_logout(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(AoopApplication.class.getResource("LoginPageFxml.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void btn_click_share_post(ActionEvent event) {

        String postText = txt_share_post.getText();

        Post post = new Post(this.user, postText);
        post.notifyObservers();
        post.shareThePost();

        String spost = sharedPostsInAccount(this.user);
        txt_area_shared_post.setText(spost);

    }
    public void createAccountPage(User user) {

        // Bu metot ile login pagede oluşturulan user account page'ye taşınır

        this.user = user;

        lbl_username.setText(user.getUserName());
        lbl_name_surname.setText(String.format("%s %s", user.getName(), user.getSurname()));
        lv_friends_list.getItems().addAll(user.getFriends());

        String post = sharedPostsInAccount(this.user);
        txt_area_shared_post.setText(post);

        String hpost = homePageInAccount(this.user);
        txt_area_home_page.setText(hpost);
    }
    public String sharedPostsInAccount(User user) {

        // Bu metot account pagede gösterilecek olan postsda gösterilecek stringini döndürür

        String dosya = "Posts.txt";
        String[] posts = {};
        String line = "";

        try(BufferedReader reader = new BufferedReader(new FileReader(dosya))) {

            String satir;

            while ((satir = reader.readLine()) != null) {

                String[] infos = satir.split("/");

                if (infos[0].equals(user.getUserName()))
                {

                    if(!(infos[1].equals("null")))
                    {
                        posts = infos[1].split(",");

                        for(String e : posts)
                        {
                            line += e + "\n";
                        }
                    }
                }

            }

        } catch (IOException e) {
            System.out.println("Dosya okuma veya yazma hatası: " + e.getMessage());
        }

        return line;
    }
    public String homePageInAccount(User user) {

        // Bu metot account pagede gösterilecek olan home page'de gösterilecek stringini döndürür

        String dosya = "Posts.txt";
        String[] posts = {};
        String line = "";

        try(BufferedReader reader = new BufferedReader(new FileReader(dosya))) {

            String satir;

            while ((satir = reader.readLine()) != null) {

                String[] infos = satir.split("/");

                if (infos[0].equals(user.getUserName()))
                {

                    if(!(infos[2].equals("null")))
                    {
                        posts = infos[2].split(",");

                        for(String e : posts)
                        {
                            line += e + "\n";
                        }
                    }
                }

            }

        } catch (IOException e) {
            System.out.println("Dosya okuma veya yazma hatası: " + e.getMessage());
        }

        return line;
    }
}
