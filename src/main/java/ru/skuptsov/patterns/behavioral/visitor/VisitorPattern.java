package ru.skuptsov.patterns.behavioral.visitor;

/**
 * @author Sergey Kuptsov
 * @since 13/06/2016
 */
public class VisitorPattern {

    public static void main(String[] args) {

        Result result = True.t().and(True.t().or(False.f())).or(False.f().and(True.t()));

        ToSqlVisitor toSqlVisitor = new ToSqlVisitor();

        result.accept(toSqlVisitor);

        System.out.println(toSqlVisitor.getString());
        System.out.println(result.result());

    }

    private interface Result {
        boolean result();

        void accept(Visitor v);

        default Result and(Result result) {
            return new And(this, result);
        }

        default Result or(Result result) {
            return new Or(this, result);
        }
    }

    private interface Visitor {
        void visit(True result);

        void visit(False result);

        void visit(And result);

        void visit(Or result);
    }

    private abstract static class LogicalOperation implements Result {
        protected final Result left;
        protected final Result right;

        protected LogicalOperation(Result left, Result right) {
            this.left = left;
            this.right = right;
        }

        public Result getLeft() {
            return left;
        }

        public Result getRight() {
            return right;
        }
    }

    private static class True implements Result {

        public static Result t() {
            return new True();
        }

        @Override
        public boolean result() {
            return true;
        }

        @Override
        public void accept(Visitor v) {
            v.visit(this);
        }
    }

    private static class False implements Result {

        public static Result f() {
            return new False();
        }

        @Override
        public boolean result() {
            return false;
        }

        @Override
        public void accept(Visitor v) {
            v.visit(this);
        }
    }

    private static class And extends LogicalOperation {

        protected And(Result left, Result right) {
            super(left, right);
        }

        @Override
        public boolean result() {
            return left.result() && right.result();
        }

        @Override
        public void accept(Visitor v) {
            v.visit(this);
        }
    }

    private static class Or extends LogicalOperation {

        protected Or(Result left, Result right) {
            super(left, right);
        }

        @Override
        public boolean result() {
            return left.result() || right.result();
        }

        @Override
        public void accept(Visitor v) {
            v.visit(this);
        }
    }

    private static class ToSqlVisitor implements Visitor {

        StringBuilder stringBuilder = new StringBuilder();

        public String getString() {
            return stringBuilder.toString();
        }

        @Override
        public void visit(True result) {
            stringBuilder.append("true");
        }

        @Override
        public void visit(False result) {
            stringBuilder.append("false");
        }

        @Override
        public void visit(And result) {
            stringBuilder.append("(");
            result.getLeft().accept(this);
            stringBuilder.append(" AND ");
            result.getRight().accept(this);
            stringBuilder.append(")");
        }

        @Override
        public void visit(Or result) {
            stringBuilder.append("(");
            result.getLeft().accept(this);
            stringBuilder.append(" OR ");
            result.getRight().accept(this);
            stringBuilder.append(")");
        }
    }
}
