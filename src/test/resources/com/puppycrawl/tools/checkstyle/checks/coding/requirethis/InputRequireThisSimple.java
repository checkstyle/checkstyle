package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisSimple {
	private final int number = 1;

    public int check() {
        int sum = number;
        sum += other();
        return sum;
    }

    private int other() {
    	return 0;
    }
}
