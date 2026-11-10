/*
TextBlockGoogleStyleFormatting

*/

// non-compiled with javac: Compilable with Java25

// violation 3 lines below 'Opening quotes (""") of text-block must be on the new line.'
// violation 4 lines below 'Text-block quotes are not vertically aligned.'
String greet() {
    return """
            hello
        """;
}

void main() {
    System.out.println(greet());
}
