/*
ClassDataAbstractionCoupling
max = (default)7
excludedClasses = (default)ArrayIndexOutOfBoundsException, ArrayList, Boolean, Byte, \
                  Character, Class, Collection, Deprecated, Deque, Double, DoubleStream, \
                  EnumSet, Exception, Float, FunctionalInterface, HashMap, HashSet, \
                  IllegalArgumentException, IllegalStateException, IndexOutOfBoundsException, \
                  IntStream, Integer, LinkedHashMap, LinkedHashSet, LinkedList, List, Long, \
                  LongStream, Map, NullPointerException, Object, Optional, OptionalDouble, \
                  OptionalInt, OptionalLong, Override, Queue, RuntimeException, SafeVarargs, \
                  SecurityException, Set, Short, SortedMap, SortedSet, Stream, String, \
                  StringBuffer, StringBuilder, SuppressWarnings, Throwable, TreeMap, TreeSet, \
                  UnsupportedOperationException, Void, boolean, byte, char, double, float, \
                  int, long, short, var, void
excludeClassesRegexps = (default)^$
excludedPackages = (default)


*/

// non-compiled with javac: Compilable with Java25

// the implicit class has a coupling of 8, ok until
// https://github.com/checkstyle/checkstyle/issues/22568
Random random = new Random();
BitSet bits = new BitSet();
Vector<String> vector = new Vector<>();
Stack<String> stack = new Stack<>();
Hashtable<String, String> table = new Hashtable<>();
Properties properties = new Properties();
PriorityQueue<String> queue = new PriorityQueue<>();
ArrayDeque<String> deque = new ArrayDeque<>();
ArrayList<String> list = new ArrayList<>();
HashMap<String, String> map = new HashMap<>();

void main() {
}

class Nested {
    Random nestedRandom = new Random();
    BitSet nestedBits = new BitSet();
}

// violation below 'Class Data Abstraction Coupling is 8 (max allowed is 7)'
class Crowded {
    Random random = new Random();
    BitSet bits = new BitSet();
    Vector<String> vector = new Vector<>();
    Stack<String> stack = new Stack<>();
    Hashtable<String, String> table = new Hashtable<>();
    Properties properties = new Properties();
    PriorityQueue<String> queue = new PriorityQueue<>();
    ArrayDeque<String> deque = new ArrayDeque<>();
    ArrayList<String> list = new ArrayList<>();
}
