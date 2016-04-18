package ru.skuptsov.patterns.creational.factory;

/**
 * Created by Сергей on 13.04.2016.
 */
public class FactoryMethod {

    public interface Transport {
        void send();
    }

    enum SCHEME {
        HTTP,
        HTTPS
    }

    public static class Http implements Transport {

        @Override
        public void send() {
            System.out.println("Send by unsecure transport");
        }
    }

    public static class Https implements Transport {

        @Override
        public void send() {
            System.out.println("Send by secure transport");
        }
    }

    public static class TransportFactory {

        private final SCHEME currentScheme;

        public TransportFactory(SCHEME scheme) {
            this.currentScheme = scheme;
        }

        public Transport create() {
            if (currentScheme == SCHEME.HTTP)
                return new Http();
            else if (currentScheme == SCHEME.HTTPS)
                return new Https();
            else
                throw new IllegalArgumentException();
        }
    }

    public static class Client{
        private final TransportFactory transportFactory;

        public Client(TransportFactory transportFactory) {
            this.transportFactory = transportFactory;
        }

        public void send(){
            transportFactory.create().send();
        }
    }

    public static void main(String[] args) {
        TransportFactory factory = new TransportFactory(SCHEME.HTTPS);
        Client client = new Client(factory);

        client.send();
    }
}

