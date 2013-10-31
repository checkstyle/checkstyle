package com.puppycrawl.tools.checkstyle.coding;

public class InputRequireThis2 {
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