/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="HiddenField">
      <property name="ignoreFormat" value="^ignored"/>
      <property name="ignoreConstructorParameter" value="true"/>
      <property name="ignoreSetter" value="true"/>
      <property name="setterCanReturnItsClass" value="true"/>
      <property name="ignoreAbstractMethods" value="true"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

String name = "default";
int value;
int ignoredCount;

void main() { }

void process(String name, int ignoredCount) { } // violation ''name' hides a field'

void setName(String name) {
    this.name = name;
}

int setValue(int value) { // violation ''value' hides a field'
    return value;
}

class Builder {
    Builder(String name) { }

    Builder setName(String name) {
        return this;
    }

    Object setValue(int value) { // violation ''value' hides a field'
        return this;
    }
}

abstract class Shape {
    abstract void resize(String name);

    void draw(String name) { } // violation ''name' hides a field'
}
