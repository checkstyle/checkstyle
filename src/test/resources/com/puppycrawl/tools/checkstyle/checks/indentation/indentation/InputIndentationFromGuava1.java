package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation. //indent:0 exp:0
        InputIndentationFromGuava.ReferenceEntry; //indent:8 exp:8
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation. //indent:0 exp:0
        InputIndentationFromGuava.Segment; //indent:8 exp:8
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation. //indent:0 exp:0
        InputIndentationFromGuava.SoftValueReference; //indent:8 exp:8
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation. //indent:0 exp:0
        InputIndentationFromGuava.StrongValueReference; //indent:8 exp:8
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation. //indent:0 exp:0
        InputIndentationFromGuava.ValueReference; //indent:8 exp:8
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation. //indent:0 exp:0
        InputIndentationFromGuava.WeakValueReference; //indent:8 exp:8
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation. //indent:0 exp:0
        InputIndentationFromGuava.WeightedSoftValueReference; //indent:8 exp:8
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation. //indent:0 exp:0
        InputIndentationFromGuava.WeightedStrongValueReference; //indent:8 exp:8
import static com.puppycrawl.tools.checkstyle.checks.indentation.indentation. //indent:0 exp:0
        InputIndentationFromGuava.WeightedWeakValueReference; //indent:8 exp:8

import java.util.List; //indent:0 exp:0

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
public class InputIndentationFromGuava1 { //indent:0 exp:0

  enum Strength { //indent:2 exp:2
    /*                                                                              //indent:4 exp:4
     *     (kevinb): If we ence the value and aren't loading, we needn't wrap the   //indent:5 exp:5
     * value. This could sar entry.                                                 //indent:5 exp:5
     */                                                                             //indent:5 exp:5

    STRONG { //indent:4 exp:4
      <K, V> Object referenceValue( //indent:6 exp:6
          Segment<K, V> s, ReferenceEntry<K, V> entry, int value, int weight) { //indent:10 exp:>=10
        return (weight == 1) //indent:8 exp:8
            ? new StrongValueReference<K, V>(value) //indent:12 exp:>=12
            : new WeightedStrongValueReference<K, V>(value, weight); //indent:12 exp:>=12
      } //indent:6 exp:6

      @Override //indent:6 exp:6
      List<Object> defaultEquivalence() { //indent:6 exp:6
        return new java.util.ArrayList<>(); //indent:8 exp:8
      } //indent:6 exp:6

      @Override //indent:6 exp:6
     <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, //indent:5 exp:5
                ReferenceEntry<K, V> entry, V value, int weight) { //indent:16 exp:>=10

        return null; //indent:8 exp:8
      } //indent:6 exp:6
    }, //indent:4 exp:4

    SOFT { //indent:4 exp:4
      <K, V> Object referenceValue1( //indent:6 exp:6
          Segment<K, V> s, ReferenceEntry<Integer, Integer> en, //indent:10 exp:>=10
              int va, int we){ //indent:14 exp:14
        return (we == 1) //indent:8 exp:8
            ? new SoftValueReference<K, V>(s.valueReferenceQueue, //indent:12 exp:>=12
                va, en) //indent:16 exp:16
            : new WeightedSoftValueReference<K, V>(); //indent:12 exp:>=12
      } //indent:6 exp:6

      @Override //indent:6 exp:6
      List<Object> defaultEquivalence() { //indent:6 exp:6
        return new java.util.ArrayList<>(); //indent:8 exp:8
      } //indent:6 exp:6

      @Override <K,V> Object referenceValue(Segment<K,V> s, //indent:6 exp:6
                ReferenceEntry<K, V> e, V value, int weight) //indent:16 exp:>=10
      { //indent:6 exp:6
        return null; //indent:8 exp:8
      } //indent:6 exp:6
    }, //indent:4 exp:4

    WEAK { //indent:4 exp:4
      @Override //indent:6 exp:6
      <K, V> Object referenceValue( //indent:6 exp:6
          Segment<K, V> seg, ReferenceEntry<K, V> entry, V value, int weight) { //indent:10 exp:>=10
        return (weight == 1) //indent:8 exp:8
            ? new WeakValueReference<K, V>() //indent:12 exp:>=12
            : new WeightedWeakValueReference<K, V>(); //indent:12 exp:>=12
      } //indent:6 exp:6

      @Override //indent:6 exp:6
      List<Object> defaultEquivalence() { //indent:6 exp:6
        return new java.util.ArrayList<>(); //indent:8 exp:8
      } //indent:6 exp:6
    }; //indent:4 exp:4

    /**                                                                          //indent:4 exp:4
     * Creates a reference for the given value according to this value strength. //indent:5 exp:5
     */                                                                          //indent:5 exp:5
    abstract <K, V> Object referenceValue( //indent:4 exp:4
        Segment<K, V> segment, ReferenceEntry<K, V> entry, V value, int weight); //indent:8 exp:>=8

    /**                                                                             //indent:4 exp:4
     * Returns the default equivalence stcompare and hash keys or values referenced //indent:5 exp:5
     * at this strength. This strategy wiss the user explicitly specifies an        //indent:5 exp:5
     * alternate strategy.                                                          //indent:5 exp:5
     */                                                                             //indent:5 exp:5
    abstract List<Object> defaultEquivalence(); //indent:4 exp:4
  } //indent:2 exp:2

} //indent:0 exp:0
