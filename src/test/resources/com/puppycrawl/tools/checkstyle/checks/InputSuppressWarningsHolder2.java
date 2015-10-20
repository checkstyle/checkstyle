package com.puppycrawl.tools.checkstyle.checks;

public class InputSuppressWarningsHolder2
{
    public static class MyResource implements AutoCloseable {
        @Override
        public void close() throws Exception { }
    }

    public static void main(String[] args) throws Exception {
        try (@SuppressWarnings("all") final MyResource resource = new MyResource()) { }
    }
}
