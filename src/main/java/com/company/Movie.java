package com.company;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class Movie {

    String title, time, chan;
    String rate;
    static String rt;

    Movie(String n, String t, String c) throws IOException {
        getData(n + " фильм");
        title = n;
        time = t;
        chan = c;
        rate = rt;
    }

    public static void getData(String mt) throws IOException {
        {

            Connection.Response movie = Jsoup.connect("https://google.com/search?q=" + mt)
                    .followRedirects(true)
                    .execute();

            Progr.cleanFile("infomovie");
            Progr.WriteToFile("infomovie", movie.body());

            File input = new File("C:/Users/User/Documents/infomovie.txt");
            Document moviedoc = Jsoup.parse(input, "UTF-8");//

            Elements movieData = moviedoc.getElementsContainingText("kinopoisk")
                .select("div[class='rc']").eq(0);

            for (Element x : movieData) {
                try {
                    rt = x.getElementsContainingText("Рейтинг")
                            .select("div[class*='dhIWPd f']").text();
                    char[] tempPl = rt.toCharArray();
                    char[] copyPl = new char[6];
                    int j = 9;
                    for (int i = 0; i < copyPl.length; i++) {
                        if (tempPl[j] == ' ' || tempPl[j] == '-') {
                            copyPl[i] = ' ';
                        } else {
                            copyPl[i] = tempPl[j];
                        }
                        j++;
                    }
                    rt = String.valueOf(copyPl);
                }
                catch (ArrayIndexOutOfBoundsException e){
                    rt = "н/д";
                }
            }
        }
    }
}