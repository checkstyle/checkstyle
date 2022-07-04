@Grapes(
        @Grab(group='org.codenarc', module='CodeNarc', version='1.4')
)
@GrabExclude('org.codehaus.groovy:groovy-xml')
import java.lang.Object

org.codenarc.CodeNarc.main([
        "-basedir=${args[0]}",
        "-includes=**/${args[1]}",
        '-rulesetfiles=./.ci/StarterRuleSet-AllRulesByCategory.groovy.txt',
        '-report=console',
] as String[])
