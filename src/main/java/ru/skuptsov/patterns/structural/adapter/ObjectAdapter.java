package ru.skuptsov.patterns.structural.adapter;

import java.util.Random;

/**
 * @author Sergey Kuptsov
 * @since 17/04/2016
 */
public class ObjectAdapter {

    interface Generator {
        int next();
    }

    private static class GeneratorAdapter implements Generator {

        private final static Random random = new Random();

        @Override
        public int next() {
            return random.nextInt();
        }
    }

    private static class SequenceGenerator {

        private final Generator generator;

        public SequenceGenerator(Generator generator) {
            this.generator = generator;
        }

        public int next() {
            return generator.next();
        }
    }

    public static void main(String[] args) {

        SequenceGenerator sequenceGenerator = new SequenceGenerator(new GeneratorAdapter());
        System.out.println(sequenceGenerator.next());

    }


}
