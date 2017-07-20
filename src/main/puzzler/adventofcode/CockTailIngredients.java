package puzzler.adventofcode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.MoreObjects;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * @author Sergey Kuptsov
 * @since 13/08/2016
 */
public class CockTailIngredients {

    private static final Map<String, IngredientProps> ingredients = new HashMap<>();
    private static final List<String> ingredientsIndex = new ArrayList<>();
    private static final Map<Long, Table<String, Integer, IngredientProps>> highestScore = new HashMap<>();
    private static Integer maxIterations;

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(CockTailIngredients.class.getResourceAsStream("/cocktailIngredients.txt")));

        reader.lines().forEach(line -> {
            String[] split = line.split(" ");

            ingredientsIndex.add(split[0]);
            ingredients.put(split[0],
                    IngredientProps.builder()
                            .capacity(Integer.valueOf(split[2].substring(0, split[2].length() - 1)))
                            .durability(Integer.valueOf(split[4].substring(0, split[4].length() - 1)))
                            .flavor(Integer.valueOf(split[6].substring(0, split[6].length() - 1)))
                            .texture(Integer.valueOf(split[8].substring(0, split[8].length() - 1)))
                            .calories(Integer.valueOf(split[10].substring(0, split[10].length())))
                            .build());
        });

        maxIterations = ingredients.size();

        calc(100, HashBasedTable.create());

        highestScore.entrySet().stream()
                .sorted((o1, o2) -> o2.getKey().compareTo(o1.getKey()))
                .forEach(j -> System.out.println(j));

        System.out.println(highestScore.entrySet().stream()
                .sorted((o1, o2) -> o2.getKey().compareTo(o1.getKey())).findFirst().get());
    }

    private static void calc(int percentLeft, Table<String, Integer, IngredientProps> ingredientsWithProps) {
        if (ingredientsWithProps.size() == ingredients.size()) {
            int sum = 0;
            boolean hasEmptyElement = false;
            for (Table.Cell<String, Integer, IngredientProps> stringIntegerIngredientPropsCell : ingredientsWithProps.cellSet()) {

                Integer columnKey = stringIntegerIngredientPropsCell.getColumnKey();
                if (columnKey == 0) {
                    hasEmptyElement = true;
                    break;
                }
                sum += columnKey;
            }

            //System.out.println(sum);
            if (sum == 100 && !hasEmptyElement)
                calculateScore(ingredientsWithProps);
            return;
        }
        String nextIngredient = ingredientsIndex.get(ingredientsWithProps.size());
        IngredientProps nextIngredientProps = ingredients.get(nextIngredient);
        for (int i = 0; i <= percentLeft; i++) {
            Table<String, Integer, IngredientProps> ingredientsWithPropsNew = HashBasedTable.create();
            ingredientsWithPropsNew.putAll(ingredientsWithProps);
            ingredientsWithPropsNew.put(nextIngredient, i, nextIngredientProps);
            calc(100 - i, ingredientsWithPropsNew);
        }
    }

    private static void calculateScore(Table<String, Integer, IngredientProps> ingredientsWithProps) {
        long score;
        long scoreCapacity = 0;
        long scoreDurability = 0;
        long scoreFlavor = 0;
        long scoreTexture = 0;
        long scoreCalories = 0;

        for (Table.Cell<String, Integer, IngredientProps> cell : ingredientsWithProps.cellSet()) {
            if (cell.getColumnKey() == 44) {
                int u = 0;
            }
            scoreCapacity += cell.getColumnKey() * cell.getValue().getCapacity();
            scoreDurability += cell.getColumnKey() * cell.getValue().getDurability();
            scoreFlavor += cell.getColumnKey() * cell.getValue().getFlavor();
            scoreTexture += cell.getColumnKey() * cell.getValue().getTexture();
            scoreCalories += cell.getColumnKey() * cell.getValue().getCalories();
        }

        if (scoreCapacity > 0 && scoreDurability > 0 && scoreFlavor > 0 && scoreTexture > 0 && scoreCalories == 500) {
            score = scoreCapacity * scoreDurability * scoreFlavor * scoreTexture;
        } else
            score = 0;

        highestScore.put(score, ingredientsWithProps);
    }

    private static class IngredientProps {
        private long capacity;
        private long durability;
        private long flavor;
        private long texture;
        private long calories;

        @java.beans.ConstructorProperties({"capacity", "durability", "flavor", "texture", "calories"})
        IngredientProps(int capacity, long durability, long flavor, long texture, long calories) {
            this.capacity = capacity;
            this.durability = durability;
            this.flavor = flavor;
            this.texture = texture;
            this.calories = calories;
        }

        public static IngredientPropsBuilder builder() {
            return new IngredientPropsBuilder();
        }

        public long getCapacity() {
            return capacity;
        }

        public long getDurability() {
            return durability;
        }

        public long getFlavor() {
            return flavor;
        }

        public long getTexture() {
            return texture;
        }

        public long getCalories() {
            return calories;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("capacity", capacity)
                    .add("durability", durability)
                    .add("flavor", flavor)
                    .add("texture", texture)
                    .add("calories", calories)
                    .toString();
        }

        public static class IngredientPropsBuilder {
            private int capacity;
            private long durability;
            private long flavor;
            private long texture;
            private long calories;

            IngredientPropsBuilder() {
            }

            public IngredientProps.IngredientPropsBuilder capacity(int capacity) {
                this.capacity = capacity;
                return this;
            }

            public IngredientProps.IngredientPropsBuilder durability(long durability) {
                this.durability = durability;
                return this;
            }

            public IngredientProps.IngredientPropsBuilder flavor(long flavor) {
                this.flavor = flavor;
                return this;
            }

            public IngredientProps.IngredientPropsBuilder texture(long texture) {
                this.texture = texture;
                return this;
            }

            public IngredientProps.IngredientPropsBuilder calories(long calories) {
                this.calories = calories;
                return this;
            }

            public IngredientProps build() {
                return new IngredientProps(capacity, durability, flavor, texture, calories);
            }

            public String toString() {
                return "puzzlers.adventofcode.CockTailIngredients.IngredientProps.IngredientPropsBuilder(capacity=" + this.capacity + ", durability=" + this.durability + ", flavor=" + this.flavor + ", texture=" + this.texture + ", calories=" + this.calories + ")";
            }
        }
    }


}
