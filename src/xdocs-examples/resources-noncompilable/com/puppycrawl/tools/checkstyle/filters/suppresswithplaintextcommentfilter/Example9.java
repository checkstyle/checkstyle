/*xml
<module name="Checker">
  <module name="com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck">
      <property name="max" value="100"/>
  </module>
  <module name="SuppressWithPlainTextCommentFilter">
    <property name="offCommentFormat" value='=\s+"""'/>
    <property name="onCommentFormat" value='^\s+""";'/>
  </module>
</module>


*/
//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

// xdoc section -- start
public class Example9 {

  // ok, opening and closing triple quotes are our delimiters
  static final String LOCATION_CSV_SAMPLE = """
          locationId,label,regionId,regionLabel,vendorId,vendorLabel,address,address2,city,stateProvinceCode,zipCode,countryCode,latitude,longitude
          ST001,Station 001,ZONE1,Zone 1,CP1,Competitor 1,123 Street,Unit 2,Houston,TX,77033,US,29.761496813335178,-95.53049214204984
          ST002,Station 002,ZONE2,,CP2,,668 Street,Unit 23,San Jose,CA,95191,US,37.35102477242508,-121.9209934020318
          """;

  // violation below, ''
  static final String SINGLE_LINE_SAMPLE = "locationId,label,regionId,regionLabel,vendorId,vendorLabel,address,address2,city,stateProvinceCode,zipCode,countryCode,latitude,longitude";
}
// xdoc section -- end
