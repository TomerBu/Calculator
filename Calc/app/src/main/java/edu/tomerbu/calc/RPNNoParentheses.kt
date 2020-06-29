package edu.tomerbu.calc

import java.util.*

class RPNNoParentheses {

    fun toRPN(expression: String) {

        val s = Stack<Char>()
        var res = Stack<Char>()

        for (char in expression) {
            if (isOperand(char))
                res.push(char)
            else if (isOperator(char)) {
                //look for other operators in the stack having higher precedence
                while (s.isNotEmpty() && hasHigherPrecedence(s.peek(), char)) {
                    res.push(s.pop())
                }
                s.push(char)
            }
        }
        while (s.isNotEmpty()) {
            res.push(s.pop())
        }

        println(res)
    }

    private val operators = arrayOf('=', '+', '-', '*', '/');
    private val precedence = mapOf<Char, Int>(Pair('*', 12), Pair('/', 12), Pair('+', 11), Pair('-', 11))
    private fun hasHigherPrecedence(op1: Char, op2: Char): Boolean {
        val v1 = precedence[op1]
        val v2 = precedence[op2]

        return v1 ?: 0 > v2 ?: 0
    }

    private fun isOperand(char: Char): Boolean {
        return !isOperator(char)
    }

    private fun isOperator(char: Char): Boolean {
        return operators.contains(char);
    }
}