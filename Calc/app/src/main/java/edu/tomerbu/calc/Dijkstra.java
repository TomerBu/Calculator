package edu.tomerbu.calc;

import android.widget.SeekBar;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Dijkstra {

    public void dd(SeekBar.OnSeekBarChangeListener seekBarChangeListener){

    }

    public void aa(){
        dd((P) (seekBar, progress, fromUser) -> {

        });
    }
    public void demo() {
        Stack<String> ops = new Stack<>();
        Stack<Double> vals = new Stack<>();

        String[] tokens = "(((3+4)*5)+3)".split("");
        for (String token : tokens) {
            //noinspection StatementWithEmptyBody
            if (isOpening(token)) {
                //Ignore
            } else if (isOperator(token)) {
                ops.push(token);
            } else if (isClosing(token)) {
                String op = ops.pop();
                double v = vals.pop();

                switch (op) {
                    case "+":
                        v = vals.pop() + v;
                        break;
                    case "-":
                        v = vals.pop() - v;
                        break;
                    case "*":
                        v = vals.pop() * v;
                        break;
                    case "/":
                        v = vals.pop() / v;
                        break;
                    case "sqrt":
                        v = Math.sqrt(v);
                        break;
                }
                vals.push(v);
            } else {
                //number:
                if (token.length() > 0)
                    vals.push(Double.parseDouble(token));
            }
        }
        //show the result
        System.out.println(vals.pop());
    }

    private List<String> operators = Arrays.asList("+", "-", "*", "/", "sqrt", "^");
    private List<String> openingBrackets = Arrays.asList("(", "[", "{");
    private List<String> closingBrackets = Arrays.asList(")", "]", "}");


    private boolean isOpening(String token) {
        return openingBrackets.contains(token);
    }

    private boolean isClosing(String token) {
        return closingBrackets.contains(token);
    }

    private boolean isOperator(String token) {
        return operators.contains(token);
    }
}
