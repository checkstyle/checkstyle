@Grab('org.codenarc:CodeNarc-Groovy4:3.1.0')
@Grab('org.gmetrics:GMetrics-Groovy4:2.1.0')
@GrabExclude('org.apache.groovy:groovy-xml')
import java.lang.Object

org.codenarc.CodeNarc.main([
        "-basedir=${args[0]}",
        "-includes=**/${args[1]}",
        '-rulesetfiles=./.ci/StarterRuleSet-AllRulesByCategory.groovy.txt',
        '-report=console',
] as String[])
