package com.google.checkstyle.test.chapter3filestructure.rule332nolinewrap; //ok

import static java.math.BigInteger.ZERO; //ok

import com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck; //ok

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater; //ok
import javax.accessibility.AccessibleAttributeSequence; //ok

public class InputNoLineWrapGood {

  public void fooMethod() {
      //
  }
}
