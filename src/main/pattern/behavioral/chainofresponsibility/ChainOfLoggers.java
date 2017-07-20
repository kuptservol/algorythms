package pattern.behavioral.chainofresponsibility;

/**
 * Created by Сергей on 12.05.2016.
 */
public class ChainOfLoggers {

    public static void main(String[] args) {
        Logger logger = new FileLogger(AbstractLogger.LogLevel.DEBUG).then(new DBLogger(AbstractLogger.LogLevel.INFO)).then(new ConsoleLogger(AbstractLogger.LogLevel.ERROR));

        logger.log(AbstractLogger.LogLevel.DEBUG, "debug information");
        System.out.println("-------------");
        logger.log(AbstractLogger.LogLevel.INFO, "info info");
        System.out.println("-------------");
        logger.log(AbstractLogger.LogLevel.ERROR, "ERROR info");
    }

    private interface Logger {
        void log(AbstractLogger.LogLevel logLevel, String message);
    }

    private static abstract class AbstractLogger implements Logger {

        private AbstractLogger next;
        private LogLevel logLevel;
        public AbstractLogger(LogLevel logLevel) {
            this.logLevel = logLevel;
        }

        public AbstractLogger then(AbstractLogger abstractLogger) {
            setNext(abstractLogger);
            return this;
        }

        private void setNext(AbstractLogger abstractLogger) {
            if (this.next != null) {
                this.next.setNext(abstractLogger);
            } else {
                this.next = abstractLogger;
            }
        }

        protected abstract void logMessage(String message);

        public void log(LogLevel logLevel, String message) {
            if (logLevel.level >= this.logLevel.level) {
                logMessage(message);
            }
            if (next != null) {
                next.log(logLevel, message);
            }
        }

        public enum LogLevel {
            DEBUG(0),
            INFO(1),
            ERROR(2);

            private final int level;

            LogLevel(int level) {
                this.level = level;
            }
        }
    }

    private static class FileLogger extends AbstractLogger {

        public FileLogger(LogLevel logLevel) {
            super(logLevel);
        }

        @Override
        protected void logMessage(String message) {
            System.out.println("Writing " + message + " to file");
        }
    }

    private static class ConsoleLogger extends AbstractLogger {

        public ConsoleLogger(LogLevel logLevel) {
            super(logLevel);
        }

        @Override
        protected void logMessage(String message) {
            System.out.println("Writing " + message + " to console");
        }
    }

    private static class DBLogger extends AbstractLogger {

        public DBLogger(LogLevel logLevel) {
            super(logLevel);
        }

        @Override
        protected void logMessage(String message) {
            System.out.println("Writing " + message + " to db");
        }
    }
}
