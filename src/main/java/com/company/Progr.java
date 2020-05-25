package com.company;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import java.io.IOException;

import org.jsoup.nodes.Element;

import java.io.*;
public class Progr {
    static String time, channel;
    static String mtitle;



    public static void getProgr() throws IOException {

        Document doc = Jsoup.connect("http://tv.mail.ru/ekaterinburg/general/")
                .userAgent("Chrome")
                .get();

        CreateFile("tvprogr");
        WriteToFile("tvprogr", doc.text() + "\n\n\n");
        Progr.CreateFile("infomovie");

        Elements Chan = doc.select("div[class=p-channels__item js-channel-item]");
        for (
                Element ch : Chan) {
            Elements Name = ch.getAllElements().select("div[class*=genre138]");
            if (Name.size() != 0) {
                channel = ch.select("a[class=p-channels__item__info__title__link]").text();
                if (channel.contains("live")) {
                    char[] tempCh = channel.toCharArray();
                    char[] copyCh = new char[tempCh.length - 4];
                    for (int i = 0; i < copyCh.length; i++) {
                        copyCh[i] = tempCh[i];
                    }

                    channel = String.valueOf(copyCh);
                }
                System.out.println(channel);
            }
            for (Element sp : Name) {
                time = sp.text().substring(0, 5);
                mtitle = sp.text().substring(5);
                if (mtitle.contains("серия")) continue;
                Movie x = new Movie(mtitle, time, channel);
                String total = x.title + "\n" + x.rate + "\n" + x.time + "\n" + x.chan + "\n";
                ExcelFile.writeExcel(x);
                System.out.println(total);
                WriteToFile("tvprogr", total);
            }
        }
        String title = doc.title();
        System.out.println(title);

    }

    public static void CreateFile(String s) {
        try {
            File data = new File("C:/Users/User/Documents/" + s + ".txt");
            if (data.createNewFile()) {
                System.out.println("File created: " + data.getName());
            } else {
                System.out.println("File already exists.");
                data.delete();
                data.createNewFile();
                System.out.println("The existing file was rewritten.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void WriteToFile(String n, String s) {
        try {
            FileWriter myWriter = new FileWriter("C:/Users/User/Documents/" + n + ".txt", true);
            myWriter.write(s);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
    public static void cleanFile(String n) {
        try {
            FileWriter myWriter = new FileWriter("C:/Users/User/Documents/" + n + ".txt");
            myWriter.write("");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}