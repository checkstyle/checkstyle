# 修复 Issue #20954：违规消息注释格式规范化

## 背景

Checkstyle 的 `InlineConfigParser` 负责解析测试输入文件中的违规注释。这些注释用于验证 Checkstyle 的实际输出是否与预期一致。

**问题**：部分测试文件中的违规消息注释使用了未加引号的意译说明文字，而非 Checkstyle 实际输出的真实引用子字符串。例如：
```java
// violation, unused variable 'a'  // 错误：未加引号
```

这导致 `InlineConfigParser` 的消息捕获组无法激活，这些注释被静默地视为"未检查的违规"——消息文本从未被真正验证。相关文件随后被添加到抑制列表（`SUPPRESSED_VALIDATE_MESSAGE_FILES`）中以使构建通过。

## 修复目标

1. 将受影响的测试文件中的违规消息注释重写为使用真实消息的引用子字符串
2. 从抑制列表中移除已修复的文件条目

## 修复范围

### 已修复的文件（注释格式已规范化）

#### finalparameters 模块
- `InputFinalParameters.java` - 已经是正确格式，从抑制列表移除
- `InputFinalParameters3.java` - 已经是正确格式，从抑制列表移除
- `InputFinalParametersPatternVariables.java` - 已经是正确格式，从抑制列表移除

#### coding/superfinalize 模块
- `InputSuperFinalizeVariations.java` - 修复 3 处注释：
  - `// violation, Method 'finalize' should call 'super.finalize'` → `// violation 'Method 'finalize' should call 'super.finalize''`
  - `// violation 2 lines below "Method 'finalize' should call 'super.finalize'"` → `// violation 2 lines below 'Method 'finalize' should call 'super.finalize''`

#### coding/unusedlocalvariable 模块
- `InputUnusedLocalVariable3.java` - 修复 1 处注释
- `InputUnusedLocalVariableNestedClasses4.java` - 修复 2 处注释
- `InputUnusedLocalVariableNestedClasses5.java` - 修复 3 处注释
- `InputUnusedLocalVariableNestedClasses6.java` - 修复 2 处注释
- `InputUnusedLocalVariableNestedClasses7.java` - 修复 6 处注释
- `InputUnusedLocalVariableAllowNamedPatternVariables.java` - 修复 2 处注释
- `InputUnusedLocalVariablePatternVariables2.java` - 修复 2 处注释

#### coding/illegaltype 模块
- `InputIllegalTypeTestIgnoreMethodNames.java` - 修复 1 处注释（补全完整消息）
- `InputIllegalTypeTestLegalAbstractClassNames.java` - 修复 1 处注释（补全完整消息）

#### imports/avoidstarimport 模块
- `InputAvoidStarImportExcludes.java` - 已经是正确格式，从抑制列表移除

#### regexp/regexpmultiline 模块
- `InputRegexpMultilineSemantic2.java` - 修复 1 处注释
- `InputRegexpMultilineSemantic5.java` - 修复 3 处注释
- `InputRegexpMultilineSemantic7.java` - 修复文件头注释
- `InputRegexpMultilineSemantic8.java` - 修复文件头注释
- `InputRegexpMultilineMultilineSupport.java` - 修复 1 处注释
- `InputRegexpMultilineMultilineSupport2.java` - 修复 2 处注释

### 仍保留在抑制列表中的文件

以下文件仍保留在 `SUPPRESSED_VALIDATE_MESSAGE_FILES` 中，原因是文件不存在（可能已被重命名或删除）：

- `checks/finalparameters/InputFinalParametersRecordForLoopPatternVariables.java`
- `checks/coding/declarationorder/Example1.java`, `Example2.java`, `Example3.java`
- `checks/coding/equalshashcode/Example1.java`
- `checks/coding/nestedifdepth/Example1.java`, `Example2.java`
- `checks/coding/nestedtrydepth/Example1.java`, `Example2.java`
- `checks/coding/noclone/Example1.java`
- `checks/coding/simplifybooleanreturn/Example1.java`
- `checks/coding/unusedlocalvariable/Example1.java`, `Example2.java`, `Example4.java`
- `checks/coding/unusedlocalvariable/InputUnusedLocalVariablePatternVariables.java`
- `checks/coding/unusedlocalvariable/InputUnusedLocalVariablePatternVariablesAllowUnnamed.java`
- `checks/coding/unusedlocalvariable/InputUnusedLocalVariablePatternVariablesCondition.java`
- `checks/coding/unusedlocalvariable/InputUnusedLocalVariablePatternVariablesCondition2.java`
- `checks/coding/unusedlocalvariable/InputUnusedLocalVariableUnnamedTryCatch.java`
- `checks/imports/importorder/Example10.java`
- `checks/metrics/npathcomplexity/Example1.java`, `Example2.java`
- `checks/naming/recordcomponentname/Example1.java`, `Example2.java`
- `checks/naming/recordtypeparametername/Example1.java`, `Example2.java`
- `checks/regexp/regexpsingleline/Example2.java`, `UseCase1.java`
- `checks/sizes/recordcomponentnumber/Example1.java`, `Example2.java`
- `checks/whitespace/separatorwrap/Example1.java`
- `com/google/checkstyle/test/chapter5naming/rule522classnames/InputClassNamesWithUnderscore.java`
- `com/google/checkstyle/test/chapter5naming/rule53camelcase/InputUnderscoreUsedInNames.java`
- `com/openjdk/checkstyle/test/chapterformatting/ruleorderofconstructorsandoverloadedmethods/InputOrderOfConstructorsAndOverloadedMethodsOne.java`
- `com/openjdk/checkstyle/test/chapternaming/ruletypevariables/InputTypeVariablesOne.java`
- `com/openjdk/checkstyle/test/chapternaming/rulevariables/InputVariablesInvalid.java`

## 修改模式总结

1. **简单替换**：将 `// violation, 说明文字` 改为 `// violation '实际消息'`
2. **带方向的替换**：将 `// violation above/below, 说明文字` 改为 `// violation above/below '实际消息'`
3. **多行替换**：将 `// violation N lines above/below "消息"` 改为 `// violation N lines above/below '消息'`
4. **文件头注释**：将 `/* // violation, 说明文字.` 改为 `/* // violation '说明文字.'`

## 验证方法

运行以下命令验证修改：

```bash
mvn test -Dtest=InlineConfigParserTest -Denforcer.skip=true
```

## 注意事项

1. 消息文本必须与 Checkstyle 实际输出的消息完全一致
2. 使用单引号包裹消息文本
3. 如果消息本身包含单引号，需要特殊处理（如 `InputSuperFinalizeVariations.java` 中的嵌套引号）
