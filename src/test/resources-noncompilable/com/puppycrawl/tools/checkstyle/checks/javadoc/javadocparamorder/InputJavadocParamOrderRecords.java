//non-compiled with javac: Compilable with Java15
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

import java.util.List;
import org.w3c.dom.Node;

/**
 * Config: default
 * Some Javadoc.
 *
 * @since something
 */
class InputJavadocParamOrderRecords {

    /**
     * Test case for record.
     *
     * @param name Parameter name desc.         // ok
     * @param <address> Parameter address desc. // ok, param does not exists
     */
    record Person(String name, String address) {
        /**
         * Test case for constructor.
         *
         * @param name Parameter name desc.  // ok
         */
        Person(String name) {
            this(name, "Unknown");
        }
    }

    /**
     * Description.
     *
     * @param brand Parameter brand desc.               // ok
     * @param licensePlate Parameter licensePlate desc. // ok
     */
    record Vehicle(String brand, String licensePlate) {

        /**
         * Description.
         *
         * @param <brand> Parameter brand desc. // ok, param does not exists
         */
        Vehicle(String brand) {
            this(brand, null);
        }
    }

    /**
     * Description.
     *
     * @param extra1 Parameter node desc.   // ok, param does not exists
     * @param errors Parameter errors desc. // ok
     * @param extra2 Parameter node desc.   // ok, param does not exists
     * @param errors Parameter errors desc. // violation
     * @param strg Parameter errors desc.   // ok
     */
    public record InnerRecord(List<String> errors, String strg) {
        public boolean isSuccess () {
            return errors.isEmpty();
        }
    }

    /**
     * Defining a Generic record.
     *
     * @param <T> Parameter node desc.  // ok
     * @param <TT> Parameter node desc. // ok, param does not exists
     * @param node Parameter node desc. // ok
     */
    public record MyTestRecord3<T>(T contents, Node node) {
        // do stuff
    }
}
