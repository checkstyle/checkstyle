package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation. //indent:0 exp:0
        InputIndentationFromGuava.ReferenceEntry; //indent:8 exp:8
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation. //indent:0 exp:0
        InputIndentationFromGuava.Segment; //indent:8 exp:8
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation. //indent:0 exp:0
        InputIndentationFromGuava.StrongAccessEntry; //indent:8 exp:8

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 2                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputIndentationFromGuava2 { //indent:0 exp:0

  /**                        //indent:2 exp:2
   * Creates new entries.    //indent:3 exp:3
   */                        //indent:3 exp:3
  enum EntryFactory { //indent:2 exp:2
    STRONG { //indent:4 exp:4
      <K, V> StrongEntry<K, V> newEntry( //indent:6 exp:6
          Segment<K, V> s, K k, int h, @XmlElement ReferenceEntry<K, V> next) { //indent:10 exp:>=10
        return new StrongEntry<K, V>(); //indent:8 exp:8
      } //indent:6 exp:6
    }, //indent:4 exp:4
    STRONG_ACCESS { //indent:4 exp:4
      <K, V> StrongAccessEntry<K, V> newEntry( //indent:6 exp:6
          Segment<K, V> s, K k, int h, @XmlElement ReferenceEntry<K, V> next) { //indent:10 exp:>=10
        return new StrongAccessEntry<K, V>(k, h, next); //indent:8 exp:8
      } //indent:6 exp:6

      <K, V> ReferenceEntry<K, V> copyEntry( //indent:6 exp:6
          Segment<K, V> s, ReferenceEntry<K, V> o, ReferenceEntry<K, V> newT) { //indent:10 exp:>=10
        return newT; //indent:8 exp:8
      } //indent:6 exp:6
      {; //indent:6 exp:6
      } //indent:6 exp:6
     }, //indent:5 exp:5
    STRONG_WRITE { //indent:4 exp:4
      <K, V> StrongEntry<K, V> newEntry( //indent:6 exp:6
          Segment<K, V> s, K k, int h, @XmlElement ReferenceEntry<K, V> next) { //indent:10 exp:>=10
        return new StrongEntry<K, V>(); //indent:8 exp:8
      } //indent:6 exp:6

      <K, V> ReferenceEntry<K, V> copyEntry( //indent:6 exp:6
          Segment<K, V> s, ReferenceEntry<K, V> o, ReferenceEntry<K, V> newN) { //indent:10 exp:>=10
        return newN; //indent:8 exp:8
      } //indent:6 exp:6
    }, //indent:4 exp:4
    STRONG_ACCESS_WRITE { //indent:4 exp:4
      <K, V> StrongEntry<K, V> newEntry( //indent:6 exp:6
          Segment<K, V> s, K k, int h, @XmlElement ReferenceEntry<K, V> next) { //indent:10 exp:>=10
        return new StrongEntry<K, V>(); //indent:8 exp:8
      } //indent:6 exp:6

      <K, V> ReferenceEntry<K, V> copyEntry( //indent:6 exp:6
          Segment<K, V> s, ReferenceEntry<K, V> o, ReferenceEntry<K, V> newN) { //indent:10 exp:>=10
        return newN; //indent:8 exp:8
      } //indent:6 exp:6
    }, //indent:4 exp:4

    WEAK { //indent:4 exp:4
      <K, V> StrongEntry<K, V> newEntry( //indent:6 exp:6
          Segment<K, V> s, K k, int h, @XmlElement ReferenceEntry<K, V> next) { //indent:10 exp:>=10
        return new StrongEntry<K, V>(); //indent:8 exp:8
      } //indent:6 exp:6
    }, //indent:4 exp:4
    WEAK_ACCESS { //indent:4 exp:4
      <K, V> StrongEntry<K, V> newEntry( //indent:6 exp:6
          Segment<K, V> s, K k, int h, @XmlElement ReferenceEntry<K, V> next) { //indent:10 exp:>=10
        return new StrongEntry<K, V>(); //indent:8 exp:8
      } //indent:6 exp:6

      <K, V> ReferenceEntry<K, V> copyEntry( //indent:6 exp:6
          Segment<K, V> s, ReferenceEntry<K, V> o, ReferenceEntry<K, V> newN) { //indent:10 exp:>=10
        return newN; //indent:8 exp:8
      } //indent:6 exp:6
    }, //indent:4 exp:4
    WEAK_WRITE { //indent:4 exp:4
      <K, V> StrongEntry<K, V> newEntry( //indent:6 exp:6
          Segment<K, V> s, K k, int h, @XmlElement ReferenceEntry<K, V> next) { //indent:10 exp:>=10
        return new StrongEntry<K, V>(); //indent:8 exp:8
      } //indent:6 exp:6

      <K, V> ReferenceEntry<K, V> copyEntry( //indent:6 exp:6
          Segment<K, V> s, ReferenceEntry<K, V> o, ReferenceEntry<K, V> newN) { //indent:10 exp:>=10
        return newN; //indent:8 exp:8
      } //indent:6 exp:6
    }, //indent:4 exp:4
    WEAK_ACCESS_WRITE { //indent:4 exp:4
      <K, V> StrongEntry<K, V> newEntry( //indent:6 exp:6
          Segment<K, V> s, K k, int h, @XmlElement ReferenceEntry<K, V> next) { //indent:10 exp:>=10
        return new StrongEntry<K, V>(); //indent:8 exp:8
      } //indent:6 exp:6

      <K, V> ReferenceEntry<K, V> copyEntry( //indent:6 exp:6
          Segment<K, V> s, ReferenceEntry<K, V> o, ReferenceEntry<K, V> newN) { //indent:10 exp:>=10
        return newN; //indent:8 exp:8
      } //indent:6 exp:6
    }; //indent:4 exp:4
  } //indent:2 exp:2

  private static class StrongEntry<T1, T2> { //indent:2 exp:2

  } //indent:2 exp:2

  public @interface XmlElement { //indent:2 exp:2
  } //indent:2 exp:2

} //indent:0 exp:0
