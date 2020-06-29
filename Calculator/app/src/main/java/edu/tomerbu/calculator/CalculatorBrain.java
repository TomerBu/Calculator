package edu.tomerbu.calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.DoubleFunction;

public class CalculatorBrain {
    private boolean resultIsPending = false;

    public boolean isResultIsPending() {
        return resultIsPending;
    }

    private Double accumulator = null;
    private String accumulatorDisplay = null;
    //    private Map<String, Double> operations = new HashMap<>();
    private Map<String, Operation> operations = new HashMap<>();

    public CalculatorBrain() {
//        operations.put("π", Math.PI);
//        operations.put("e", Math.E);

        operations.put("π", new ConstantOperation(Math.PI));
        operations.put("e", new ConstantOperation(Math.E));

        operations.put("+", new BinaryOperation(Double::sum));
        operations.put("-", new BinaryOperation((a, b) -> a - b));
        operations.put("÷", new BinaryOperation((a, b) -> a / b));
        operations.put("x", new BinaryOperation((a, b) -> a * b));

        operations.put("√", new UnaryOperation(Math::sqrt));
        operations.put("%", new UnaryOperation(a -> a / 100));
        operations.put("cos", new UnaryOperation(Math::cos));
        operations.put("+/-", new UnaryOperation(a -> -a));
        operations.put("=", new EqualsOperation());
    }

    public void performOperation(String symbol) {
        //if the operation was a constant:
        Operation operation = operations.get(symbol);
        if (operation == null) return;
        switch (operation.getOperationType()) {
            case CONSTANT:
                accumulator = operation.asConstantOperation().getOperation();
                accumulatorDisplay = symbol;
                break;
            case UNARY: {
                DoubleFunction<Double> func = operation.asUnary().getOperation();
                if (accumulator != null) {
                    accumulator = func.apply(accumulator);


                    String description;
                    if (resultIsPending()) {
                        // Unary operations execute on the current operand. If there
                        // is a pending operation the description of the pending
                        // operation needs to be included in the description. Include the
                        // pending operation description before the unary operation
                        // description and clear the pending operation description since
                        // it has been accounted for here.
                        //
                        //description = pendingBinaryOperation.getDescription() + "\(symbol)(\(accumulator.description))"
                        description = "";
                        //pendingBinaryOperation!.description = ""
                    } else {
                        description = symbol + accumulatorDisplay;
                    }
                    accumulatorDisplay = description;
                }
                break;
            }
            case BINARY: {
                BiFunction<Double, Double, Double> func = operation.asBinary().getOperation();
                pendingBinaryOperation = new PendingBinaryOperation(accumulator, func);
                accumulator = null;
                break;
            }
            case EQUALS:
                performPendingBinaryOperation();
                break;
            case NULLARY:
                break;
        }
    }

    private void performPendingBinaryOperation() {
        if (pendingBinaryOperation != null && accumulator != null) {
            accumulator = pendingBinaryOperation.performOperationWith(accumulator);
            pendingBinaryOperation = null;
        }
    }

    private PendingBinaryOperation pendingBinaryOperation;

    private boolean resultIsPending() {
        return pendingBinaryOperation != null;
    }

    public String getResultDescription() {
        return accumulatorDisplay;
    }

    //used by the controller
    public void setOperand(Double displayValue) {
        this.accumulator = displayValue;
    }

    private static class PendingBinaryOperation {
        private final Double firstOperand;
        private final BiFunction<Double, Double, Double> function;

        public PendingBinaryOperation(Double firstOperand, BiFunction<Double, Double, Double> function) {
            this.firstOperand = firstOperand;
            this.function = function;
        }

        public double performOperationWith(Double secondOperand) {
            return function.apply(firstOperand, secondOperand);
        }
    }

    public Double getResult() {
        return accumulator;
    }
}
