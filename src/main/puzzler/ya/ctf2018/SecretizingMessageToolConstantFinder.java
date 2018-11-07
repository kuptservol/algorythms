package puzzler.ya.ctf2018;

import java.time.Instant;

/**
 * @author Sergey Kuptsov <kuptservol@yandex-team.ru>
 */
public class SecretizingMessageToolConstantFinder {

    public static void main(String[] args) {
        long M = 4294967296L;
        long A = 134775813L;
        long C = 1;
        long MAX_SEED = 1000000000;

        long KNOWN_KEY_FIRST_PART = 250595469;
        long known_key_id = 534;
//        long KNOWN_KEY_FIRST_PART = 266458202L;
//        long known_key_id = 3;

        long X;
        long NEXT_X;

        long seed_iteration = (known_key_id - 1) * 4 + 1;

        for (long SEED = 1; SEED < MAX_SEED; SEED++) {
            if ((SEED % 1000000) == 0) {
                System.out.println("Passes SEED : " + SEED + " time: " + Instant.now());
            }
            //for check
            // long SEED = 18273645;
            X = SEED;
            for (int i = 1; i <= seed_iteration; i++) {
                NEXT_X = (A * X + C) % M;
                X = NEXT_X;
            }

            if (X == KNOWN_KEY_FIRST_PART) {
                System.out.println("Found correct SEED : " + SEED);
                break;
            }
        }
    }
}
