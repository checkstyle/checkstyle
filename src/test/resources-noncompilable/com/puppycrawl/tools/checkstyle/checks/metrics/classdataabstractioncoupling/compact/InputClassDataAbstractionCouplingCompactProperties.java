/*
ClassDataAbstractionCoupling
max = 1
excludedClasses = Random
excludeClassesRegexps = ^Bit.*
excludedPackages = java.util.concurrent


*/

// non-compiled with javac: Compilable with Java25

// the implicit class has a coupling of 2, ok until
// https://github.com/checkstyle/checkstyle/issues/22568
Random random = new Random();
BitSet bits = new BitSet();
Vector<String> vector = new Vector<>();
Stack<String> stack = new Stack<>();
Object map = new java.util.concurrent.ConcurrentHashMap<String, String>();

void main() {
}

class Nested {
    Hashtable<String, String> table = new Hashtable<>();
    BitSet nestedBits = new BitSet();
}

// violation below 'Class Data Abstraction Coupling is 2 (max allowed is 1)'
record Point(int x) {
    static Properties properties = new Properties();
    static PriorityQueue<String> queue = new PriorityQueue<>();
    static Random pointRandom = new Random();
}
