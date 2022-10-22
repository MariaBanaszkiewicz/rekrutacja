package pl.akai;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    /*
        Twoim zadaniem jest napisanie prostego programu do pobierania i transformowania danych
        udostępnianych przez API. Dokumentacje API możesz znależć pod poniższym linkiem:
        https://akai-recruitment.herokuapp.com/documentation.html

        Całe API zawiera jeden endpoint: https://akai-recruitment.herokuapp.com/book
        Endpoint ten zwraca liste książek zawierajacch informację takie jak:
        - id
        - tytuł
        - autor
        - ocena

        Twoim zadaniem jest:
        1. Stworzenie odpowiedniej klasy do przechowywania informacji o książce
        2. Sparsowanie danych udostępnianych przez endpoint. Aby ułatwić to zadanie,
           do projektu są dołaczone 3 najpopularniejsze biblioteki do parsowania JSONów
           do obiektów Javy - Gson, Org.Json, Jackson. Możesz wykorzystać dowolną z nich
        3. Po sparsowaniu JSONu do obiektów Javy, uzupełnij program o funkcję wypisującą 3 autorów z
           najwyższą średnią ocen. Na przykład, gdy osoba X jest autorem książki A z oceną 9 i B z oceną 8,
           to powinna zostać wyświetlona informacja: X - 8.5

       Projekt został utworzony przy użyciu najnowszej Javy 17,
       jednakże nic nie stoi na przeszkodzie użycia innej wersji jeśli chcesz
     */

    public static void main(String[] args) throws Exception {
         URL url = new URL("https://akai-recruitment.herokuapp.com/book");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();

        if (status > 299){
            throw new Exception("Failed to connect with server.");
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        ArrayList<Book> books = new Gson().fromJson(String.valueOf(content), new TypeToken<ArrayList<Book>>() {}.getType());
        con.disconnect();

        Map<String, Float> authors_sum = new HashMap<String, Float>();
        Map<String, Integer> authors_times = new HashMap<String, Integer>();

        for(Book book : books){
            authors_sum.merge(book.getAuthor(),book.getRating(),Float::sum);
            authors_times.merge(book.getAuthor(),1,Integer::sum);
        }
        Map<String, Float> authors_result = new HashMap<String, Float>();
        for(Map.Entry<String, Float> a : authors_sum.entrySet()){
            float sum = a.getValue();
            float times = authors_times.get(a.getKey());
            authors_result.put(a.getKey(), sum/times);
        }

        Float top1val = 0.0f;
        String top1key = "";

        Float top2val = 0.0f;
        String top2key = "";

        Float top3val = 0.0f;
        String top3key = "";

        for(Map.Entry<String, Float> r : authors_result.entrySet()){
            Float val = r.getValue();
            if (val>top3val){
                if (val>top2val){
                    top3val = top2val;
                    top3key = top2key;
                    if(val>top1val){
                        top2val = top1val;
                        top2key = top1key;
                        top1val = val;
                        top1key = r.getKey();
                    } else {
                        top2val = val;
                        top2key = r.getKey();
                    }
                } else {
                    top3val = val;
                    top3key = r.getKey();
                }
            }
        }


        System.out.println("1. "+top1key+" - "+top1val+"\n" +
                "2. "+top2key+" - "+top2val+"\n" +
                "3. "+top3key+" - "+top3val);

    }
}
