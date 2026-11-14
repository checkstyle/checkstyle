/*xml
<module name="Checker">
  <module name="SuppressWarningsFilter"/>
  <module name="TreeWalker">
    <module name="SuppressWarningsHolder"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.HiddenFieldCheck"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;

public class InputSuppressWarningsHolder9 {
    private int count;
    private int size;

    public void setCount(@SuppressWarnings("checkstyle:hiddenfield") int count) {
        this.count = count;
    }

    // violation below ''size' hides a field.'
    public void setSize(int size) {
        this.size = size;
    }

    public InputSuppressWarningsHolder9(@SuppressWarnings("checkstyle:hiddenfield") int count) {
        this.count = count;
    }

    // violation below ''count' hides a field.'
    public void setAnotherCount(int count) {
        this.count = count;
    }

    public void setBoth(@SuppressWarnings("checkstyle:hiddenfield") int count,
                        int size) { // violation ''size' hides a field.'
        this.count = count;
        this.size = size;
    }

    record RecordTest(int x, @SuppressWarnings("checkstyle:hiddenfield") int count) {
    }

    // violation below ''count' hides a field.'
    public void setThirdCount(int count) {
        this.count = count;
    }
}
