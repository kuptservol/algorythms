package ru.skuptsov.puzzlers.coursera.princeton.algorythm.program.assignment.one;

import ru.skuptsov.algorythms.utils.IOUtils;


import java.io.FileNotFoundException;

/**
 * Created by Сергей on 07.02.2016.
 */
public class FindONotationConstantsByRunningTimes {


    public static void main(String[] args) throws FileNotFoundException {

        IOUtils io = IOUtils.createFromFilePath("running_times.txt");


        int[] Ns = new int[100];
        double[] times = new double[100];
        int i = 0;

        while(!io.isEmpty()){
            Ns[i] = io.readInt();
            times[i] = io.readDouble();
            i++;
        }

        double avgRatio=0;
        for (int j = 1; j < i; j++) {

            if(times[j-1] != 0) {
                double ratio = logOfBase(Ns[j] / Ns[j - 1], times[j] / times[j - 1]);
                System.out.println(ratio);

                avgRatio += ratio;
            }
        }

        System.out.println((avgRatio/(i-2)));

        System.out.println(16+16+32+32+8+24+192);
    }

    public static double logOfBase(double base, double num) {
        return Math.log(num) / Math.log(base);
    }
}
