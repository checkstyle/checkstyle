import subprocess
import os
import re

# Get all files modified in the last commit
files = subprocess.check_output(['git', 'diff', '--name-only', 'HEAD~1']).decode('utf-8').splitlines()

for f in files:
    if not f.endswith('.java'): continue
    with open(f, 'r') as file:
        lines = file.readlines()
    
    new_lines = []
    i = 0
    while i < len(lines):
        line = lines[i]
        new_lines.append(line)
        # If line ends with { (ignoring whitespace), check if the next line is empty
        if line.strip().endswith('{'):
            while i + 1 < len(lines) and lines[i+1].strip() == '':
                # Skip the empty line
                i += 1
        i += 1

    if new_lines != lines:
        with open(f, 'w') as file:
            file.writelines(new_lines)
