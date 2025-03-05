document.addEventListener("DOMContentLoaded", function () {
  const xmlHeader = `<?xml version="1.0"?>
<!DOCTYPE module PUBLIC
  "-//Puppy Crawl//DTD Check Configuration 1.3//EN"
  "https://checkstyle.org/dtds/configuration_1_3.dtd">
`;

  const clipboardIcon = `
    <svg viewBox="0 0 24 24" width="16" height="16" fill="currentColor" aria-hidden="true">
      <path d="M16 1H4c-1.1 0-2 .9-2 2v14h2V3h12V1zm3 4H8c-1.1 0-2 .9-2 2v14
               c0 1.1 0 2 2 2h11c1.1 0 2-.9 2-2V7c0-1.1-.9-2-2-2zm0 16H8V7h11v14z"/>
    </svg>
  `;

  document.querySelectorAll(".wrapper").forEach(wrapper => {
    const pre = wrapper.querySelector("pre");
    if (!pre) return;

    const codeBlock = pre.querySelector("code");
    if (!codeBlock) return;

    const copyButton = document.createElement("button");
    copyButton.className = "copy-button";
    copyButton.innerHTML = clipboardIcon;

    wrapper.appendChild(copyButton);

    copyButton.addEventListener("click", () => {
      let textToCopy = codeBlock.innerText.trim();

      if (codeBlock.classList.contains("language-xml")) {
        if (
          textToCopy.toLowerCase().includes('<module name="checker">') &&
          !textToCopy.startsWith("<?xml")
        ) {
          textToCopy = xmlHeader + textToCopy;
        }
      }

      navigator.clipboard.writeText(textToCopy)
        .then(() => {
          copyButton.textContent = "Copied!";
          setTimeout(() => {
            copyButton.innerHTML = clipboardIcon;
          }, 2000);
        })
        .catch(err => {
          console.error("Failed to copy:", err);
        });
    });
  });
});
