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
    double rating;
    static double r;

    Movie(String n, String t, String c) throws IOException {
        title = n;
        time = t;
        chan = c;
        int y = 0;
        getData(n + " фильм", y);
        rating = r;
    }

    public static double getData(String mt, int y) throws IOException {
        {

            Connection.Response movie = Jsoup.connect("https://google.com/search?q=" + mt)
                    .followRedirects(true)
                    .execute();

            Progr.cleanFile("infomovie");
            Progr.WriteToFile("infomovie", movie.body());

            File input = new File("C:/Users/User/Documents/infomovie.txt");
            Document moviedoc = Jsoup.parse(input, "UTF-8");//

            Elements movieData = moviedoc.getElementsContainingText("kinopoisk")
                    .select("div[class='rc']").eq(y);

            for (Element x : movieData) {
                try {
                    String rt = x.getElementsContainingText("Рейтинг")
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

                    String[] str = rt.split("/");
                    r = Double.parseDouble(str[0].replaceAll(",", "."));
                } catch (ArrayIndexOutOfBoundsException e) {
                    y++;
                    getData(mt, y);
                }
            }
        }
        return r;
    }
}