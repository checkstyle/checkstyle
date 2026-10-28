/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = true


*/

// non-compiled with javac: Compilable with Java25

String name = "checkstyle";

void checkField() {
    name.equals("checkstyle"); // violation 'left .* of .* equals'
    name.equalsIgnoreCase("CHECKSTYLE");
}

void checkLocal() {
    String local = "local";
    local.equalsIgnoreCase("VALUE");
    local.equals("value"); // violation 'left .* of .* equals'
}

void main() {
}
