package ru.skuptsov.algorythms.unionfind;

import java.util.Arrays;

/**
 * Created by Сергей on 04.02.2016.
 */
public class CheckMaxLength {

    int maxHeight = 0;
    int[] height;
    int[] id;


    public CheckMaxLength(int[] id) {
        this.id = id;
    }

    public static void main(String[] args) {

        int[] id = {0 ,0 ,0 ,0 ,2 ,0 ,4 ,0 ,2 ,0  } ;
        CheckMaxLength checkMaxLength = new CheckMaxLength(id);

        checkMaxLength.height = new int[id.length];
        for (int i = 0; i < id.length; i++) {
            checkMaxLength.height[i]=0;
        }

        for (int i = 0; i < id.length; i++) {
            checkMaxLength.findRoot(i);
        }

        System.out.println(checkMaxLength.maxHeight);

        System.out.println(Arrays.toString(checkMaxLength.height));

    }

    void findRoot(int p){

        int k = p;
        int i =0;
        while(id[p]!=p) {
            p = id[p];
            i++;
        }

        height[k] = i;

        for (int j = 0; j < id.length; j++) {
            if(i!=0){
            if (i/2-height[j]>0) {
                System.out.println("alarm");
            }
        }}

        if(i> maxHeight)
            maxHeight =i;

    }

}


