/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidDoubleBraceInitialization"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

List<String> values = new ArrayList<String>() { // violation 'Avoid double brace initialization.'
    {
        add("field");
    }
};

void main() {
    // violation below 'Avoid double brace initialization.'
    List<String> local = new ArrayList<String>() {{ add("local"); }};
    List<String> regular = new ArrayList<>();
    regular.add("regular");
    Runnable action = new Runnable() {
        @Override
        public void run() {
            regular.addAll(local);
        }
    };
    action.run();
}

List<String> createValues() {
    return new ArrayList<String>() { // violation 'Avoid double brace initialization.'
        {
            add("returned");
        }
    };
}

Supplier<List<String>> supplier = () -> {
    return new ArrayList<String>() { // violation 'Avoid double brace initialization.'
        {
            add("lambda");
        }
    };
};

class Helper {
    List<String> createValues() {
        return new ArrayList<String>() { // violation 'Avoid double brace initialization.'
            {
                add("nested");
            }
        };
    }
}
