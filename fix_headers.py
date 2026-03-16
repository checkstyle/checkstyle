import os, re

directory = "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/missingjavadocmethod/"
new_line = "ignoreMethodsWithImplementation = (default)false\n"

for filename in os.listdir(directory):
    if not filename.startswith("Input") or not filename.endswith(".java"):
        continue
    filepath = os.path.join(directory, filename)
    with open(filepath, "r") as f:
        content = f.read()

    if "ignoreMethodsWithImplementation" in content:
        continue

    if "ignoreMethodNamesRegex" in content:
        content = re.sub(r"(ignoreMethodNamesRegex[^\n]*\n)", r"\1" + new_line, content, count=1)
    elif "minLineCount" in content:
        content = re.sub(r"(minLineCount)", new_line + r"\1", content, count=1)
    else:
        content = content.replace("*/\n", new_line + "*/\n", 1)

    with open(filepath, "w") as f:
        f.write(content)

print("Done")
