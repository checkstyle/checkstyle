document.addEventListener("DOMContentLoaded", function () {
  const preTags = document.querySelectorAll("pre");

  preTags.forEach((pre) => {
    let code = pre.querySelector("code");

    if (!code) {
      code = document.createElement("code");
      code.textContent = pre.textContent.trim();
      pre.innerHTML = "";
      pre.appendChild(code);
    }

    pre.removeAttribute("tabindex");
    pre.className = "";

    const codeText = code.textContent.trim();
    const langClass = detectXML(codeText) ? "language-xml" : "language-java";

    code.classList.add(langClass);
    pre.classList.add(langClass);
  });

  Prism.highlightAll();
});

function detectXML(codeText) {
 return /^\s*<\?xml|^\s*<[^<>]+>/.test(codeText);
}
