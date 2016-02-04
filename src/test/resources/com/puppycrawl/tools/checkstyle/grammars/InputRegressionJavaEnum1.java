package com.puppycrawl.tools.checkstyle.grammars;

public enum InputRegressionJavaEnum1 {
    E1(), E2 {
        class anonymous {}
        private static final int f = 1;

        @Override
        public void override() {};
        public <T> void m1() {};
        public void m2() throws Exception { throw new Exception(); };
        public native void m3() throws Exception;

        {;}
    }
    ;
    
    public void override() {}
}
enum e1 {
    E1(),;
}
enum e2 {
    E1();;
}