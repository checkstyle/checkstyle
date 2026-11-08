/*
IllegalToken
tokens = (default)LABELED_STAT


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

int findFirstNegative(int[][] grid) {
    int found = 0;
    outer: // violation 'Using 'outer:' is not allowed.'
    for (int[] row : grid) {
        for (int value : row) {
            if (value < 0) {
                found = value;
                break outer;
            }
        }
    }
    return found;
}

int countPositive(int[] values) {
    int count = 0;
    loop: // violation 'Using 'loop:' is not allowed.'
    for (int value : values) {
        if (value <= 0) {
            continue loop;
        }
        count++;
    }
    return count;
}

int sum(int[] values) {
    int total = 0;
    for (int value : values) {
        total += value;
    }
    return total;
}
