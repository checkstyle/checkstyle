package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

/*
* Config = default
*/
public class InputRedundantThisAnonymousObjects {

    int bar;

    interface Anon {
        public void foo();
    }

    void methodOne() {
        Anon foo = new Anon() {

            public void foo() {
                 this.doSideEffect(); // violation
            }

            public void doSideEffect() {}
        };

        this.bar = 0;

        new Anon() {

            public void foo() {
                new Anon() {
                    private int anonMember;
                    private String name;

                    public void foo() {
                        int anonMember = 0;
                        this.anonMember++; // no violation
                        this.name = "something"; // violation
                    }
                };
            }
        };
    }

    public void methodTwo() {

        this.methodFour(new Anon() { // violation
            private int member;

            public void foo() {
                this.member++; // violation
            }
        });
    }

    public void methodThree() {

        methodFour(new Anon() {
            private int member;

            public void foo() {
                member++;
            }
        });
    }

    public void methodFour(Anon aw) { }
}
