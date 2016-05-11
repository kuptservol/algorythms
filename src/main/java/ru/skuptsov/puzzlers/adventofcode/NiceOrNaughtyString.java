package ru.skuptsov.puzzlers.adventofcode;

import java.util.Scanner;

/**
 * Created by Сергей on 28.03.2016.
 */
public class NiceOrNaughtyString {

    public static final int CHAR_A = 97;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int niceString = 0;

        while (scanner.hasNext()) {
            String aWord = scanner.next();
            if (isWordPretty(aWord)) {
                niceString++;
            }
        }

        System.out.println(niceString);

    }


    public static boolean isWordPretty(String word) {

        boolean hasDouble = false;
        boolean hasUglyChars = false;
        int vowelCounter = 0;
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {

            char ch = chars[i];
            if(i!=chars.length-1)
                if(chars[i+1]==ch)
                    hasDouble = true;


            switch (ch){
                case 'a' :
                    vowelCounter++;
                    if(i!=chars.length-1)
                        if(chars[i+1]=='b')
                            hasUglyChars = true;
                    break;
                case 'c':
                    if(i!=chars.length-1)
                        if(chars[i+1]=='d')
                            hasUglyChars = true;
                    break;
                case 'p':
                    if(i!=chars.length-1)
                        if(chars[i+1]=='q')
                            hasUglyChars = true;
                    break;
                case 'x':
                    if(i!=chars.length-1)
                        if(chars[i+1]=='y')
                            hasUglyChars = true;
                    break;
                case 'e' :
                    vowelCounter++;
                    break;
                case 'i' :
                    vowelCounter++;
                    break;
                case 'o' :
                    vowelCounter++;
                    break;
                case 'u' :
                    vowelCounter++;
                    break;

            }
        }


        return vowelCounter>=3 && hasDouble && !hasUglyChars;
    }


}
