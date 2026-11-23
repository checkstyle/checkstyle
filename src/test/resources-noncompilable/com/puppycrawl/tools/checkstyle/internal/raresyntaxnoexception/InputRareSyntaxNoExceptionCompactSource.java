// non-compiled with javac: Compilable with Java25

int count = 42;

void main() {
    int total = count;
    for (int index = 0; index < 3; index++) {
        total += index;
    }
    try {
        if (total < 0) {
            throw new IllegalStateException("negative");
        }
        System.out.println("total " + total);
    }
    catch (IllegalStateException ex) {
        throw new IllegalArgumentException("failed", ex);
    }
    String text = switch (total) {
        case 42 -> "answer";
        case 43 -> "close";
        default -> "other";
    };
    Runnable task = () -> System.out.println(text);
    task.run();
}
