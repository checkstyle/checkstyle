// non-compiled with javac: Compilable with Java25
import java.util.List;
import java.util.ArrayList;

private static final int VALUE = 1;

int a = 1, b = 2;

final int[] VALUES = {1, 2};

int[][] matrix = {{1, 2}, {3, 4}};

List<String> items = new ArrayList<>();

@Deprecated
String oldField = "x";

static void run() {}

@Deprecated
void oldMethod() {}

public static void main(String[] args) {}

int sum(int x, int y) {
    return x + y;
}

<T> T identity(T value) {
    return value;
}

void format(String pattern, Object... args) {}

void readFile(final String path) throws RuntimeException {}

synchronized void lock() {}
