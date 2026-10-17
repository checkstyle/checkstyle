/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NestedTryDepth">
      <property name="max" value="2"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() {
}

void tryBlocks() {
    try {
        try {
            try { // ok, at the configured limit
                try { // violation 'Nested try depth is 3 (max allowed is 2)\.'
                } finally {
                }
            } finally {
            }
        } finally {
        }
        try { // ok, sibling try starts at depth 1 again
        } finally {
        }
    } finally {
    }
}

void catchAndFinally() {
    try {
    } catch (RuntimeException exception) {
        try {
            try { // ok, at the configured limit
                try { // violation 'Nested try depth is 3 (max allowed is 2)\.'
                } finally {
                }
            } finally {
            }
        } finally {
        }
    } finally {
        try {
            try { // ok, at the configured limit
                try { // violation 'Nested try depth is 3 (max allowed is 2)\.'
                } finally {
                }
            } finally {
            }
        } finally {
        }
    }
}

void afterPreviousMethod() {
    try { // ok, method starts at depth 0 again
        try {
            try { // ok, at the configured limit
            } finally {
            }
        } finally {
        }
    } finally {
    }
}
