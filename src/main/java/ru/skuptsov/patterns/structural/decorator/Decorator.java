package ru.skuptsov.patterns.structural.decorator;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;

/**
 * @author Sergey Kuptsov
 * @since 17/04/2016
 */
public class Decorator {

    public interface LineReader {
        String read() throws IOException;
    }

    private static class SimpleLineReader implements LineReader {

        private final Reader in;

        public SimpleLineReader(Reader in) {
            this.in = in;
        }

        @Override
        public String read() throws IOException {
            StringBuilder builder = new StringBuilder();
            int ch;
            while ((ch = in.read()) != -1) {
                builder.append(ch);
            }
            return builder.toString();
        }
    }

    private static class LazyLineReader implements LineReader {

        private final LineReader lineReader;
        private volatile String line = null;

        private LazyLineReader(LineReader lineReader) {
            this.lineReader = lineReader;
        }

        @Override
        public String read() throws IOException {
            if (line == null) {
                synchronized (this) {
                    if (line == null) {
                        line = lineReader.read();
                    }
                }
            }
            return line;
        }
    }

    private static class UpperCaseLineReader implements LineReader {

        private final LineReader lineReader;

        private UpperCaseLineReader(LineReader lineReader) {
            this.lineReader = lineReader;
        }

        @Override
        public String read() throws IOException {
            return lineReader.read().toUpperCase();
        }
    }

    public static void main(String[] args) throws IOException {

        char[] chars = {'A', 'f', 'g', 'r'};
        LineReader lineReader = new UpperCaseLineReader(
                new LazyLineReader(
                        new SimpleLineReader(
                                new CharArrayReader(chars))));

        System.out.println(lineReader.read());
        System.out.println(lineReader.read());

    }

}
