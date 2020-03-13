package org.checkstyle.suppressionxpathfilter.suppresswarningsholder;

public class SuppressionXpathRegressionSuppressWarningsHolderOne {
    public static class MyResource implements AutoCloseable {
        @Override
        public void close() throws Exception { }
    }

    public static void main(String[] args) throws Exception {
        try (@SuppressWarnings("all") final MyResource resource = new MyResource()) { } // warn
        try (@MyAnnotation("all") final MyResource resource = new MyResource()) { }
    }
}
@interface MyAnnotation {
    String[] value();
}

