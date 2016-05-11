package ru.skuptsov.patterns.creational.builder;

import com.google.common.base.Objects;

/**
 * @author Sergey Kuptsov
 * @since 29/04/2016
 */
public class Builder {

    private String field1;
    private String field2;
    private String field3;
    private String field4;
    private String field5;

    Builder(String field1, String field2, String field3, String field4, String field5) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
        this.field5 = field5;
    }

    public static BuilderBuilder builder() {
        return new BuilderBuilder();
    }

    public static void main(String[] args) {

        Builder buildedObject = Builder.builder().field1("field1").field2("field2").build();

        System.out.println(buildedObject);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("field1", field1)
                .add("field2", field2)
                .add("field3", field3)
                .add("field4", field4)
                .add("field5", field5)
                .toString();
    }

    public static class BuilderBuilder {
        private String field1;
        private String field2;
        private String field3;
        private String field4;
        private String field5;

        BuilderBuilder() {
        }

        public Builder.BuilderBuilder field1(String field1) {
            this.field1 = field1;
            return this;
        }

        public Builder.BuilderBuilder field2(String field2) {
            this.field2 = field2;
            return this;
        }

        public Builder.BuilderBuilder field3(String field3) {
            this.field3 = field3;
            return this;
        }

        public Builder.BuilderBuilder field4(String field4) {
            this.field4 = field4;
            return this;
        }

        public Builder.BuilderBuilder field5(String field5) {
            this.field5 = field5;
            return this;
        }

        public Builder build() {
            return new Builder(field1, field2, field3, field4, field5);
        }

        public String toString() {
            return "ru.skuptsov.patterns.creational.builder.Builder.BuilderBuilder(field1=" + this.field1 + ", field2=" + this.field2 + ", field3=" + this.field3 + ", field4=" + this.field4 + ", field5=" + this.field5 + ")";
        }
    }
}
