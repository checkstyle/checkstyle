/*
AtclauseOrder
violateExecutionOnNonTightHtml = (default)false
target = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF
tagOrder = (default)@author, @version, @param, @return, @throws, @exception, \
            @see, @since, @serial, @serialField, @serialData, @deprecated


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.javadoc.atclauseorder;

import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Native;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.w3c.dom.Node;

public class InputAtclauseOrderLotsOfRecords1 {
    public static int getRecord() {
        return record;
    }

    // Simple Annotated Record components
    public @interface NonNull1 {
    }


    /**
     * Javadoc
     *
     */
    public record AnnotatedBinaryNode(@Native @NonNull1 Node left, @NonNull1 Node right) {
    }

    public interface Coords {
        public double x();

        public double y();
    }

    /**
     * @param r     not really a param
     * @param theta not really a param
     * @return
     */
    public record Polar(double r, double theta) implements Coords {
        @Override
        public double x() {
            return r * Math.cos(theta);
        }

        /**
         * Javadoc
         */
        @Override
        public double y() {
            return r * Math.sin(theta);
        }
    }

    /**
     * Javadoc
     */
    public record Holder<T>(T t) {
    }

    public record HolderG<G>(G g) {

        /**
         * Javadoc here too
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HolderG<?> holderG = (HolderG<?>) o;
            return Objects.equals(g, holderG.g);
        }

        /**
         *
         * @return
         */
        @Override
        public int hashCode() {
            return Objects.hash(g);
        }
    }

    private static int record = 2;
}
