package vttp2023.batch3.ssf.frontcontroller.model;

import java.security.SecureRandom;
import java.util.Random;

public class Captcha {
    private Integer solution;
    private Integer inputSolution;
    private String query;

    private final Integer MIN = 1;
    private final Integer MAX = 50;

    public Captcha() {
        String[] q = generateQuery();
        this.solution = Integer.parseInt(q[0]);
        this.query = q[1];
    }

    public Integer getSolution() {
        return solution;
    }
    public void setSolution(Integer solution) {
        this.solution = solution;
    }
    public Integer getInputSolution() {
        return inputSolution;
    }
    public void setInputSolution(Integer inputSolution) {
        this.inputSolution = inputSolution;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String[] generateQuery() {
        String[] q = new String[2];
        Random rand = new SecureRandom();
        int a = rand.nextInt(MAX-MIN + 1) + MIN;
        int b = rand.nextInt(MAX-MIN + 1) + MIN;
        int oper = rand.nextInt(4);

        switch (oper) {
            case 0:
                q[0] = Integer.toString(a + b);
                q[1] = a + " + " + b;
                break;
            case 1:
                q[0] = Integer.toString(a - b);
                q[1] = a + " - " + b;
            case 2:
                q[0] = Long.toString(Math.round((double)a / b));
                q[1] = a + " / " + b;

            case 3:
                q[0] = Integer.toString(a * b);
                q[1] = a + " * " + b;
            default:
                break;
        }

        return q;
    }
}
