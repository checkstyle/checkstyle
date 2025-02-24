document.addEventListener('DOMContentLoaded', function() {
  const xmlHeader = `<?xml version="1.0"?>
<!DOCTYPE module PUBLIC
  "-//Puppy Crawl//DTD Check Configuration 1.3//EN"
  "https://checkstyle.org/dtds/configuration_1_3.dtd">
`;
  const clipboardIcon = `
    <svg viewBox="0 0 24 24" width="16" height="16" fill="currentColor" aria-hidden="true">
      <path d="M16 1H4c-1.1 0-2 .9-2 2v14h2V3h12V1zm3 4H8c-1.1 0-2 .9-2 2v14
               c0 1.1.9 2 2 2h11c1.1 0 2-.9 2-2V7c0-1.1-.9-2-2-2zm0 16H8V7h11v14z"/>
    </svg>
  `;

  const codeBlocks = document.querySelectorAll('pre > code');

  codeBlocks.forEach(codeBlock => {
    const pre = codeBlock.parentElement;

    if (window.getComputedStyle(pre).position === 'static') {
      pre.style.position = 'relative';
    }

    if (pre.querySelector('.copy-button')) {
      return;
    }

    const button = document.createElement('button');
    button.className = 'copy-button';
    button.innerHTML = clipboardIcon;

    pre.appendChild(button);

    button.addEventListener('click', () => {
      let textToCopy = codeBlock.innerText;

      if (codeBlock.classList.contains('language-xml')) {
        if (!textToCopy.trim().startsWith('<?xml')) {
          textToCopy = xmlHeader + textToCopy;
        }
      }

      navigator.clipboard.writeText(textToCopy)
        .then(() => {
          button.textContent = 'Copied!';
          setTimeout(() => {
            button.innerHTML = clipboardIcon;
          }, 2000);
        })
        .catch(err => {
          console.error('Failed to copy:', err);
        });
    });
  });
});
