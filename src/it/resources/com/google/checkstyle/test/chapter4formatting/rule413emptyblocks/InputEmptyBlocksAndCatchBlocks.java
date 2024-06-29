package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

class InputEmptyBlocksAndCatchBlocks
{
    static {}

    public void fooMethod()
    {
        InputEmptyBlocksAndCatchBlocks r = new InputEmptyBlocksAndCatchBlocks();
        int a = 1;
        if (a == 1) {} // violation 'Empty if block.'
        char[] s = {'1', '2'};
        int index = 2;
        if (doSideEffect() == 1) {} // violation 'Empty if block.'
        IO in = new IO();
        while ((r = in.read()) != null) {}
        for (; index < s.length && s[index] != 'x'; index++) {}
        if (a == 1) {} else {System.identityHashCode("a");}  // violation 'Empty if block.'
        do {} while(a == 1);
        switch (a) {} // violation 'Empty switch block.'
        int[] z = {};
    }

    public int doSideEffect()
    {
        return 1;
    }

    public void emptyMethod() {}
}

class IO
{
    public InputEmptyBlocksAndCatchBlocks read()
    {
        return new InputEmptyBlocksAndCatchBlocks();
    }
}
class Empty {}

interface EmptyImplement {}

class WithInner
{
    static {}

    public void emptyMethod() {}

    public int doSideEffect()
    {
        return 1;
    }

    class Inner
    {
        private void withEmpty()
        {
            InputEmptyBlocksAndCatchBlocks r = new InputEmptyBlocksAndCatchBlocks();
            int a = 1;
            if (a == 1) {} // violation 'Empty if block.'
            char[] s = {'1', '2'};
            int index = 2;
            if (doSideEffect() == 1) {} // violation 'Empty if block.'
            IO in = new IO();
            while ((r = in.read()) != null) {}
            for (; index < s.length && s[index] != 'x'; index++) {}
            if (a == 1) {} else {System.identityHashCode("a");} // violation 'Empty if block.'
            do {} while(a == 1);
            switch (a) {} // violation 'Empty switch block.'
            int[] z = {};
        }
    }
}

class WithAnon
{
    interface AnonWithEmpty {
        public void fooEmpty();
    }

    void method()
    {
        AnonWithEmpty foo = new AnonWithEmpty() {

            public void emptyMethod() {}

            public void fooEmpty() {
                InputEmptyBlocksAndCatchBlocks r = new InputEmptyBlocksAndCatchBlocks();
                int a = 1;
                if (a == 1) {} // violation 'Empty if block.'
                char[] s = {'1', '2'};
                int index = 2;
                if (doSideEffect() == 1) {} // violation 'Empty if block.'
                IO in = new IO();
                while ((r = in.read()) != null) {}
                for (; index < s.length && s[index] != 'x'; index++) {}
                if (a == 1) {} else {System.identityHashCode("a");} // violation 'Empty if block.'
                do {} while(a == 1);
                switch (a) {} // violation 'Empty switch block.'
                int[] z = {};
            }

            public int doSideEffect()
            {
                return 1;
            }
        };
    }
}

class NewClass {

    void foo() {
        int a = 1;

        if (a == 1) {
            System.identityHashCode("a");
        } else {} // violation 'Empty else block.'

        if (a == 1) {
            System.identityHashCode("a");
        } else {/*ignore*/}

        if (a == 1) {
            /*ignore*/
        } else {
            System.identityHashCode("a");
        }

        if (a == 1) {
            System.identityHashCode("a");
        } else if (a != 1) {
            /*ignore*/
        } else {
            /*ignore*/
        }

        if (a == 1) {
            /*ignore*/
        } else if (a != 1) {
            System.identityHashCode("a");
        } else {
            /*ignore*/
        }

        if (a == 1) {
            /*ignore*/
        } else if (a != 1) {
            /*ignore*/
        } else {
            System.identityHashCode("a");
        }

        if (a == 1) {
            /*ignore*/
        } else if (a != 1) {
            /*ignore*/
        } else {
            /*ignore*/
        }

        if (a == 1) {
            /*ignore*/
        } else if (a != 1) {} // violation 'Empty if block.'
        else {} // violation 'Empty else block.'

        if (a == 1) {} // violation 'Empty if block.'
        else if (a != 1) {
            /*ignore*/
        }
        else {} // violation 'Empty else block.'

        if (a == 1) {} // violation 'Empty if block.'
        else if (a != 1) {} // violation 'Empty if block.'
        else {
            /*ignore*/
        }
    }

    class NewInner {

        void foo() {
            int a = 1;

            if (a == 1) {
                System.identityHashCode("a");
            } else {} // violation 'Empty else block.'

            if (a == 1) {
                System.identityHashCode("a");
            } else {/*ignore*/}

            if (a == 1) {
                /*ignore*/
            } else {
                System.identityHashCode("a");
            }

            if (a == 1) {
                System.identityHashCode("a");
            } else if (a != 1) {
                /*ignore*/
            } else {
                /*ignore*/
            }

            if (a == 1) {
                /*ignore*/
            } else if (a != 1) {
                System.identityHashCode("a");
            } else {
                /*ignore*/
            }

            if (a == 1) {
                /*ignore*/
            } else if (a != 1) {
                /*ignore*/
            } else {
                System.identityHashCode("a");
            }

            if (a == 1) {
                /*ignore*/
            } else if (a != 1) {
                /*ignore*/
            } else {
                /*ignore*/
            }

            if (a == 1) {
                /*ignore*/
            } else if (a != 1) {} // violation 'Empty if block.'
            else {} // violation 'Empty else block.'

            if (a == 1) {} // violation 'Empty if block.'
            else if (a != 1) {
                /*ignore*/
            }
            else {} // violation 'Empty else block.'

            if (a == 1) {} // violation 'Empty if block.'
            else if (a != 1) {} // violation 'Empty if block.'
            else {
                /*ignore*/
            }
        }

        NewInner anon = new NewInner() {

            void foo() {
                int a = 1;

                if (a == 1) {
                    System.identityHashCode("a");
                } else {} // violation 'Empty else block.'

                if (a == 1) {
                    System.identityHashCode("a");
                } else {/*ignore*/}

                if (a == 1) {
                    /*ignore*/
                } else {
                    System.identityHashCode("a");
                }

                if (a == 1) {
                    System.identityHashCode("a");
                } else if (a != 1) {
                    /*ignore*/
                } else {
                    /*ignore*/
                }

                if (a == 1) {
                    /*ignore*/
                } else if (a != 1) {
                    System.identityHashCode("a");
                } else {
                    /*ignore*/
                }

                if (a == 1) {
                    /*ignore*/
                } else if (a != 1) {
                    /*ignore*/
                } else {
                    System.identityHashCode("a");
                }

                if (a == 1) {
                    /*ignore*/
                } else if (a != 1) {
                    /*ignore*/
                } else {
                    /*ignore*/
                }

                if (a == 1) {
                    /*ignore*/
                } else if (a != 1) {} // violation 'Empty if block.'
                else {} // violation 'Empty else block.'

                if (a == 1) {} // violation 'Empty if block.'
                else if (a != 1) {
                    /*ignore*/
                }
                else {} // violation 'Empty else block.'

                if (a == 1) {} // violation 'Empty if block.'
                else if (a != 1) {} // violation 'Empty if block.'
                else {
                    /*ignore*/
                }
            }
        };
    }
}

class Example {

    void doNothing() {}

    void doNothingElse() {

    }
}

class TestingEmptyBlockCatch {
    boolean flag;
    void doSm() {}
    void foo() {
        try {
            if (!flag) {
                doSm();
            }
        } catch (Exception e) { /* ignore */ }
        finally {/* ignore */}
    }

    void foo2() {
        try {
            if (!flag) {
                doSm();
            }
        } catch (Exception e) {} // violation 'Empty catch block.'
        finally {} // violation 'Empty finally block.'
    }

    class Inner {
        boolean flag;
        void doSm() {}
        void foo() {
            try {
                if (!flag) {
                    doSm();
                }
            } catch (Exception e) { /* ignore */ }
            finally {/* ignore */}
        }

        void foo2() {
            try {
                if (!flag) {
                    doSm();
                }
            } catch (Exception e) {} // violation 'Empty catch block.'
            finally {} // violation 'Empty finally block.'
        }
    }

    Inner anon = new Inner(){
        boolean flag;
        void doSm() {}
        void foo() {
            try {
                if (!flag) {
                    doSm();
                }
            } catch (Exception e) { /* ignore */ }
            finally {/* ignore */}
        }

        void foo2() {
            try {
                if (!flag) {
                    doSm();
                }
            } catch (Exception e) {} // violation 'Empty catch block.'
            finally {} // violation 'Empty finally block.'
        }
    };
}
