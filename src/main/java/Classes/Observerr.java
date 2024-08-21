package Classes;

import java.io.*;

public class Observerr implements Observer {
    String friendName;

    public Observerr(String friendName)
    {
        this.friendName = friendName;
    }
    @Override
    public void update(String text){

        //User gönderi paylaştığında arkadaşlarının homePage'ine gönderilmesini sağlayan metot
        String dosya = "Posts.txt";
        String dosya2 = "PostsTemp.txt";

        try {
            BufferedReader reader1 = new BufferedReader(new FileReader(dosya));
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(dosya2));

            String satir;

            while ((satir = reader1.readLine()) != null) {

                String[] infos = satir.split("/");


                if (infos[0].equals(friendName))
                {

                    if(infos[2].equals("null"))
                    {
                        String line = String.format("%s/%s/%s\n", infos[0], infos[1], text);
                        writer1.write(line);
                    }
                    else
                    {
                        String line = String.format("%s/%s/%s,%s\n", infos[0], infos[1], infos[2], text);
                        writer1.write(line);
                    }

                } else
                {
                    String line = String.format("%s\n", satir);
                    writer1.write(line);
                }

            }

            reader1.close();
            writer1.close();
            //Posts.txt dosyasının güncellenmesi sağlanır
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
}
