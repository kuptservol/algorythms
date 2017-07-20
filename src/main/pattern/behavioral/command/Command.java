package pattern.behavioral.command;

/**
 * Created by Сергей on 12.04.2016.
 */
public class Command {


    public static void main(String[] args) {
        TradePlatForm tradePlatForm = new TradePlatForm();
        Stock stock = new Stock();

        BuyStock buyStock = new BuyStock(stock);
        SellStock sellStock = new SellStock(stock);

        tradePlatForm.executeNext(buyStock);
        tradePlatForm.executeNext(sellStock);
    }

    public interface MarketOperation {
        void execute();
    }

    public static class Stock {

        private String name = "ABC";
        private int quantity = 10;

        public void buy() {
            System.out.println("Stock [ Name: " + name + ", Quantity: " + quantity + " ] bought");
        }

        public void sell() {
            System.out.println("Stock [ Name: " + name + ", Quantity: " + quantity + " ] sold");
        }
    }

    public static class SellStock implements MarketOperation {

        private final Stock stock;

        public SellStock(Stock stock) {
            this.stock = stock;
        }

        @Override
        public void execute() {
            stock.sell();
        }
    }

    public static class BuyStock implements MarketOperation {

        private final Stock stock;

        public BuyStock(Stock stock) {
            this.stock = stock;
        }

        @Override
        public void execute() {
            stock.buy();
        }
    }

    public static class TradePlatForm {

        public void executeNext(MarketOperation marketOperation) {
            marketOperation.execute();
        }
    }
}
