package puzzler.codingame;

import java.math.BigDecimal;

/**
 * @author Sergey Kuptsov <kuptservol@yandex-team.ru>
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(BigDecimal.valueOf(552970)
                .divide(BigDecimal.valueOf(Math.log(5.0d)), BigDecimal.ROUND_FLOOR)
                .multiply(BigDecimal.valueOf(Math.log(10))));
    }
}
