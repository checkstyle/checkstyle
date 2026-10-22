/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnonInnerLength"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() {
    Runnable ok = new Runnable() {
        @Override
        public void run() {
            System.out.println("ok");
        }
    };
    ok.run();
}

// violation below 'Anonymous inner class length is 21 lines (max allowed is 20).'
Runnable overLimit = new Runnable() {
    @Override
    public void run() {
        System.out.println("1");
        System.out.println("2");
        System.out.println("3");
        System.out.println("4");
        System.out.println("5");
        System.out.println("6");
        System.out.println("7");
        System.out.println("8");
        System.out.println("9");
        System.out.println("10");
        System.out.println("11");
        System.out.println("12");
        System.out.println("13");
        System.out.println("14");
        System.out.println("15");
        System.out.println("16");
    }
};
