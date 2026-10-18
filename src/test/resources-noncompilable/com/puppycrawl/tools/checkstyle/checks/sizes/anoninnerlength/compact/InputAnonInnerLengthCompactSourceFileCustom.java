/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnonInnerLength">
      <property name="max" value="2"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() {
    // violation below 'Anonymous inner class length is 3 lines (max allowed is 2).'
    Runnable overLimit = new Runnable() {
        public void run() { }
    };
    overLimit.run();
}

Runnable atLimit = new Runnable() { };
