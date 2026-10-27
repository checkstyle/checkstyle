/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NestedTryDepth"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() {
}

void tryBlocks() {
    try {
        try {
            try { // violation 'Nested try depth is 2 (max allowed is 1)\.'
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
            try { // violation 'Nested try depth is 2 (max allowed is 1)\.'
            } finally {
            }
        } finally {
        }
    } finally {
        try {
            try { // violation 'Nested try depth is 2 (max allowed is 1)\.'
            } finally {
            }
        } finally {
        }
    }
}

void afterPreviousMethod() {
    try { // ok, method starts at depth 0 again
        try { // ok, at the default limit
        } finally {
        }
    } finally {
    }
}
