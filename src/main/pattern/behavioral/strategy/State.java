package pattern.behavioral.strategy;

/**
 * @author Sergey Kuptsov
 * @since 19/06/2016
 */
public class State {

    public static void main(String[] args) {
        TCPConnection tcpConnection = new TCPConnectionImpl();

        tcpConnection.open();
        tcpConnection.acknowledge();
        tcpConnection.close();
        tcpConnection.open();
    }

    interface TCPConnection {
        void open();

        void close();

        void acknowledge();
    }

    interface TCPState {
        TCPState open();

        TCPState close();

        TCPState acknowledge();
    }

    static class TCPConnectionImpl implements TCPConnection {

        private TCPState tcpState;

        TCPConnectionImpl() {
            this.tcpState = new TCPListen();
        }

        @Override
        public void open() {
            tcpState = tcpState.open();
        }

        @Override
        public void close() {
            tcpState = tcpState.close();
        }

        @Override
        public void acknowledge() {
            tcpState = tcpState.acknowledge();
        }
    }

    static class TCPListen implements TCPState {

        @Override
        public TCPState open() {
            System.out.println("Listening to channel");
            return new TCPEstablished();
        }

        @Override
        public TCPState close() {
            System.out.println("closing connection");
            return new TCPClosed();
        }

        @Override
        public TCPState acknowledge() {
            throw new IllegalArgumentException();

        }
    }

    static class TCPClosed implements TCPState {

        @Override
        public TCPState open() {
            throw new IllegalArgumentException();
        }

        @Override
        public TCPState close() {
            throw new IllegalArgumentException();
        }

        @Override
        public TCPState acknowledge() {
            throw new IllegalArgumentException();
        }
    }

    static class TCPEstablished implements TCPState {

        @Override
        public TCPState open() {
            throw new IllegalArgumentException();
        }

        @Override
        public TCPState close() {
            System.out.println("closing connection");
            return new TCPClosed();
        }

        @Override
        public TCPState acknowledge() {
            System.out.println("acknowledge connection");
            return this;
        }
    }

}
