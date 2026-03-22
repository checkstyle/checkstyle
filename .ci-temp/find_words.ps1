$suspectWords = @("WWWWWW","WWWWWWWWWWWWWWWWWWWWWWWWWWWW","XXXX","xxxxxx","DEADBEEF","CHDBEFIF","HEXCHARS","HASHTAG","HXKS","FCBL","FCCD","FDCF","FFFA","FFFB","FFFD","FFFFL","IGNORETHIS","DENORMALIZER","DECCHARS","DDDL","ACEM","AFBR","ARCHITEW","BXOR","BNOT","DOTALL","NONFORMATTED","NONGROUP","NONJAVA","NOTNULL","NODESET","NAMEFILE","NEGS","NMCS","PLHM","PRMC","VALUELONG","EBNF","LBRACE","RBRACE","LBRACK","RBRACK")
$output = @()
foreach ($word in $suspectWords) {
    $hits = Get-ChildItem -Path C:\Users\brije\checkstyle\src -Recurse -Include "*.java","*.xml","*.txt" | Select-String -Pattern $word -List | Select-Object -First 3
    foreach ($hit in $hits) {
        $output += "$word -> " + $hit.Path.Replace("C:\Users\brije\checkstyle\","")
    }
    if (-not $hits) {
        $output += "$word -> NOT FOUND IN SRC"
    }
}
$output | Set-Content C:\Users\brije\checkstyle\.ci-temp\word_locations.txt

