package ru.skuptsov.puzzlers.adventofcode;

import com.google.common.base.Objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.lang.Integer.valueOf;

/**
 * @author Sergey Kuptsov
 * @since 10/04/2016
 */
public class WireCircuitCommand {

    private static final Map<String, Gate> gates = new HashMap<>();
    private static Map<String, Integer> results = new HashMap<>();

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        WireCircuitCommand.class.getResourceAsStream("/signal_gates")));

        WireCircuitCommand wireCircuitCommand = new WireCircuitCommand();


        String line;
        while ((line = reader.readLine()) != null) {
            wireCircuitCommand.processCommand(line);
        }
        System.out.println(wireCircuitCommand.getOutputSignalValue("a"));
    }

    private int getOutputSignalValue(String a) {
        return gates.get(a).getOutputSignalValue();
    }

    private void processCommand(String line) {
        String[] signalTokens = line.split(" ");

        if (Character.isDigit(signalTokens[0].toCharArray()[0]) && signalTokens.length == 3) {
            Gate gate = new Gate(signalTokens[2]);
            if (!gates.containsKey(gate.gateName)) {
                gates.put(gate.gateName(), gate);
            } else {
                gate = gates.get(gate.gateName());
            }
            gate.addSourceGates(OperationType.SIMPLE_VALUE, new SimpleValueGate(valueOf(signalTokens[0])), new SimpleValueGate(valueOf(signalTokens[0])));
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
        Gate targetGate = new Gate(targetGateLetter);
        if (!gates.containsKey(targetGate.gateName)) {
            gates.put(targetGate.gateName(), targetGate);
        } else {
            targetGate = gates.get(targetGate.gateName());
        }

        Gate sourceGate = new Gate(sourceGateLetter);
        if (!gates.containsKey(sourceGate.gateName)) {
            gates.put(sourceGate.gateName(), sourceGate);
        } else {
            sourceGate = gates.get(sourceGate.gateName);
        }
        targetGate.addSourceGates(operationType, sourceGate, sourceGate);
    }

    private void formTargetGate(String[] signalTokens, OperationType operationType) {
        Gate targetGate = new Gate(signalTokens[4]);
        if (!gates.containsKey(targetGate.gateName)) {
            gates.put(targetGate.gateName(), targetGate);
        } else {
            targetGate = gates.get(targetGate.gateName());
        }

        Gate sourceGate1 = null;
        Gate sourceGate2 = null;

        if (!Character.isDigit(signalTokens[0].toCharArray()[0])) {

            sourceGate1 = new Gate(signalTokens[0]);
            if (!gates.containsKey(sourceGate1.gateName)) {
                gates.put(sourceGate1.gateName(), sourceGate1);
            } else {
                sourceGate1 = gates.get(sourceGate1.gateName);
            }
        } else {
            sourceGate1 = new SimpleValueGate(Integer.valueOf(signalTokens[0]));
        }

        if (!Character.isDigit(signalTokens[2].toCharArray()[0])) {
            sourceGate2 = new Gate(signalTokens[2]);
            if (!gates.containsKey(sourceGate2.gateName)) {
                gates.put(sourceGate2.gateName(), sourceGate2);
            } else {
                sourceGate2 = gates.get(sourceGate2.gateName);
            }
        } else {
            sourceGate2 = new SimpleValueGate(Integer.valueOf(signalTokens[2]));
        }

        targetGate.addSourceGates(operationType, sourceGate1, sourceGate2);
    }

    private enum OperationType {

        SIMPLE_VALUE(gate -> gate[0]),
        NOT(gate -> ~gate[0]),
        LSHIFT(gate -> gate[0] << gate[1]),
        RSHIFT(gate -> gate[0] >>> gate[1]),
        AND(gate -> gate[0] & gate[1]),
        OR(gate -> gate[0] | gate[1]);

        private final Function<Integer[], Integer> function;

        OperationType(Function<Integer[], Integer> function) {
            this.function = function;
        }

        public int execute(Gate left, Gate right) {
            return function.apply(new Integer[]{left.getOutputSignalValue(), right.getOutputSignalValue()}) & 0x0000FFFF;
        }
    }

    private static class Gate {

        private final String gateName;
        protected int outputSignal;
        protected OperationType sourceOperationType;
        protected Gate sourceGateLeft;
        protected Gate sourceGateRight;


        protected Gate(String gateName) {
            this.gateName = gateName;
        }

        public void addSourceGates(OperationType sourceOperationType, Gate sourceGateLeft, Gate sourceGateRight) {
            this.sourceOperationType = sourceOperationType;
            this.sourceGateLeft = sourceGateLeft;
            this.sourceGateRight = sourceGateRight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Gate that = (Gate) o;
            return Objects.equal(gateName, that.gateName);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(gateName);
        }

        public int getOutputSignalValue() {

            if (results.containsKey(this.gateName)) {
                return results.get(this.gateName);
            }
            int value = sourceOperationType.execute(sourceGateLeft, sourceGateRight);
            results.put(this.gateName, value);
            System.out.println(this.gateName + " " + value);
            return value;
        }

        public String gateName() {
            return gateName;
        }
    }

    private static class SimpleValueGate extends Gate {

        protected SimpleValueGate(int value) {
            super("");
            outputSignal = value;
        }

        public int getOutputSignalValue() {
            return outputSignal;
        }

    }
}
