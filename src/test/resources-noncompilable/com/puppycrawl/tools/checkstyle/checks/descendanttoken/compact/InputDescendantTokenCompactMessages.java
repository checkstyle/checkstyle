/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
      <property name="tokens" value="LITERAL_FINALLY"/>
      <property name="limitedTokens" value="LITERAL_RETURN"/>
      <property name="minimumNumber" value="1"/>
      <property name="maximumNumber" value="1"/>
      <property name="minimumMessage" value="Too few {3} in {2}: {0} &lt; {1}."/>
      <property name="maximumMessage" value="Too many {3} in {2}: {0} &gt; {1}."/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() { }

void empty() {
    try {
        // violation below 'Too few LITERAL_RETURN in LITERAL_FINALLY: 0 < 1.'
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
        // violation below 'Too many LITERAL_RETURN in LITERAL_FINALLY: 2 > 1.'
    } finally {
        if (flag) {
            return;
        }
        return;
    }
}
