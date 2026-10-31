/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
      <property name="tokens" value="LITERAL_FINALLY"/>
      <property name="limitedTokens" value="LITERAL_RETURN"/>
      <property name="minimumNumber" value="1"/>
      <property name="maximumNumber" value="1"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() { }

void empty() {
    try {
        // violation below 'Count of 0 for '.*' descendant '.*' is less than minimum count 1.'
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
        // violation below 'Count of 2 for '.*' descendant '.*' exceeds maximum count 1.'
    } finally {
        if (flag) {
            return;
        }
        return;
    }
}
