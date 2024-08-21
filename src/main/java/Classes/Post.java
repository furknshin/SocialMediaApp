package Classes;

import java.io.*;
import java.util.ArrayList;

public class Post
{
    public User publisher; //Gönderiyi paylaşan User
    String text; //Gönderi metni
    ArrayList<String> observers; //User'ın arkadaşları observerlardır

    public Post(User publisher, String text)
    {
        this.publisher = publisher;
        this.text = text;
        this.observers = publisher.getFriends();
    }

    public void shareThePost()
    {
        //KUllanıcı paylaşım yapar ve Posts.txt güncellenir
        String dosya = "Posts.txt";
        String dosya2 = "PostsTemp.txt";

        try {
            BufferedReader reader1 = new BufferedReader(new FileReader(dosya));
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(dosya2));

            String satir;

            while ((satir = reader1.readLine()) != null) {

                String[] infos = satir.split("/");


                if (infos[0].equals(publisher.getUserName()))
                {
                    if(infos[1].equals("null"))
                    {
                        String line = String.format("%s/%s/%s\n", infos[0], text, infos[2]);
                        writer1.write(line);
                    }
                    else
                    {
                        String line = String.format("%s/%s,%s/%s\n", infos[0], infos[1], text, infos[2]);
                        writer1.write(line);
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

        } catch (IOException e) {
            System.out.println("Dosya okuma veya yazma hatası: " + e.getMessage());
        }
    }

    public void notifyObservers()
    {
        for (String friend : observers)
        {
        Observerr observer = new Observerr(friend);
        observer.update(text);
        }
    }
}
