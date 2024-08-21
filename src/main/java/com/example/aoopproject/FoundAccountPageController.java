package com.example.aoopproject;

import Classes.PrivateUser;
import Classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.*;

public class FoundAccountPageController {

    User user;
    User sUser;

    @FXML
    private Button btn_back;
    @FXML
    private Button btn_add_friend;
    @FXML
    private Label txt_acc_status;
    @FXML
    private Label lbl_found_acc_name_surname;
    @FXML
    private Label lbl_found_acc_username;
    @FXML
    private TextArea txt_area_searched_hpage;


    public void createSearchedAccountPage(User sUser) {

        // Account page'de aranan kullanıcı adına göre aranan user ve sayfası oluşturulur

        this.sUser = sUser;

        lbl_found_acc_username.setText(sUser.getUserName());
        lbl_found_acc_name_surname.setText(String.format("%s %s", sUser.getName(), sUser.getSurname()));

        String hpost = homePageInSearchedAccount(sUser);
        txt_area_searched_hpage.setText(hpost);

    }
    public void mainUser(User user){

        // Account page'nin sahibi olan user'ın bilgileri foundaccountpage'ye bu metot ile aktarılır ve
        // aranılan hesap gizli bir hesapsa bilgileri gizlenir

        this.user = user;


        if((sUser instanceof PrivateUser) && !(user.getFriends().contains(sUser.getUserName()))){
            txt_acc_status.setText("This account is private!");
            txt_area_searched_hpage.setText("Private account!");
            btn_add_friend.setDisable(true);
        }
    }
    @FXML
    void btn_click_back(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(AoopApplication.class.getResource("AccountPageFxml.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Account Page");

        AccountPageController controller = fxmlLoader.getController();
        controller.createAccountPage(user);

        stage.setScene(scene);
        stage.show();

    }
    @FXML
    void btn_click_add_friend(ActionEvent event) {

        // add friend butonuna tıklanıldığında bu metot çalışır ve ekleyen hesap ve eklenilen hesaptaki veriler
        // txt dosyalarında düzenlenip paylaşılır

        String dosya = "Users.txt";
        String dosya2 = "UsersTemp.txt";
        String dosya3 = "Posts.txt";
        String dosya4 = "PostsTemp.txt";

        String userPosts = "";
        String sUserPosts = "";


        try {

            BufferedReader reader1 = new BufferedReader(new FileReader(dosya));
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(dosya2));

            String satir;

            while ((satir = reader1.readLine()) != null) {

                String[] infos = satir.split(" ");


                if (infos[2].equals(user.getUserName()))
                {
                    if(infos[5].equals("null"))
                    {
                        String line = String.format("%s %s %s %s %s %s\n", infos[0], infos[1], infos[2],infos[3],infos[4],sUser.getUserName());
                        writer1.write(line);
                        user.getFriends().add(sUser.getUserName());
                    }
                    else
                    {
                        String line = String.format("%s %s %s %s %s %s,%s\n", infos[0], infos[1], infos[2],infos[3],infos[4],infos[5], sUser.getUserName());
                        writer1.write(line);
                        user.getFriends().add(sUser.getUserName());

                    }

                }
                else if (infos[2].equals(sUser.getUserName()))
                {
                    if(infos[5].equals("null"))
                    {
                        String line = String.format("%s %s %s %s %s %s\n", infos[0], infos[1], infos[2],infos[3],infos[4],user.getUserName());
                        writer1.write(line);
                        sUser.getFriends().add(user.getUserName());
                    }
                    else
                    {
                        String line = String.format("%s %s %s %s %s %s,%s\n", infos[0], infos[1], infos[2],infos[3],infos[4],infos[5], user.getUserName());
                        writer1.write(line);
                        sUser.getFriends().add(user.getUserName());
                    }
                }

                else
                {
                    String line = String.format("%s\n", satir);
                    writer1.write(line);
                }

            }

            reader1.close();
            writer1.close();

            File kaynakDosya = new File(dosya2);
            File hedefDosya = new File(dosya);

            FileInputStream fis = new FileInputStream(kaynakDosya);
            FileOutputStream fos = new FileOutputStream(hedefDosya);

            int byteOkunan;
            while ((byteOkunan = fis.read()) != -1) {
                fos.write(byteOkunan);
            }

            fis.close();
            fos.close();

        }
        catch (IOException e)
        {
            System.out.println("Dosya okuma veya yazma hatası: " + e.getMessage());
        }


        try(BufferedReader reader = new BufferedReader(new FileReader(dosya3))) {

            String line;


            while ((line = reader.readLine()) != null) {

                String[] infos = line.split("/");

                if (infos[0].equals(user.getUserName()))
                {

                    userPosts = infos[1];

                }
                if (infos[0].equals(sUser.getUserName()))
                {
                    sUserPosts = infos[1];
                }

            }

        } catch (IOException e) {
            System.out.println("Dosya okuma veya yazma hatası: " + e.getMessage());
        }

        try {
            BufferedReader reader2 = new BufferedReader(new FileReader(dosya3));
            BufferedWriter writer2 = new BufferedWriter(new FileWriter(dosya4));

            String line1;

            while ((line1 = reader2.readLine()) != null) {

                String[] infos = line1.split("/");


                if (infos[0].equals(user.getUserName()))
                {
                    if(infos[2].equals("null")){

                        String line = String.format("%s/%s/%s\n", infos[0], infos[1], sUserPosts);
                        writer2.write(line);
                    }
                    else
                    {
                        if(sUserPosts.equals("null"))
                        {
                            String line = String.format("%s/%s/%s\n", infos[0], infos[1], infos[2]);
                            writer2.write(line);
                        }
                        else
                        {
                            String line = String.format("%s/%s/%s,%s\n", infos[0], infos[1], infos[2], sUserPosts);
                            writer2.write(line);
                        }

                    }


                }
                else if (infos[0].equals(sUser.getUserName()))
                {
                    if(infos[2].equals("null")){

                        String line = String.format("%s/%s/%s\n", infos[0], infos[1], userPosts);
                        writer2.write(line);
                    }
                    else
                    {
                        if(userPosts.equals("null"))
                        {
                            String line = String.format("%s/%s/%s\n", infos[0], infos[1], infos[2]);
                            writer2.write(line);
                        }
                        else
                        {
                            String line = String.format("%s/%s/%s,%s\n", infos[0], infos[1], infos[2], userPosts);
                            writer2.write(line);
                        }
                    }


                }

                else
                {
                    String line = String.format("%s\n", line1);
                    writer2.write(line);
                }

            }

            reader2.close();
            writer2.close();

            File kaynakDosya2 = new File(dosya4);
            File hedefDosya2 = new File(dosya3);

            FileInputStream fis1 = new FileInputStream(kaynakDosya2);
            FileOutputStream fos1 = new FileOutputStream(hedefDosya2);

            int byteOkunan;
            while ((byteOkunan = fis1.read()) != -1) {
                fos1.write(byteOkunan);
            }

            fis1.close();
            fos1.close();

        }
        catch (IOException e)
        {
            System.out.println("Dosya okuma veya yazma hatası: " + e.getMessage());
        }



    }
    public String homePageInSearchedAccount(User user) {

        // Aranılan hesabın paylaşımları olan string bu metotta döndürülür

        String dosya = "Posts.txt";
        String[] posts = null;
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
}
