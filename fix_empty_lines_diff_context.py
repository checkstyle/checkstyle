import subprocess
import os
import re

diff_output = subprocess.check_output(['git', 'diff', 'HEAD~1']).decode('utf-8')

files_to_edit = {}
current_file = None
current_line = 0

for line in diff_output.splitlines():
    if line.startswith('+++ b/'):
        current_file = line[6:]
        if current_file not in files_to_edit:
            files_to_edit[current_file] = []
    elif line.startswith('@@'):
        # @@ -606,9 +606,17 @@
        m = re.match(r'^@@ -\d+(?:,\d+)? \+(\d+)(?:,\d+)? @@', line)
        if m:
            current_line = int(m.group(1))
    elif line.startswith('+') and not line.startswith('+++'):
        added_text = line[1:]
        files_to_edit[current_file].append((current_line, added_text))
        current_line += 1
    elif line.startswith('-') and not line.startswith('---'):
        pass
    else:
        if not line.startswith('\\') and not line.startswith('---'):
            current_line += 1

lines_removed = 0
for f, additions in files_to_edit.items():
    if not f.endswith('.java'): continue
    if not os.path.exists(f): continue
    
    with open(f, 'r') as file:
        lines = file.read().splitlines()
    
    additions.sort(reverse=True)
    
    for line_num, text in additions:
        if text.strip() == '':
            idx = line_num - 1
            if idx < len(lines) and idx > 0:
                if lines[idx].strip() == '':
                    # Now reliably check if previous non-empty line ends with `{`
                    prev_idx = idx - 1
                    while prev_idx >= 0 and lines[prev_idx].strip() == '':
                        prev_idx -= 1
                    
                    if prev_idx >= 0 and lines[prev_idx].strip().endswith('{'):
                        # This empty line was ADDED and it follows `{`! Remove it!
                        del lines[idx]
                        lines_removed += 1

    if lines_removed > 0:
        with open(f, 'w') as file:
            file.write('\n'.join(lines) + '\n')

print(f"Removed {lines_removed} lines.")
