package com.puppycrawl.tools.checkstyle.checks.coding;

public class BadCodecheck {
    public static void main(String[] args) {
        int unused = 41; // IntelliJ will flag this as an "unused variable"
    }
}
