package com.puppycrawl.tools.checkstyle.api.abstractcheck;

/**
 * I'm a javadoc
 */
public class InputAbstractCheckTestFileContents {
    private int variable;

    public InputAbstractCheckTestFileContents(int variable) {
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
