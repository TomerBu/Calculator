package edu.tomerbu.calculator;

import java.util.function.BiFunction;
import java.util.function.DoubleFunction;
import java.util.function.Supplier;

public abstract class Operation {
    protected OperationType operationType;

    enum OperationType {
        NULLARY, UNARY, BINARY, CONSTANT, EQUALS;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public BinaryOperation asBinary() {
        return (BinaryOperation) this;
    }

    public UnaryOperation asUnary() {
        return (UnaryOperation) this;
    }

    public EqualsOperation asEquals() {
        return (EqualsOperation) this;
    }

    public NullaryOperation asNullary() {
        return (NullaryOperation) this;
    }

    public ConstantOperation asConstantOperation() {
        return (ConstantOperation) this;
    }
}

class ConstantOperation extends Operation {
    private Double constant;

    public ConstantOperation(Double constant) {
        this.constant = constant;
        this.operationType = OperationType.CONSTANT;
    }

    public Double getOperation() {
        return constant;
    }
}

class UnaryOperation extends Operation {
    private DoubleFunction<Double> unaryFunction;

    public UnaryOperation(DoubleFunction<Double> unaryFunction) {
        this.unaryFunction = unaryFunction;
        this.operationType = OperationType.UNARY;
    }

    public DoubleFunction<Double> getOperation() {
        return unaryFunction;
    }
}

class EqualsOperation extends Operation {

    public EqualsOperation() {
        this.operationType = OperationType.EQUALS;
    }
}

class NullaryOperation extends Operation {
    private Supplier<Double> supplierFunction;

    public NullaryOperation(Supplier<Double> supplierFunction) {
        this.supplierFunction = supplierFunction;
        this.operationType = OperationType.NULLARY;
    }

    public Supplier<Double> getOperation() {
        return supplierFunction;
    }
}

class BinaryOperation extends Operation {
    private BiFunction<Double, Double, Double> binaryFunction;

    public BinaryOperation(BiFunction<Double, Double, Double> binaryFunction) {
        this.binaryFunction = binaryFunction;
        this.operationType = OperationType.BINARY;
    }

    public BiFunction<Double, Double, Double> getOperation() {
        return binaryFunction;
    }
}