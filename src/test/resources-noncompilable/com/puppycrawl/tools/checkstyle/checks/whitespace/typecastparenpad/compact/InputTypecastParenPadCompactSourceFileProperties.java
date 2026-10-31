/*
TypecastParenPad
option = space


*/

// non-compiled with javac: Compilable with Java25

void checkTypecasts(Object obj) {
    String valid = ( String ) obj;
    String noFollowed = (String ) obj; // violation ''(' is not followed by whitespace.'
    String noPreceded = ( String) obj; // violation '')' is not preceded with whitespace.'
    String neither = (String) obj;
    // 2 violations above:
    // ''(' is not followed by whitespace.'
    // '')' is not preceded with whitespace.'
    String multiline = ( String )
        obj;
    System.out.println(valid + noFollowed + noPreceded + neither + multiline);
}

void main() {
}

