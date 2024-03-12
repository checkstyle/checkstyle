/*
AtclauseOrder
violateExecutionOnNonTightHtml = (default)false
target = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF
tagOrder = @author, @version, @param, @return, @throws, @exception, @see, @since, @serial, \
           @serialField, @serialData, @deprecated


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

public class InputAtclauseOrderLotsOfRecords4 {

    /**
     * Javadoc here
     */
    record RX(int[]xs) {
    }

    private static int record = 2;

    public Class<Record[]> getRecordType() {
        return Record[].class;
    }

    class LocalRecordHelper {
        Class<?> m(int x) {
            record R76(int x) {
            }
            return R.class;
        }

        private class R {
            public R(int x) {
            }
        }
    }

    /**
     * @deprecated
     */
    @Deprecated
            record R1() implements Serializable {
        private static final TimeUnit Path = null;
        private static final long serialVersionUID = -2911897846173867769L;

        public R1 {

        }
    }

    record RR3(String...args) implements Serializable {
        private static final boolean firstDataSetCreated = false;
        private static final long serialVersionUID = -5626758281412733319L;

        public RR3 {
            if (firstDataSetCreated) {
                ProcessHandle.current();
            }
        }
    }

}
