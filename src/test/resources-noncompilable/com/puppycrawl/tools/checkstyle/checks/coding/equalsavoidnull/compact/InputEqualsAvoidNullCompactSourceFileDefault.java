/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

// non-compiled with javac: Compilable with Java25

String name = "checkstyle";

void checkField() {
    name.equals("checkstyle"); // violation 'left .* of .* equals'
    name.equalsIgnoreCase("CHECKSTYLE"); // violation 'left .* of .* equalsIgnoreCase'
    "checkstyle".equals(name);
}

void checkLocal() {
    String local = "local";
    local.equals("value"); // violation 'left .* of .* equals'
    local.equals(local);
}

void checkParameter(String param) {
    param.equals("value"); // violation 'left .* of .* equals'
    param.equalsIgnoreCase("VALUE"); // violation 'left .* of .* equalsIgnoreCase'
}

void main() {
}
