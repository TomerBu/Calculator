package edu.tomerbu.calc

import java.util.*

class RPNWithParentheses {
    private val operators = arrayOf('=', '+', '-', '*', '/');
    private val precedence =
        mapOf<Char, Int>(Pair('*', 12), Pair('/', 12), Pair('+', 11), Pair('-', 11))

    public fun eval(expression: String): Int? {
        val rpn = toRPN(expression)
        println(rpn)
        return eval(rpn)
    }

    private fun eval(rpn: Iterable<Char>): Int? {
        val stack = Stack<Int>()

        for (token in rpn) {
            when (token) {
                '+' -> {
                    val rightOperand = stack.pop()
                    val leftOperand = stack.pop()
                    stack.push(leftOperand + rightOperand)
                }
                '-' -> {
                    val rightOperand = stack.pop()
                    val leftOperand = stack.pop()
                    stack.push(leftOperand - rightOperand)
                }
                '*' -> {
                    val rightOperand = stack.pop()
                    val leftOperand = stack.pop()
                    stack.push(leftOperand * rightOperand)
                }
                '/' -> {
                    val rightOperand = stack.pop()
                    val leftOperand = stack.pop()
                    stack.push(leftOperand / rightOperand)
                }
                else -> {
                    stack.push(token.toString().toInt())
                }
            }
        }
        return stack.pop()
    }

    private fun toRPN(expression: String): Stack<Char> {
        val s = Stack<Char>()
        val res = Stack<Char>()

        for (char in expression) {
            if (isOperand(char))
                res.push(char)
            else if (isOperator(char)) {
                //look for other operators in the stack having higher precedence
                while (s.isNotEmpty() &&
                    hasHigherPrecedence(s.peek(), char) &&
                    !isOpening(s.peek())
                ) {
                    res.push(s.pop())
                }
                s.push(char)
            } else if (isOpening(char)) {
                s.push(char)
            } else if (isClosing(char)) {
                while (s.isNotEmpty() && !isOpening(s.peek())) {
                    res.push(s.pop())
                }
                s.pop() // pop the opening parentheses
            }
        }
        while (s.isNotEmpty()) {
            res.push(s.pop())
        }

        return res
    }

    private fun hasHigherPrecedence(op1: Char, op2: Char): Boolean {
        val v1 = precedence[op1]
        val v2 = precedence[op2]

        return v1 ?: 0 > v2 ?: 0
    }

    private fun isOpening(char: Char): Boolean {
        val r = char == '('
        return r
    }

    private fun isClosing(char: Char): Boolean {
        return char == ')'
    }

    private fun isOperand(char: Char): Boolean {
        return Character.isDigit(char)
    }

    private fun isOperator(char: Char): Boolean {
        return operators.contains(char);
    }
}