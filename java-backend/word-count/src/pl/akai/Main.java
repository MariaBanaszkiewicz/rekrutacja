package pl.akai;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Main {

    private static String[] sentences = {
            "Taki mamy klimat",
            "Wszędzie dobrze ale w domu najlepiej",
            "Wyskoczył jak Filip z konopii",
            "Gdzie kucharek sześć tam nie ma co jeść",
            "Nie ma to jak w domu",
            "Konduktorze łaskawy zabierz nas do Warszawy",
            "Jeżeli nie zjesz obiadu to nie dostaniesz deseru",
            "Bez pracy nie ma kołaczy",
            "Kto sieje wiatr ten zbiera burzę",
            "Być szybkim jak wiatr",
            "Kopać pod kimś dołki",
            "Gdzie raki zimują",
            "Gdzie pieprz rośnie",
            "Swoją drogą to gdzie rośnie pieprz?",
            "Mam nadzieję, że poradzisz sobie z tym zadaniem bez problemu",
            "Nie powinno sprawić żadnego problemu, bo Google jest dozwolony"
    };

    public static void main(String[] args) {
        /* TODO Twoim zadaniem jest wypisanie na konsoli trzech najczęściej występujących słów
                w tablicy 'sentences' wraz z ilością ich wystąpień..

                Przykładowy wynik:
                1. "mam" - 12
                2. "tak" - 5
                3. "z" - 2
        */
        Map<String, Integer> Words = new HashMap<String, Integer>();

        for(String el : sentences){
            String[] arr = el.split(" ");
            for(String word : arr){
                Words.merge(word.toLowerCase(),1,Integer::sum);
            }
        }
        Integer top1val = 0;
        String top1key = "";

        Integer top2val = 0;
        String top2key = "";

        Integer top3val = 0;
        String top3key = "";

        for(Map.Entry<String, Integer> w : Words.entrySet()){
            Integer val = w.getValue();
            if (val>top3val){
                if (val>top2val){
                    top3val = top2val;
                    top3key = top2key;
                    if(val>top1val){
                        top2val = top1val;
                        top2key = top1key;
                        top1val = val;
                        top1key = w.getKey();
                    } else {
                        top2val = val;
                        top2key = w.getKey();
                    }
                } else {
                    top3val = val;
                    top3key = w.getKey();
                }
            }
        }


        System.out.println("1. "+top1key+" - "+top1val+"\n" +
                "2. "+top2key+" - "+top2val+"\n" +
                "3. "+top3key+" - "+top3val);
    }

}