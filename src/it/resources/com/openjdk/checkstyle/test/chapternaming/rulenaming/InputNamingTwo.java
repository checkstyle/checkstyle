package com.openjdk.checkstyle.test.chapternaming.rulenaming;

// violation first line 'Header mismatch*'

public class InputNamingTwo {
    private int hidden = 0;

    public InputNamingTwo() {
        int hidden = 0; // violation, ''hidden' hides a field'
    }

    public InputNamingTwo(int hidden) {
    }

    public void shadow() {
        int hidden = 0; // violation, ''hidden' hides a field'
    }

    public void shadowFor() {
        for (int hidden = 0; hidden < 1; hidden++) {
        // violation above, ''hidden' hides a field'
        }
    }

    public void shadowParam(int hidden) {
        // violation above, ''hidden' hides a field'
    }

    public class Inner {
        private int innerHidden = 0;

        public Inner() {
            int innerHidden = 0; // violation, ''innerHidden' hides a field'
        }

        public Inner(int innerHidden) {
        }

        private void innerShadow() {
            int innerHidden = 0; // violation, ''innerHidden' hides a field'
            int hidden = 0; // violation, ''hidden' hides a field'
        }

        private void innerShadowFor() {
            for (int innerHidden = 0; innerHidden < 1; innerHidden++) {
            // violation above, ''innerHidden' hides a field'
            }
            for (int hidden = 0; hidden < 1; hidden++) { // violation, ''hidden' hides a field'
            }
        }

        private void shadowParam(int innerHidden, int hidden) {
            // 2 violations above:
            // ''innerHidden' hides a field'
            // ''hidden' hides a field'
        }

        {
            int innerHidden = 0; // violation, ''innerHidden' hides a field'
            int hidden = 0; // violation, ''hidden' hides a field'
        }
    }

    {
        int hidden = 0; // violation, ''hidden' hides a field'
    }
}
