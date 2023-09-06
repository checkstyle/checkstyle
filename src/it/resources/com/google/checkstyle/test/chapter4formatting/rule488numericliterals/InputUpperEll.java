package com.google.checkstyle.test.chapter4formatting.rule488numericliterals;

class InputUpperEll
{
    /** test **/
    private final long IGNORE = 666l + 666L; //warn

    private String notWarn = "666l"; 
    private long foo()
    {
        processUpperEll(66l); //warn
        processUpperEll(66L); 
        processUpperEll("s", 66l); //warn
        processUpperEll("s", 66L); 
        return 666l + 666L; //warn
    }

    private void processUpperEll(long aLong) {
        long bad = (4+5*7^66l/7+890) //warn
                & (88l + 78 * 4); //warn
        long good = (4+5*7^66L/7+890) & (88L + 78 * 4); 
        long[] array = {
            66l, //warn
            66L, 
        };
    }

    private void processUpperEll(String s, long l) {}

    class Inner {
        /** test **/
        private static final long IGNORE = 666l + 666L; //warn

        private static final String notWarn = "666l"; 
        private long foo()
        {
            processUpperEll(66l); //warn
            processUpperEll(66L); 
            processUpperEll("s", 66l); //warn
            processUpperEll("s", 66L); 
            return 666l + 666L; //warn
        }

        private void processUpperEll(long aLong)
        {
            long bad = (4+5*7^66l/7+890) //warn
                    & (88l + 78 * 4); //warn
            long good = (4+5*7^66L/7+890) & (88L + 78 * 4); 
        }
        private void processUpperEll(String s, long l) {
            long[] array = {
                    66l, //warn
                    66L, 
                };
        }

        void fooMethod()
        {
            Foo foo = new Foo() {
                /** test **/
                private final long IGNORE = 666l + 666L; //warn

                private String notWarn = "666l"; 
                private long foo()
                {
                    processUpperEll(66l); //warn
                    processUpperEll(66L); 
                    processUpperEll("s", 66l); //warn
                    processUpperEll("s", 66L); 
                    return 666l + 666L; //warn
                }

                private void processUpperEll(long aLong) {
                    long bad = (4+5*7^66l/7+890) //warn
                            & (88l + 78 * 4); //warn
                    long good = (4+5*7^66L/7+890) & (88L + 78 * 4); 
                    long[] array = {
                        66l, //warn
                        66L, 
                    };
                }

                private void processUpperEll (String s, long aLong) {}
            };
        }
    }

    class Foo {}

    interface Long {
        public static final long IGNORE = 666l + 666L; //warn
        public static final String notWarn = "666l"; 
        long bad = (4+5*7^66l/7+890) //warn
                & (88l + 78 * 4); //warn
        long good = (4+5*7^66L/7+890) & (88L + 78 * 4); 
    }
}



