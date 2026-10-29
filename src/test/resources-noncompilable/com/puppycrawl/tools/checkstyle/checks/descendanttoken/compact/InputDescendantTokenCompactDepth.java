/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
      <property name="tokens" value="LITERAL_FINALLY"/>
      <property name="limitedTokens" value="LITERAL_FINALLY,LITERAL_RETURN"/>
      <property name="minimumDepth" value="2"/>
      <property name="maximumDepth" value="2"/>
      <property name="maximumNumber" value="0"/>
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
        // violation below 'Count of 1 for '.*' descendant '.*' exceeds maximum count 0.'
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
        // violation below 'Count of 1 for '.*' descendant '.*' exceeds maximum count 0.'
    } finally {
        if (flag) {
            return;
        }
        return;
    }
}
