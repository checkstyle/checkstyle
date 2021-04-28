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
     * @param name
     *            Parameter name desc.
     * @param <address>
     *            Parameter address desc. // violation
     */
    record Person(String name, String address) {
        /**
         * Test case for constructor.
         *
         * @param name
         *            Parameter name desc.
         */
        Person(String name) {
            this(name, "Unknown");
        }
    }

    /**
     * Description.
     *
     * @param brand
     *            Parameter brand desc.
     * @param licensePlate
     *            Parameter licensePlate desc.
     */
    record Vehicle(String brand, String licensePlate) {

        /**
         * Description.
         *
         * @param <brand
         *            Parameter brand desc. // violation
         */
        Vehicle(String brand) {
            this(brand, null);
        }
    }

    /**
     * Description.
     *
     * @param errors
     *            Parameter errors desc.
     */
    public record InnerRecord(List<String> errors) {
        public boolean isSuccess () {
            return errors.isEmpty();
        }
    }

    /**
     * Defining a Generic record.
     *
     * @param <T>
     *            Parameter node desc.
     * @param <TT>
     *            Parameter node desc. // violation
     * @param node
     *            Parameter node desc.
     */
    public record MyTestRecord3<T>(T contents, Node node) {
        public MyTestRecord3 {
            int x = 5;
        }
    }
}
