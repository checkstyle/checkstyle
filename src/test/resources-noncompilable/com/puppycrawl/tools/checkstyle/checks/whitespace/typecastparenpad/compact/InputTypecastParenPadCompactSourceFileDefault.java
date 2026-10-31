/*
TypecastParenPad
option = (default)nospace


*/

// non-compiled with javac: Compilable with Java25

void checkTypecasts(Object obj) {
    String valid = (String) obj;
    String followed = ( String) obj; // violation ''(' is followed by whitespace.'
    String preceded = (String ) obj; // violation '')' is preceded with whitespace.'
    String both = ( String ) obj;
    // 2 violations above:
    // ''(' is followed by whitespace.'
    // '')' is preceded with whitespace.'
    String multiline = (String)
        obj;
    System.out.println(valid + followed + preceded + both + multiline);
}

void main() {
}

