/*
UnusedCatchParameterShouldBeUnnamed


*/

// non-compiled with javac: Compilable with Java25

Exception error;

void main() {
}

int parse(String text) {
    try {
        return Integer.parseInt(text);
    }
    catch (NumberFormatException e) { // violation 'Unused catch parameter 'e' should be unnamed.'
        return 0;
    }
}

int parseLogged(String text) {
    try {
        return Integer.parseInt(text);
    }
    catch (NumberFormatException e) {
        System.out.println(e.getMessage());
        return -1;
    }
}

int parseUnnamed(String text) {
    try {
        return Integer.parseInt(text);
    }
    catch (NumberFormatException _) {
        return 0;
    }
}

void record(String text) {
    try {
        Integer.parseInt(text);
    }
    catch (NumberFormatException error) {
        // violation above 'Unused catch parameter 'error' should be unnamed.'
        this.error = null;
    }
}
