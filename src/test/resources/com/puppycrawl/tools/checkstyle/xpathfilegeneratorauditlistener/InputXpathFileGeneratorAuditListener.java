package com.puppycrawl.tools.checkstyle.xpathfilegeneratorauditlistener;

public class InputXpathFileGeneratorAuditListener {

    private boolean isValid = true;

    public String sayHello(String name) {
        return "Hello, " + name;
    }

    public int add(int a, int b) {
        return a + b;
    }

    public void sort(int a[]) {
        for (int i = 0;i < a.length; i++) {
            for (int j = i+1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    int buf = a[i];
                    a[i] = a[j];
                    a[j] = buf;
                }
            }
        }
    }
}
