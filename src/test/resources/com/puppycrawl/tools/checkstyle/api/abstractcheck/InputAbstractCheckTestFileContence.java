package com.puppycrawl.tools.checkstyle.api.abstractcheck;

/**
 * I'm a javadoc
 */
public class InputAbstractCheckTestFileContence {
    private int variable;

    public InputAbstractCheckTestFileContence(int variable) {
        this.variable = variable;
    }

    public int getVariable() {
        return variable;
    }

    public void setVariable(int variable) {
        this.variable = variable;
    }

    public int multiplyBy(int multiplier) {
        return variable * multiplier;
    }
}
