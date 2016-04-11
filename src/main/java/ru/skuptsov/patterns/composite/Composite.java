package ru.skuptsov.patterns.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Kuptsov
 * @since 08/04/2016
 */
public class Composite {

    @FunctionalInterface
    public interface ComponentPrice {
        int getPrice();

        default void add(ComponentPrice componentPrice) {
            throw new UnsupportedOperationException();
        }

        default void remove(ComponentPrice componentPrice) {
            throw new UnsupportedOperationException();
        }
    }

    public static abstract class SingleComponent implements ComponentPrice {
    }

    public static abstract class CompositeComponent implements ComponentPrice {

        private final List<ComponentPrice> components = new ArrayList<>();

        @Override
        public int getPrice() {
            return components.stream().map(ComponentPrice::getPrice).reduce(0, (price1, price2) -> price1 + price2);
        }

        @Override
        public void add(ComponentPrice componentPrice) {
            components.add(componentPrice);
        }

        @Override
        public void remove(ComponentPrice componentPrice) {
            components.remove(componentPrice);
        }
    }

    public static class Apple extends SingleComponent {

        public static final int APPLE_PRICE = 50;

        @Override
        public int getPrice() {
            return APPLE_PRICE;
        }
    }


    public static class ShoppingCart extends CompositeComponent {

    }

    public static class BunchOfBananas extends CompositeComponent {

        private final int num;

        public static final int BANANA_PRICE = 100;


        public BunchOfBananas(int num) {
            this.num = num;
        }

        @Override
        public int getPrice() {
            return BANANA_PRICE * num + super.getPrice();
        }
    }


    public static void main(String[] args) {

        ComponentPrice apple1 = new Apple();
        ComponentPrice apple2 = new Apple();
        ComponentPrice bunchOfBananas = new BunchOfBananas(10);

        ComponentPrice shoppingCart = new ShoppingCart();
        shoppingCart.add(apple1);
        shoppingCart.add(apple2);
        shoppingCart.add(bunchOfBananas);

        ComponentPrice moreBiggerShoppingCart = new ShoppingCart();
        moreBiggerShoppingCart.add(shoppingCart);


        System.out.println(moreBiggerShoppingCart.getPrice());


    }

}
