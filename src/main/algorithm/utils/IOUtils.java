package algorithm.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Сергей on 02.02.2016.
 */
public class IOUtils {

    private final Scanner scanner;

    private IOUtils(String filePath) throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filePath).getFile());
        scanner = new Scanner(file);
    }

    public static IOUtils createFromFilePath(String filePath) throws FileNotFoundException {
        return new IOUtils(filePath);
    }

    public Integer readInt() {
        return scanner.nextInt();
    }

    public Double readDouble() {
        return scanner.nextDouble();
    }

    public boolean isEmpty() {
        return !scanner.hasNext();
    }

    public void println(Object o) {
        System.out.println(o);
    }

    public void print(int[] o) {
        System.out.println(Arrays.toString(o));
    }


    public Float readFloat() {
        return scanner.nextFloat();
    }
}
