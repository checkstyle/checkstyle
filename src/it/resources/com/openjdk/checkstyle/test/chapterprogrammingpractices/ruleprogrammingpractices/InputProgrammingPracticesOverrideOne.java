package com.openjdk.checkstyle.test.chapterprogrammingpractices.ruleprogrammingpractices;

// violation first line 'Header mismatch*'

public class InputProgrammingPracticesOverrideOne {

    class Child extends Parent {
        /** {@inheritDoc} */
        @Override
        public void test1() {}

        /** {@inheritDoc} */
        public void test2() {}
        // violation above """Must include @java.lang.Override annotation when
        //  '@inheritDoc' Javadoc tag exists."""

        /** {@inheritDoc} */
        private void test3() {}
        // violation above '{@inheritDoc} tag is not valid at this location.'

        /** {@inheritDoc} */
        public void test4() {}
        // violation above """Must include @java.lang.Override annotation when
        //  '@inheritDoc' Javadoc tag exists."""

        /** {@inheritDoc} */
        public boolean equals(Object o) {
            // violation above """Must include @java.lang.Override annotation when
            //  '@inheritDoc' Javadoc tag exists."""
            return o == this;
        }
    }

    class Parent {
        public void test1() {
        }

        public void test2() {
        }

        private void test3() {
        }

        public void test4() {
        }
    }
}
