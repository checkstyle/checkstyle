import groovy.cli.picocli.CliBuilder
import static java.lang.System.err
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING

import java.nio.file.Files
import java.nio.file.Paths
import java.util.regex.Pattern

 void main(String[] args) {
    def cliOptions = getCliOptions(args)
    if (cliOptions != null) {
        if (areValidCliOptions(cliOptions)) {
            def cfg = new Config(cliOptions)
            def configFilesList = [cfg.config, cfg.baseConfig, cfg.patchConfig, cfg.listOfProjects]
            copyConfigFilesAndUpdatePaths(configFilesList)

            if (cfg.localGitRepo && hasUnstagedChanges(cfg.localGitRepo)) {
                def exMsg = "Error: git repository ${cfg.localGitRepo.path} has unstaged changes!"
                throw new IllegalStateException(exMsg)
            }

            // Delete work directories to avoid conflicts with previous reports generation
            if (new File(cfg.reportsDir).exists()) {
                deleteDir(cfg.reportsDir)
            }
            if (new File(cfg.tmpReportsDir).exists()) {
                deleteDir(cfg.tmpReportsDir)
            }

            def checkstyleBaseReportInfo = null
            if (cfg.isDiffMode()) {
                checkstyleBaseReportInfo = launchCheckstyleReport(cfg.checkstyleToolBaseConfig)
            }

            def checkstylePatchReportInfo = launchCheckstyleReport(cfg.checkstyleToolPatchConfig)

            if (checkstylePatchReportInfo) {
                deleteDir(cfg.reportsDir)
                moveDir(cfg.tmpReportsDir, cfg.reportsDir)

                generateDiffReport(cfg.diffToolConfig)
                generateSummaryIndexHtml(cfg.diffDir, checkstyleBaseReportInfo,
                    checkstylePatchReportInfo, configFilesList, cfg.allowExcludes)
            }
        }
        else {
            throw new IllegalArgumentException('Error: invalid command line arguments!')
        }
    }
}

def getCliOptions(args) {
    def cliOptionsDescLineLength = 120
    def cli = new CliBuilder(
        usageMessage: 'groovy diff.groovy [options]',
        headerHeading: 'Options:\n',
        width: cliOptionsDescLineLength
    )
    cli.with {
        r(longOpt: 'localGitRepo', arity: "1", required: true, argName: 'path',
            'Path to local git repository (required)')
        b(longOpt: 'baseBranch', arity: "1", required: false, argName: 'branch_name',
            'Base branch name. Default is master (optional, default is master)')
        p(longOpt: 'patchBranch', arity: "1", required: true, argName: 'branch_name',
            'Name of the patch branch in local git repository (required)')
        bc(longOpt: 'baseConfig', arity: "1", required: false, argName: 'path', 'Path to the base ' \
            + 'checkstyle config file (optional, if absent then the tool will use only ' \
            + 'patchBranch in case the tool mode is \'single\', otherwise baseBranch ' \
            + 'will be set to \'master\')')
        pc(longOpt: 'patchConfig', arity: "1", required: false, argName: 'path',
            'Path to the patch checkstyle config file (required if baseConfig is specified)')
        c(longOpt: 'config', arity: "1", required: false, argName: 'path', 'Path to the checkstyle ' \
            + 'config file (required if baseConfig and patchConfig are not secified)')
        g(longOpt: 'allowExcludes', required: false, 'Whether to allow excludes specified in the list of ' \
            + 'projects (optional, default is false)')
        h(longOpt: 'useShallowClone', 'Enable shallow cloning')
        l(longOpt: 'listOfProjects', arity: "1", required: true, argName: 'path',
            'Path to file which contains projects to test on (required)')
        s(longOpt: 'shortFilePaths', required: false, 'Whether to save report file paths' \
            + ' as a shorter version to prevent long paths. (optional, default is false)')
        m(longOpt: 'mode', arity: "1", required: false, argName: 'mode', 'The mode of the tool:' \
            + ' \'diff\' or \'single\'. (optional, default is \'diff\')')
        xm(longOpt: 'extraMvnRegressionOptions', arity: "1", required: false, 'Extra arguments to pass to Maven' \
            + 'for Checkstyle Regression run (optional, ex: -Dmaven.prop=true)')
    }
    return cli.parse(args)
}

def areValidCliOptions(cliOptions) {
    def valid = true
    def baseConfig = cliOptions.baseConfig
    def patchConfig = cliOptions.patchConfig
    def config = cliOptions.config
    def toolMode = cliOptions.mode
    def patchBranch = cliOptions.patchBranch
    def baseBranch = cliOptions.baseBranch
    def listOfProjectsFile = new File(cliOptions.listOfProjects)
    def localGitRepo = cliOptions.localGitRepo

    if (toolMode && !('diff'.equals(toolMode) || 'single'.equals(toolMode))) {
        err.println "Error: Invalid mode: \'$toolMode\'. The mode should be \'single\' or \'diff\'!"
        valid = false
    }
    else if (!isValidCheckstyleConfigsCombination(config, baseConfig, patchConfig, toolMode)) {
        valid = false
    }
    else if (localGitRepo && !isValidGitRepo(new File(localGitRepo))) {
        err.println "Error: $localGitRepo is not a valid git repository!"
        valid = false
    }
    else if (localGitRepo && !isExistingGitBranch(new File(localGitRepo), patchBranch)) {
        err.println "Error: $patchBranch is not an exiting git branch!"
        valid = false
    }
    else if (baseBranch &&
            !isExistingGitBranch(new File(localGitRepo), baseBranch)) {
        err.println "Error: $baseBranch is not an existing git branch!"
        valid = false
    }
    else if (!listOfProjectsFile.exists()) {
        err.println "Error: file ${listOfProjectsFile.name} does not exist!"
        valid = false
    }

    return valid
}

def isValidCheckstyleConfigsCombination(config, baseConfig, patchConfig, toolMode) {
    def valid = true
    if (!config && !patchConfig && !baseConfig) {
        err.println "Error: you should specify either \'config\'," \
            + " or \'baseConfig\', or \'patchConfig\'!"
        valid = false
    }
    else if (config && (patchConfig || baseConfig)) {
        err.println "Error: you should specify either \'config\'," \
            + " or \'baseConfig\' and \'patchConfig\', or \'patchConfig\' only!"
        valid = false
    }
    else if ('diff'.equals(toolMode) && baseConfig && !patchConfig) {
        err.println "Error: \'patchConfig\' should be specified!"
        valid = false
    }
    else if ('diff'.equals(toolMode) && patchConfig && !baseConfig) {
        err.println "Error: \'baseConfig\' should be specified!"
        valid = false
    }
    else if ('single'.equals(toolMode) && (baseConfig || config)) {
        err.println "Error: \'baseConfig\' and/or \'config\' should not be used in \'single\' mode!"
        valid = false
    }
    return valid
}

def isValidGitRepo(gitRepoDir) {
    def valid = true
    if (gitRepoDir.exists() && gitRepoDir.isDirectory()) {
        def gitStatusCmd = "git status".execute(null, gitRepoDir)
        gitStatusCmd.waitFor()
        if (gitStatusCmd.exitValue() != 0) {
            err.println "Error: \'${gitRepoDir.path}\' is not a git repository!"
            valid = false
        }
    }
    else {
        err.println "Error: \'${gitRepoDir.path}\' does not exist or it is not a directory!"
        valid = false
    }
    return valid
}

def isExistingGitBranch(gitRepo, branchName) {
    def exist = true
    def gitRevParseCmd = "git rev-parse --verify $branchName".execute(null, gitRepo)
    gitRevParseCmd.waitFor()
    if (gitRevParseCmd.exitValue() != 0) {
        err.println "Error: git repository ${gitRepo.path} does not have a branch with name \'$branchName\'!"
        exist = false
    }
    return exist
}

def copyConfigFilesAndUpdatePaths(configFilesList) {
    // Remove boolean value for single config in case of different base and patch config
    configFilesList.removeIf { it instanceof Boolean }

    // Remove dups in case of single config
    for (filename in configFilesList.unique()) {
        def sourceFile = new File(filename)
        def checkstyleTesterDir = new File("").getCanonicalFile()
        def destFile = new File("${checkstyleTesterDir.toPath()}/${sourceFile.getName()}")
        Files.copy(sourceFile.toPath(), destFile.toPath(), REPLACE_EXISTING)
        filename = destFile.toPath()
    }
}

def hasUnstagedChanges(gitRepo) {
    def hasUnstagedChanges = true
    def gitStatusCmd = "git diff --exit-code".execute(null, gitRepo)
    gitStatusCmd.waitFor()
    if (gitStatusCmd.exitValue() == 0) {
        hasUnstagedChanges = false
    } else {
        println gitStatusCmd.text
    }
    return hasUnstagedChanges
}

def getCheckstyleVersionFromPomXml(pathToPomXml, xmlTagName) {
    def pomXmlFile = new File(pathToPomXml)
    def checkstyleVersion
    pomXmlFile.eachLine {
        line ->
            if (line.matches("^.*<$xmlTagName>.*-SNAPSHOT</$xmlTagName>.*")) {
                checkstyleVersion = line.substring(line.indexOf('>') + 1, line.lastIndexOf('<'))
                return true
            }
    }
    return checkstyleVersion
}

def launchCheckstyleReport(cfg) {
    CheckstyleReportInfo reportInfo
    def isRegressionTesting = cfg.branch && cfg.localGitRepo

    // If "no exception" testing, these may not be defined in repos other than checkstyle
    if (isRegressionTesting) {
        println "Installing Checkstyle artifact ($cfg.branch) into local Maven repository ..."
        executeCmd("git checkout $cfg.branch", cfg.localGitRepo)
        executeCmd("git log -1 --pretty=MSG:%s%nSHA-1:%H", cfg.localGitRepo)
        executeCmd("mvn -e --no-transfer-progress --batch-mode -Pno-validations clean install",
            cfg.localGitRepo)
    }

    cfg.checkstyleVersion =
            getCheckstyleVersionFromPomXml("$cfg.localGitRepo/pom.xml", 'version')

    generateCheckstyleReport(cfg)

    println "Moving Checkstyle report into $cfg.destDir ..."
    moveDir("reports", cfg.destDir)

    if (isRegressionTesting) {
        reportInfo = new CheckstyleReportInfo(
            cfg.branch,
            getLastCheckstyleCommitSha(cfg.localGitRepo, cfg.branch),
            getLastCommitMsg(cfg.localGitRepo, cfg.branch),
            getLastCommitTime(cfg.localGitRepo, cfg.branch)
        )
    }
    return reportInfo
}

def generateCheckstyleReport(cfg) {
    println 'Testing Checkstyle started'

    def targetDir = 'target'
    def srcDir = getOsSpecificPath("src", "main", "java")
    def reposDir = 'repositories'
    def reportsDir = 'reports'
    makeWorkDirsIfNotExist(srcDir, reposDir, reportsDir)

    final repoNameParamNo = 0
    final repoTypeParamNo = 1
    final repoURLParamNo = 2
    final repoCommitIDParamNo = 3
    final repoExcludesParamNo = 4
    final fullParamListSize = 5

    def checkstyleConfig = cfg.checkstyleCfg
    def checkstyleVersion = cfg.checkstyleVersion
    def allowExcludes = cfg.allowExcludes
    def useShallowClone = cfg.useShallowClone
    def listOfProjectsFile = new File(cfg.listOfProjects)
    def projects = listOfProjectsFile.readLines()
    def extraMvnRegressionOptions = cfg.extraMvnRegressionOptions

    projects.each {
        project ->
            if (!project.startsWith('#') && !project.isEmpty()) {
                def params = project.split('\\|', -1)
                if (params.length < fullParamListSize) {
                    throw new InvalidPropertiesFormatException("Error: line '$project' " +
                        "in file '$listOfProjectsFile.name' should have $fullParamListSize " +
                        "pipe-delimited sections!")
                }

                def repoName = params[repoNameParamNo]
                def repoType = params[repoTypeParamNo]
                def repoUrl = params[repoURLParamNo]
                def commitId = params[repoCommitIDParamNo]

                def excludes = ""
                if (allowExcludes) {
                    excludes = params[repoExcludesParamNo]
                }

                deleteDir(srcDir)
                if (repoType == 'local') {
                    copyDir(repoUrl, getOsSpecificPath("$srcDir", "$repoName"))
                } else {
                    if (useShallowClone && !isGitSha(commitId)) {
                        shallowCloneRepository(repoName, repoType, repoUrl, commitId, reposDir)
                    } else {
                        cloneRepository(repoName, repoType, repoUrl, commitId, reposDir)
                    }
                    copyDir(getOsSpecificPath("$reposDir", "$repoName"), getOsSpecificPath("$srcDir", "$repoName"))
                }
                runMavenExecution(srcDir, excludes, checkstyleConfig,
                    checkstyleVersion, extraMvnRegressionOptions)
                def repoPath = repoUrl
                if (repoType != 'local') {
                    repoPath = new File(getOsSpecificPath("$reposDir", "$repoName")).absolutePath
                }
                postProcessCheckstyleReport(targetDir, repoName, repoPath)
                deleteDir(getOsSpecificPath("$srcDir", "$repoName"))
                moveDir(targetDir, getOsSpecificPath("$reportsDir", "$repoName"))
            }
    }

    // restore empty_file to make src directory tracked by git
    new File(getOsSpecificPath("$srcDir", "empty_file")).createNewFile()
}

def getLastCheckstyleCommitSha(gitRepo, branch) {
    executeCmd("git checkout $branch", gitRepo)
    return 'git rev-parse HEAD'.execute(null, gitRepo).text.trim()
}

def getLastCommitMsg(gitRepo, branch) {
    executeCmd("git checkout $branch", gitRepo)
    return 'git log -1 --pretty=%B'.execute(null, gitRepo).text.trim()
}

def getLastCommitTime(gitRepo, branch) {
    executeCmd("git checkout $branch", gitRepo)
    return 'git log -1 --format=%cd'.execute(null, gitRepo).text.trim()
}

def getCommitSha(commitId, repoType, srcDestinationDir) {
    def cmd = ''
    switch (repoType) {
        case 'git':
            cmd = "git rev-parse $commitId"
            break
        default:
            throw new IllegalArgumentException("Error! Unknown $repoType repository.")
    }
    def sha = cmd.execute(null, new File("$srcDestinationDir")).text
    // cmd output contains new line character which should be removed
    return sha.replace('\n', '')
}

def shallowCloneRepository(repoName, repoType, repoUrl, commitId, srcDir) {
    def srcDestinationDir = getOsSpecificPath("$srcDir", "$repoName")
    if (!Files.exists(Paths.get(srcDestinationDir))) {
        def cloneCmd = getCloneShallowCmd(repoType, repoUrl, srcDestinationDir, commitId)
        println "Shallow clone $repoType repository '$repoName' to $srcDestinationDir folder ..."
        executeCmdWithRetry(cloneCmd)
        println "Cloning $repoType repository '$repoName' - completed\n"
    }
    println "$repoName is synchronized"
}

def cloneRepository(repoName, repoType, repoUrl, commitId, srcDir) {
    def srcDestinationDir = getOsSpecificPath("$srcDir", "$repoName")
    if (!Files.exists(Paths.get(srcDestinationDir))) {
        def cloneCmd = getCloneCmd(repoType, repoUrl, srcDestinationDir)
        println "Cloning $repoType repository '$repoName' to $srcDestinationDir folder ..."
        executeCmdWithRetry(cloneCmd)
        println "Cloning $repoType repository '$repoName' - completed\n"
    }

    if (commitId && commitId != '') {
        def lastCommitSha = getLastProjectCommitSha(repoType, srcDestinationDir)
        def commitIdSha = getCommitSha(commitId, repoType, srcDestinationDir)
        if (lastCommitSha != commitIdSha) {
            if (!isGitSha(commitId)) {
                // If commitId is a branch or tag, fetch more data and then reset
                fetchAdditionalData(repoType, srcDestinationDir, commitId)
            }
            def resetCmd = getResetCmd(repoType, commitId)
            println "Resetting $repoType sources to commit '$commitId'"
            executeCmd(resetCmd, new File("$srcDestinationDir"))
        }
    }
    println "$repoName is synchronized"
}

def getCloneCmd(repoType, repoUrl, srcDestinationDir) {
    if ('git'.equals(repoType)) {
        return "git clone $repoUrl $srcDestinationDir"
    }
    throw new IllegalArgumentException("Error! Unknown $repoType repository.")
}

def getCloneShallowCmd(repoType, repoUrl, srcDestinationDir, commitId) {
    if ('git'.equals(repoType)) {
        return "git clone --depth 1 --branch $commitId $repoUrl $srcDestinationDir"
    }
    throw new IllegalArgumentException("Error! Unknown repository type: $repoType")
}

def fetchAdditionalData(repoType, srcDestinationDir, commitId) {
    String fetchCmd = ''
    if ('git'.equals(repoType)) {
        if (isGitSha(commitId)) {
            fetchCmd = "git fetch"
        } else {
            // Check if commitId is a tag and handle accordingly
            if (isTag(commitId, new File(srcDestinationDir))) {
                fetchCmd = "git fetch --tags"
            } else {
                fetchCmd = "git fetch origin $commitId:$commitId"
            }
        }
    } else {
        throw new IllegalArgumentException("Error! Unknown $repoType repository.")
    }

    executeCmd(fetchCmd, new File(srcDestinationDir))
}

def isTag(commitId, gitRepo) {
    def tagCheckCmd = "git tag -l $commitId".execute(null, gitRepo)
    tagCheckCmd.waitFor()
    return tagCheckCmd.text.trim().equals(commitId)
}

// it is not very accurate match, but in case of mismatch we will do full clone
def isGitSha(value) {
    return value ==~ /[0-9a-f]{5,40}/
}

def executeCmdWithRetry(cmd, dir = new File("").getAbsoluteFile(), retry = 5) {
    def osSpecificCmd = getOsSpecificCmd(cmd)
    def left = retry
    while (true) {
        def proc = osSpecificCmd.execute(null, dir)
        proc.consumeProcessOutput(System.out, System.err)
        proc.waitFor()
        left--
        if (proc.exitValue() != 0) {
            if (left <= 0) {
                throw new GroovyRuntimeException("Error: ${proc.err.text}!")
            } else {
                Thread.sleep(15000)
            }
        } else {
            break
        }
    }
}

def generateDiffReport(cfg) {
    def diffToolDir = Paths.get("").toAbsolutePath()
        .parent
        .resolve("patch-diff-report-tool")
        .toFile()
    executeCmd("mvn -e --no-transfer-progress --batch-mode clean package -DskipTests", diffToolDir)
    def diffToolJarPath = getPathToDiffToolJar(diffToolDir)

    println 'Starting diff report generation ...'
    Paths.get(cfg.patchReportsDir).toFile().eachFile {
        fileObj ->
            if (fileObj.isDirectory()) {
                def projectName = fileObj.name
                def patchReportDir = new File("$cfg.patchReportsDir/$projectName")
                if (patchReportDir.exists()) {
                    def patchReport = "$cfg.patchReportsDir/$projectName/checkstyle-result.xml"
                    def outputDir = "$cfg.reportsDir/diff/$projectName"
                    def diffCmd = """java -jar $diffToolJarPath --patchReport $patchReport
                        --output $outputDir --patchConfig $cfg.patchConfig"""
                    if ('diff'.equals(cfg.mode)) {
                        def baseReport = "$cfg.masterReportsDir/$projectName/checkstyle-result.xml"
                        diffCmd += " --baseReport $baseReport --baseConfig $cfg.baseConfig"
                    }
                    if (cfg.shortFilePaths) {
                        diffCmd += ' --shortFilePaths'
                    }
                    executeCmd(diffCmd)
                } else {
                    def exMsg = "Error: patch report for project $projectName is not found!"
                    throw new FileNotFoundException(exMsg)
                }
            }
    }
    println 'Diff report generation finished ...'
}

def getPathToDiffToolJar(diffToolDir) {
    def targetDir = diffToolDir.absolutePath + '/target/'
    def pathToDiffToolJar
    Paths.get(targetDir).toFile().eachFile {
        fileObj ->
            def jarPattern = "patch-diff-report-tool-.*.jar-with-dependencies.jar"
            def fileName = fileObj.name
            if (fileName.matches(jarPattern)) {
                pathToDiffToolJar = fileObj.absolutePath
                return true
            }
    }
    if (pathToDiffToolJar == null) {
        throw new FileNotFoundException("Error: difff tool jar file is not found!")
    }
    return pathToDiffToolJar
}

def getTextTransform() {
    def diffToolDir = Paths.get("").toAbsolutePath()
        .parent
        .resolve("patch-diff-report-tool")
        .toFile()
    def diffToolJarPath = getPathToDiffToolJar(diffToolDir)
    this.class.classLoader.rootLoader.addURL(new URL("file:$diffToolJarPath"))
    def textTransform = this.class.getClassLoader().loadClass("com.github.checkstyle.site.TextTransform").newInstance()

    return textTransform
}

def generateSummaryIndexHtml(diffDir, checkstyleBaseReportInfo,
                             checkstylePatchReportInfo, configFilesList, allowExcludes) {
    println 'Starting creating report summary page ...'
    def projectsStatistic = getProjectsStatistic(diffDir)
    def summaryIndexHtml = new File("$diffDir/index.html")

    summaryIndexHtml.text = ''
    summaryIndexHtml << ('<html><head>')
    summaryIndexHtml << ('<link rel="icon" href="https://checkstyle.org/images/favicon.png" type="image/x-icon" />')
    summaryIndexHtml << ('<title>Checkstyle Tester Report Diff Summary</title>')
    summaryIndexHtml << ('</head><body>')
    summaryIndexHtml << ('\n')
    if (!allowExcludes) {
        summaryIndexHtml << ('<h3><span style="color: #ff0000;">')
        summaryIndexHtml << ('<strong>WARNING: Excludes are ignored by diff.groovy.</strong>')
        summaryIndexHtml << ('</span></h3>')
    }
    printReportInfoSection(summaryIndexHtml, checkstyleBaseReportInfo, checkstylePatchReportInfo, projectsStatistic)
    printConfigSection(diffDir, configFilesList, summaryIndexHtml)

    projectsStatistic.sort { it.key.toLowerCase() }.sort { it.value == 0 ? 1 : 0 }.each {
        project, diffCount ->
            summaryIndexHtml << ("<a href='$project/index.html'>$project</a>")
            if (diffCount[0] != 0) {
                if (diffCount[1] == 0) {
                    summaryIndexHtml << (" ( &#177;${diffCount[0]}, <span style=\"color: red;\">" +
                        "-${diffCount[2]}</span> )")
                }
                else if (diffCount[2] == 0) {
                    summaryIndexHtml << (" ( &#177;${diffCount[0]}, <span style=\"color: green;\">" +
                        "+${diffCount[1]}</span> )")
                }
                else {
                    summaryIndexHtml << (" ( &#177;${diffCount[0]}, <span style=\"color: red;\">" +
                        "-${diffCount[2]}</span>, <span style=\"color: green;\">+${diffCount[1]}</span> )")
                }
            }
            summaryIndexHtml << ('<br />')
            summaryIndexHtml << ('\n')
    }
    summaryIndexHtml << ('</body></html>')

    println 'Creating report summary page finished...'
}

def printConfigSection(diffDir, configFilesList, summaryIndexHtml) {
    def textTransform = getTextTransform()
    for (filename in configFilesList) {
        def configFile = new File(filename)
        generateAndPrintConfigHtmlFile(diffDir, configFile, textTransform,
            summaryIndexHtml)
    }
}

def generateAndPrintConfigHtmlFile(diffDir, configFile, textTransform, summaryIndexHtml) {
    def configfilenameWithoutExtension = getFilenameWithoutExtension(configFile.name)
    def configFileHtml = new File("$diffDir/${configfilenameWithoutExtension}.html")
    textTransform.transform(configFile.name, configFileHtml.toPath().toString(), Locale.ENGLISH,
        "UTF-8", "UTF-8")

    summaryIndexHtml << ('<h6>')
    summaryIndexHtml << ("<a href='${configFileHtml.name}'>${configFile.name} file</a>")
    summaryIndexHtml << ('</h6>')
}

def getFilenameWithoutExtension(filename) {
    def filenameWithoutExtension
    int pos = filename.lastIndexOf(".")
    if (pos > 0) {
        filenameWithoutExtension = filename.substring(0, pos)
    }
    return filenameWithoutExtension
}

def makeWorkDirsIfNotExist(srcDirPath, repoDirPath, reportsDirPath) {
    def srcDir = new File(srcDirPath)
    if (!srcDir.exists()) {
        srcDir.mkdirs()
    }
    def repoDir = new File(repoDirPath)
    if (!repoDir.exists()) {
        repoDir.mkdir()
    }
    def reportsDir = new File(reportsDirPath)
    if (!reportsDir.exists()) {
        reportsDir.mkdir()
    }
}

def printReportInfoSection(summaryIndexHtml, checkstyleBaseReportInfo, checkstylePatchReportInfo, projectsStatistic) {
    def date = new Date()
    summaryIndexHtml << ('<h6>')
    if (checkstyleBaseReportInfo) {
        summaryIndexHtml << "Base branch: $checkstyleBaseReportInfo.branch"
        summaryIndexHtml << ('<br />')
        summaryIndexHtml << "Base branch last commit SHA: $checkstyleBaseReportInfo.commitSha"
        summaryIndexHtml << ('<br />')
        summaryIndexHtml << "Base branch last commit message: \"$checkstyleBaseReportInfo.commitMsg\""
        summaryIndexHtml << ('<br />')
        summaryIndexHtml << "Base branch last commit timestamp: \"$checkstyleBaseReportInfo.commitTime\""
        summaryIndexHtml << ('<br />')
        summaryIndexHtml << ('<br />')
    }
    summaryIndexHtml << "Patch branch: $checkstylePatchReportInfo.branch"
    summaryIndexHtml << ('<br />')
    summaryIndexHtml << "Patch branch last commit SHA: $checkstylePatchReportInfo.commitSha"
    summaryIndexHtml << ('<br />')
    summaryIndexHtml << "Patch branch last commit message: \"$checkstylePatchReportInfo.commitMsg\""
    summaryIndexHtml << ('<br />')
    summaryIndexHtml << "Patch branch last commit timestamp: \"$checkstylePatchReportInfo.commitTime\""
    summaryIndexHtml << ('<br />')
    summaryIndexHtml << ('<br />')
    summaryIndexHtml << "Tested projects: ${projectsStatistic.size()}"
    summaryIndexHtml << ('<br />')
    summaryIndexHtml << "&#177; differences found: ${projectsStatistic.values().sum()[0]}"
    summaryIndexHtml << ('<br />')
    summaryIndexHtml << "Time of report generation: $date"
    summaryIndexHtml << ('</h6>')
}

def getProjectsStatistic(diffDir) {
    def projectsStatistic = new HashMap<>()
    def totalDiff = 0
    def addedDiff = 0
    def removedDiff = 0
    Paths.get(diffDir).toFile().eachFile {
        fileObjf ->
            if (fileObjf.isDirectory()) {
                def projectName = fileObjf.name
                def indexHtmlFile = new File(fileObjf.absolutePath + '/index.html')
                indexHtmlFile.eachLine {
                    line ->
                        def totalPatch = Pattern.compile("id=\"totalPatch\">[0-9]++")
                        def totalPatchMatcher = totalPatch.matcher(line)
                        if (totalPatchMatcher.find()) {
                            def removedPatchLinePattern = Pattern.compile(".(?<totalRemoved>[0-9]++) removed")
                            def removedPatchLineMatcher = removedPatchLinePattern.matcher(line)
                            if (removedPatchLineMatcher.find()) {
                                removedDiff = Integer.valueOf(removedPatchLineMatcher.group('totalRemoved'))
                            } else {
                                removedDiff = 0
                            }

                            def addPatchLinePattern = Pattern.compile("(?<totalAdd>[0-9]++) added")
                            def addPatchLineMatcher = addPatchLinePattern.matcher(line)
                            if (addPatchLineMatcher.find()) {
                                addedDiff = Integer.valueOf(addPatchLineMatcher.group('totalAdd'))
                            } else {
                                addedDiff = 0
                            }
                        }

                        def linePattern = Pattern.compile("totalDiff\">(?<totalDiff>[0-9]++)")
                        def lineMatcher = linePattern.matcher(line)
                        if (lineMatcher.find()) {
                            totalDiff = Integer.valueOf(lineMatcher.group('totalDiff'))
                            def diffSummary = [totalDiff, addedDiff, removedDiff]
                            projectsStatistic.put(projectName, diffSummary)
                        }
                }
            }
    }
    return projectsStatistic
}

def runMavenExecution(srcDir, excludes, checkstyleConfig,
                      checkstyleVersion, extraMvnRegressionOptions) {
    println "Running 'mvn clean' on $srcDir ..."
    def mvnClean = "mvn -e --no-transfer-progress --batch-mode clean"
    executeCmd(mvnClean)
    println "Running Checkstyle on $srcDir ... with excludes {$excludes}"
    def mvnSite = "mvn -e --no-transfer-progress --batch-mode site " +
        "-Dcheckstyle.config.location=$checkstyleConfig -Dcheckstyle.excludes=$excludes"
    if (checkstyleVersion) {
        mvnSite = mvnSite + " -Dcheckstyle.version=$checkstyleVersion"
    }
    if (extraMvnRegressionOptions) {
        mvnSite = mvnSite + " "

        if (!extraMvnRegressionOptions.startsWith("-")) {
            mvnSite = mvnSite + "-"
        }
        mvnSite = mvnSite + extraMvnRegressionOptions
    }
    println(mvnSite)
    executeCmd(mvnSite)
    println "Running Checkstyle on $srcDir - finished"
}

def postProcessCheckstyleReport(targetDir, repoName, repoPath) {
    def reportFile = Paths.get("$targetDir", "checkstyle-result.xml").toFile()
    def oldPath = new File(getOsSpecificPath("src", "main", "java", "$repoName")).absolutePath
    def newContent = reportFile.text.replace(oldPath, getOsSpecificPath("$repoPath"))
    reportFile.text = newContent
}

def copyDir(source, destination) {
    def sourcePath = Paths.get(source)
    def destPath = Paths.get(destination)
    Files.walk(sourcePath).forEach { src ->
        def target = destPath.resolve(sourcePath.relativize(src))
        if (Files.isDirectory(src)) {
            Files.createDirectories(target)
        } else {
            Files.copy(src, target, REPLACE_EXISTING)
        }
    }
}

def moveDir(source, destination) {
    def sourcePath = Paths.get(source)
    def destPath = Paths.get(destination)
    Files.move(sourcePath, destPath, REPLACE_EXISTING)
}

def deleteDir(dir) {
    def path = Paths.get(dir)
    if (Files.exists(path)) {
        Files.walk(path)
            .sorted(Comparator.reverseOrder())
            .forEach { Files.delete(it) }
    }
}

def executeCmd(cmd, dir = new File("").absoluteFile) {
    println "Running command: ${cmd}"
    def osSpecificCmd = getOsSpecificCmd(cmd)
    def proc = osSpecificCmd.execute(null, dir)
    proc.consumeProcessOutput(System.out, System.err)
    proc.waitFor()
    if (proc.exitValue() != 0) {
        throw new GroovyRuntimeException("Error: ${proc.err.text}!")
    }
}

def getOsSpecificCmd(cmd) {
    def osSpecificCmd
    if (System.properties['os.name'].toLowerCase().contains('windows')) {
        osSpecificCmd = "cmd /c $cmd"
    }
    else {
        osSpecificCmd = cmd
    }
    return osSpecificCmd
}

def getOsSpecificPath(String... name) {
    def slash = isWindows() ? "\\" : "/"
    def path = name.join(slash)
    return path
}

def isWindows() {
    return System.properties['os.name'].toLowerCase().contains('windows')
}

def getResetCmd(repoType, commitId) {
    def resetCmd = ''
    switch (repoType) {
        case 'git':
            if (isGitSha(commitId)) {
                resetCmd = "git reset --hard $commitId"
            } else {
                resetCmd = "git reset --hard refs/tags/$commitId"
            }
            break
        default:
            throw new IllegalArgumentException("Error! Unknown $repoType repository.")
    }
    return resetCmd
}

def getLastProjectCommitSha(repoType, srcDestinationDir) {
    def cmd = ''
    switch (repoType) {
        case 'git':
            cmd = "git rev-parse HEAD"
            break
        default:
            throw new IllegalArgumentException("Error! Unknown $repoType repository.")
    }
    def sha = cmd.execute(null, new File("$srcDestinationDir")).text
    // cmd output contains new line character which should be removed
    return sha.replace('\n', '')
}

class Config {
    def localGitRepo
    def shortFilePaths
    def listOfProjects
    def mode

    def baseBranch
    def patchBranch

    def baseConfig
    def patchConfig
    def config

    def reportsDir
    def masterReportsDir
    def patchReportsDir
    def tmpReportsDir
    def tmpMasterReportsDir
    def tmpPatchReportsDir
    def diffDir
    def extraMvnRegressionOptions

    def checkstyleVersion
    def sevntuVersion
    def allowExcludes
    def useShallowClone

    Config(cliOptions) {
        if (cliOptions.localGitRepo) {
            localGitRepo = new File(cliOptions.localGitRepo)
        }

        shortFilePaths = cliOptions.shortFilePaths
        listOfProjects = cliOptions.listOfProjects
        extraMvnRegressionOptions = cliOptions.extraMvnRegressionOptions

        checkstyleVersion = cliOptions.checkstyleVersion
        allowExcludes = cliOptions.allowExcludes
        useShallowClone = cliOptions.useShallowClone

        mode = cliOptions.mode
        if (!mode) {
            mode = 'diff'
        }

        baseBranch = cliOptions.baseBranch
        if (!baseBranch) {
            baseBranch = 'master'
        }
        patchBranch = cliOptions.patchBranch

        baseConfig = cliOptions.baseConfig
        patchConfig = cliOptions.patchConfig
        config = cliOptions.config
        if (config) {
            baseConfig = config
            patchConfig = config
        }

        reportsDir = 'reports'
        masterReportsDir = "$reportsDir/$baseBranch"
        patchReportsDir = "$reportsDir/$patchBranch"

        tmpReportsDir = 'tmp_reports'
        tmpMasterReportsDir = "$tmpReportsDir/$baseBranch"
        tmpPatchReportsDir = "$tmpReportsDir/$patchBranch"

        diffDir = "$reportsDir/diff"
    }

    def isDiffMode() {
        return 'diff'.equals(mode)
    }

    def isSingleMode() {
        return 'single'.equals(mode)
    }

    def getCheckstyleToolBaseConfig() {
        return [
            localGitRepo: localGitRepo,
            branch: baseBranch,
            checkstyleCfg: baseConfig,
            listOfProjects: listOfProjects,
            destDir: tmpMasterReportsDir,
            extraMvnRegressionOptions: extraMvnRegressionOptions,
            allowExcludes:allowExcludes,
            useShallowClone: useShallowClone,
        ]
    }

    def getCheckstyleToolPatchConfig() {
        return [
            localGitRepo: localGitRepo,
            branch: patchBranch,
            checkstyleCfg: patchConfig,
            listOfProjects: listOfProjects,
            destDir: tmpPatchReportsDir,
            extraMvnRegressionOptions: extraMvnRegressionOptions,
            allowExcludes: allowExcludes,
            useShallowClone: useShallowClone,
        ]
    }

    def getDiffToolConfig() {
        return [
            reportsDir: reportsDir,
            masterReportsDir: masterReportsDir,
            patchReportsDir: patchReportsDir,
            baseConfig: baseConfig,
            patchConfig: patchConfig,
            shortFilePaths: shortFilePaths,
            mode: mode,
            allowExcludes: allowExcludes,
            useShallowClone: useShallowClone,
        ]
    }
}

class CheckstyleReportInfo {
    def branch
    def commitSha
    def commitMsg
    def commitTime

    CheckstyleReportInfo(branch, commitSha, commitMsg, commitTime) {
        this.branch = branch
        this.commitSha = commitSha
        this.commitMsg = commitMsg
        this.commitTime = commitTime
    }
}
