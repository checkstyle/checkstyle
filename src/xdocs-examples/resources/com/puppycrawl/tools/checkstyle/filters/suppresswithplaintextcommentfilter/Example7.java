/*xml
<module name="Checker">
  <property name="fileExtensions" value="xml"/>

  <module name="RegexpSingleline">
    <property name="id" value="typeCode"/>
    <property name="format"
        value="param\s+name=&quot;type&quot;\s+value=&quot;code&quot;"/>
    <property name="message"
        value="Type code is not allowed. Use type raw instead."/>
  </module>

  <module name="RegexpSingleline">
    <property name="id" value="typeConfig"/>
    <property name="format"
        value="param\s+name=&quot;type&quot;\s+value=&quot;config&quot;"/>
    <property name="message" value="Type config is not allowed in this file."/>
  </module>

  <module name="SuppressWithPlainTextCommentFilter">
    <property name="offCommentFormat" value="CSOFF (\w+) \(([\w\s]+)\)"/>
    <property name="onCommentFormat" value="CSON (\w+)"/>
    <property name="idFormat" value="$1"/>
  </module>

</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

// xdoc section -- start
public class Example7 {
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
