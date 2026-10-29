/*
SeparatorWrap
option = (default)EOL
tokens = (default)DOT, COMMA


*/

// non-compiled with javac: Compilable with Java25

void checkWrap(String text) {
    boolean empty = text
            .isEmpty(); // violation ''.' should be on the previous line.'
    printTwo(1
            , 2); // violation '',' should be on the previous line'
    boolean alsoEmpty = text.
            isEmpty();
    printTwo(1,
            2);
    boolean inline = text.isEmpty();
    if (empty || alsoEmpty || inline) {
        System.out.println("done");
    }
}

void printTwo(int first, int second) {
    System.out.println(first + second);
}

void main() {
}
