package ru.skuptsov.puzzlers.adventofcode;

import com.google.common.base.Objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;

import static java.lang.Integer.valueOf;

/**
 * @author Sergey Kuptsov
 * @since 10/04/2016
 */
public class WireCircuitCommand {

    private final Map<String, Gate> gates = new HashMap<>();
    private List<Gate> startGates = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        WireCircuitCommand.class.getResourceAsStream("/signal_gates")));

        WireCircuitCommand wireCircuitCommand = new WireCircuitCommand();


        String line;
        while ((line = reader.readLine()) != null) {
            wireCircuitCommand.processCommand(line);
        }
        wireCircuitCommand.runSignalCircuit();
        System.out.println(wireCircuitCommand.getOutputSignalValue("a"));
        //wireCircuitCommand.printOutputSignalValues();
    }

    private int getOutputSignalValue(String a) {
        return gates.get(a).getOutputSignalValue();
    }

    private void printOutputSignalValues() {
        System.out.println(gates);
    }

    private void runSignalCircuit() {
        startGates.forEach(Gate::provideSignal);

    }

    private static abstract class Gate extends Observable implements Observer {

        protected int outputSignal;
        protected boolean signalOnOutput;
        private final String gateName;
        protected OperationType sourceOperationType;
        protected Gate[] sourceGates;

        public void addSourceGates(OperationType sourceOperationType, Gate[] sourceGates) {
            this.sourceGates = sourceGates;
            this.sourceOperationType = sourceOperationType;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("outputSignal", outputSignal)
                    .add("signalOnOutput", signalOnOutput)
                    .add("gateName", gateName)
                    .add("sourceOperationType", sourceOperationType)
                    .add("sourceGates", sourceGates)
                    .toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Gate that = (Gate) o;
            return Objects.equal(gateName, that.gateName);
        }

        public Gate[] getSourceGates() {
            return sourceGates;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(gateName);
        }

        protected Gate(String gateName) {
            this.gateName = gateName;
        }

        public boolean hasSignalOnOutput() {
            return signalOnOutput;
        }

        public int getOutputSignalValue() {
            return ;
        }

        public String gateName() {
            return gateName;
        }

        public void provideSignal() {
            executeSignalValue();
            if (hasSignalOnOutput()) {
                setChanged();
                notifyObservers(this);
            }
        }

        @Override
        public void update(Observable o, Object arg) {
            executeSignalValue();
        }

        protected abstract void executeSignalValue();

    }

    private enum OperationType {

        SIMPLE_VALUE(gate -> gate[0]),
        NOT(gate -> ~gate[0]),
        LSHIFT(gate -> gate[0] << gate[1]),
        RSHIFT(gate -> gate[0] >> gate[1]),
        AND(gate -> gate[0] & gate[1]),
        OR(gate -> gate[0] | gate[1]);

        private final Function<Integer[], Integer> function;

        OperationType(Function<Integer[], Integer> function) {
            this.function = function;
        }

        public int execute(Integer[] gates) {
            return function.apply(gates) & 0x0000FFFF;
        }
    }

    private static class OneSourceValueGate extends Gate {

        protected OneSourceValueGate(String gateName) {
            super(gateName);
        }

        @Override
        protected void executeSignalValue() {
            if (sourceGates[0].hasSignalOnOutput()) {
                this.outputSignal = sourceOperationType.execute(new Integer[]{
                        sourceGates[0].getOutputSignalValue()
                });
                signalOnOutput = true;
            }
        }
    }

    private static class TwoSourceValueGate extends Gate {

        protected TwoSourceValueGate(String gateName) {
            super(gateName);
        }

        @Override
        protected void executeSignalValue() {
            if (sourceGates[0].hasSignalOnOutput() && sourceGates[1].hasSignalOnOutput()) {
                this.outputSignal = sourceOperationType.execute(new Integer[]{
                        sourceGates[0].getOutputSignalValue(),
                        sourceGates[1].getOutputSignalValue()
                });
                signalOnOutput = true;
            }
        }
    }

    private static class SimpleValueGate extends OneSourceValueGate {

        protected SimpleValueGate(int value) {
            super("");
            outputSignal = value;
            signalOnOutput = true;
        }
    }

    private void processCommand(String line) {
        String[] signalTokens = line.split(" ");
        int i = 0;
        if (line.equals("1 AND cx -> cy")) {
            i++;
        }

        if (Character.isDigit(signalTokens[0].toCharArray()[0]) && signalTokens.length == 3) {
            Gate gate = new OneSourceValueGate(signalTokens[2]);
            gate.addSourceGates(OperationType.SIMPLE_VALUE, new Gate[]{new SimpleValueGate(valueOf(signalTokens[0]))});
            gates.put(gate.gateName(), gate);
            startGates.add(gate);
        } else if (signalTokens[0].equals("NOT")) {
            formOneValueGate(signalTokens[3], signalTokens[1], OperationType.NOT);
        } else {
            switch (signalTokens[1]) {
                case "AND":
                    formTargetGate(signalTokens, OperationType.AND);
                    break;
                case "OR":
                    formTargetGate(signalTokens, OperationType.OR);
                    break;
                case "LSHIFT":
                    formTargetGate(signalTokens, OperationType.LSHIFT);
                    break;
                case "RSHIFT":
                    formTargetGate(signalTokens, OperationType.RSHIFT);
                    break;
                case "->":
                    formOneValueGate(signalTokens[2], signalTokens[0], OperationType.SIMPLE_VALUE);
                    break;

            }
        }
    }

    private void formOneValueGate(String targetGateLetter, String sourceGateLetter, OperationType operationType) {
        Gate targetGate = new OneSourceValueGate(targetGateLetter);
        if (!gates.containsKey(targetGate.gateName)) {
            gates.put(targetGate.gateName(), targetGate);
        } else {
            targetGate = gates.get(targetGate.gateName());
        }
        Gate sourceGate = new OneSourceValueGate(sourceGateLetter);
        if (!gates.containsKey(sourceGate.gateName)) {
            gates.put(sourceGate.gateName(), targetGate);
        } else {
            sourceGate = gates.get(sourceGate.gateName);
        }
        sourceGate.addObserver(targetGate);
        targetGate.addSourceGates(operationType, new Gate[]{sourceGate});
    }

    private void formTargetGate(String[] signalTokens, OperationType operationType) {
        Gate targetGate = new TwoSourceValueGate(signalTokens[4]);
        if (!gates.containsKey(targetGate.gateName)) {
            gates.put(targetGate.gateName(), targetGate);
        } else {
            targetGate.addSourceGates(gates.get(targetGate.gateName()).sourceOperationType,
                    gates.get(targetGate.gateName()).getSourceGates());
        }

        Gate sourceGate1 = null;
        Gate sourceGate2 = null;

        if (!Character.isDigit(signalTokens[0].toCharArray()[0])) {

            sourceGate1 = new OneSourceValueGate(signalTokens[0]);
            if (!gates.containsKey(sourceGate1.gateName)) {
                gates.put(sourceGate1.gateName(), targetGate);
            } else {
                sourceGate1 = gates.get(sourceGate1.gateName);
            }
        } else {
            sourceGate1 = new SimpleValueGate(Integer.valueOf(signalTokens[0]));
        }

        if (!Character.isDigit(signalTokens[2].toCharArray()[0])) {
            sourceGate2 = new OneSourceValueGate(signalTokens[2]);
            if (!gates.containsKey(sourceGate2.gateName)) {
                gates.put(sourceGate2.gateName(), targetGate);
            } else {
                sourceGate2 = gates.get(sourceGate2.gateName);
            }
        } else {
            sourceGate2 = new SimpleValueGate(Integer.valueOf(signalTokens[2]));
        }

        sourceGate2.addObserver(targetGate);
        sourceGate1.addObserver(targetGate);

        targetGate.addSourceGates(operationType, new Gate[]{sourceGate1, sourceGate2});
    }
}
