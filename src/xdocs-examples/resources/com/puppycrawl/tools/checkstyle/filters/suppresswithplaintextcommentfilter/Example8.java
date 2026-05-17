/*xml
<module name="Checker">
  <property name="fileExtensions" value="sql"/>

  <module name="RegexpSingleline">
    <property name="format" value="^.*JOIN\s.+\s(ON|USING)$"/>
    <property name="message" value="Don't use JOIN, use sub-select instead."/>
  </module>

  <module name="LineLength">
    <property name="max" value="60"/>
  </module>

  <module name="SuppressWithPlainTextCommentFilter">
    <property name="offCommentFormat" value="CSOFF\: ([\w\|]+)"/>
    <property name="onCommentFormat" value="CSON\: ([\w\|]+)"/>
    <property name="checkFormat" value="$1"/>
  </module>

</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

// xdoc section -- start
public class Example8 {
  // CHECKSTYLE_OFF: ALMOST_ALL
  public static final int MAX_ITEMS = 100;
  private String[] stringArray;
  // CHECKSTYLE_ON: ALMOST_ALL
  private int[] intArray;

  static final String LOCATION_CSV_SAMPLE = """
          locationId,label,regionId,regionLabel,vendorId,vendorLabel,address,address2,city,stateProvinceCode,zipCode,countryCode,latitude,longitude
          ST001,Station 001,ZONE1,Zone 1,CP1,Competitor 1,123 Street,Unit 2,Houston,TX,77033,US,29.761496813335178,-95.53049214204984
          ST002,Station 002,ZONE2,,CP2,,668 Street,Unit 23,San Jose,CA,95191,US,37.35102477242508,-121.9209934020318
          """;

  static final String SINGLE_LINE_SAMPLE = "locationId,label,regionId,regionLabel,vendorId,vendorLabel,address,address2,city,stateProvinceCode,zipCode,countryCode,latitude,longitude";
}
// xdoc section -- end
