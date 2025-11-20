/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

// Java21
package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentCheck2 {
    record Rectangle(Object test1, Object test2) {}
    record ColoredPoint(Object test1, Object test2, Object test3) {}

    class Angle {
        public int unit;
    }

    class Triangle {
        public int point;
        public Angle angle = new Angle();
    }

    public void testAssignment(Object obj) {

        if (obj instanceof Triangle tg) {
            tg.point = 5;
            tg.angle.unit = 93;
        }

        if (obj instanceof Rectangle objValidator) {

            if (obj instanceof ColoredPoint) {
                Object formatedObj = objValidator;
            }
        }

        if (obj instanceof String message) {
            System.out.println(message);
        }
        else {
            record Message(Object content) {}
            Message message;
            Object result = obj;
            if (result instanceof Message) {
              message = (Message) result;
            }
        }

        if (new Angle().unit == 0 && obj instanceof Triangle parent) {
            while (parent != null) {
                Angle angle = new Angle();
                if (parent.angle instanceof Angle internal) {}

                parent = null;
                // violation above, "Assignment of pattern variable 'parent' is not allowed."
            }
        }
    }

    public static boolean bigEnoughTriang(Object s) {
        if (!(s instanceof Triangle r)) {
            return false;
        }
        r = null; // ok until #17203
        return true;
     }

     public class StringHolder {

         String string;

         void setString(Object object) {
             StringHolder stringHolder = new StringHolder();
             if (object instanceof String string) {
                 this.string = string; // ok because assignment to field, not pattern variable
                 stringHolder.string = string;
                 getObj().string = string;
                 {
                     string = "string";
                     // violation above, "Assignment of pattern variable 'string' is not allowed."
                 }
             }
         }

         StringHolder getObj() {
             return new StringHolder();
         }
     }
}
