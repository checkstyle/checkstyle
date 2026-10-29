/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() { }

void empty() {
    try {
    } finally {
    }
}

void oneReturn() {
    try {
    } finally {
        return;
    }
}

void nestedReturn(boolean flag) {
    try {
    } finally {
        if (flag) {
            return;
        }
    }
}

void mixed(boolean flag) {
    try {
    } finally {
        if (flag) {
            return;
        }
        throw new IllegalStateException();
    }
}

void twoReturns(boolean flag) {
    try {
    } finally {
        if (flag) {
            return;
        }
        return;
    }
}
