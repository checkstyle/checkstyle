/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
      <property name="tokens" value="LITERAL_FINALLY"/>
      <property name="limitedTokens" value="LITERAL_RETURN,LITERAL_THROW"/>
      <property name="minimumNumber" value="1"/>
      <property name="maximumNumber" value="1"/>
      <property name="sumTokenCounts" value="true"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() { }

void empty() {
    try {
        // violation below 'Total count of 0 is less than minimum count 1 under '.*'.'
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
        // violation below 'Total count of 2 exceeds maximum count 1 under '.*'.'
    } finally {
        if (flag) {
            return;
        }
        throw new IllegalStateException();
    }
}

void twoReturns(boolean flag) {
    try {
        // violation below 'Total count of 2 exceeds maximum count 1 under '.*'.'
    } finally {
        if (flag) {
            return;
        }
        return;
    }
}
