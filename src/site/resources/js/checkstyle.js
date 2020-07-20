let scrollButton;

window.addEventListener('load', () => {
    scrollButton = document.querySelector('a[title="toTop"]');
    scrollButton.innerText = 'To Top';
    scrollButton.style.display = 'none';

    scrollButton.addEventListener('click', (e) => {
        e.preventDefault();
        document.documentElement.scrollTop = 0;
    });
});

window.addEventListener('scroll', () => {
    if (document.documentElement.scrollTop > 500) {
        scrollButton.style.display = 'block';
    } else {
        scrollButton.style.display = 'none';
    }
});