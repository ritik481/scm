// document.addEventListener('DOMContentLoaded', () => {
//     const toggleButton = document.getElementById('theme-toggle');
//     const prefersDarkScheme = window.matchMedia('(prefers-color-scheme: dark)');
    
//     function setTheme(theme) {
//         document.documentElement.setAttribute('data-theme', theme);
//         localStorage.setItem('theme', theme);
//     }

//     // Initialize theme: Check localStorage first, then system preference
//     const savedTheme = localStorage.getItem('theme');
//     if (savedTheme) {
//         setTheme(savedTheme);
//     } else if (prefersDarkScheme.matches) {
//         setTheme('dark');
//     } else {
//         setTheme('light');
//     }

//     // Event listener for the toggle button
//     if (toggleButton) {
//         toggleButton.addEventListener('click', () => {
//             const currentTheme = document.documentElement.getAttribute('data-theme');
//             setTheme(currentTheme === 'dark' ? 'light' : 'dark');
//         });
//     }
// });
document.addEventListener('DOMContentLoaded', () => {
    const toggleButton = document.getElementById('theme-toggle');
    const prefersDarkScheme = window.matchMedia('(prefers-color-scheme: dark)');

    function applyThemeStyles(theme) {
        if (theme === 'dark') {
            document.body.style.backgroundColor = '#2e2e2e'; // dark gray
            document.body.style.color = '#f5f5f5';

            if (toggleButton) {
                toggleButton.style.backgroundColor = '#4b5563'; // gray button
                toggleButton.style.color = '#ffffff';
            }
        } else {
            document.body.style.backgroundColor = '#ffffff'; // white
            document.body.style.color = '#111111';

            if (toggleButton) {
                toggleButton.style.backgroundColor = '#e5e7eb'; // light gray button
                toggleButton.style.color = '#111111';
            }
        }
    }

    function setTheme(theme) {
        document.documentElement.setAttribute('data-theme', theme);
        localStorage.setItem('theme', theme);
        applyThemeStyles(theme);
    }

    // Initialize theme: Check localStorage first, then system preference
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme) {
        setTheme(savedTheme);
    } else if (prefersDarkScheme.matches) {
        setTheme('dark');
    } else {
        setTheme('light');
    }

    // Event listener for the toggle button
    if (toggleButton) {
        toggleButton.addEventListener('click', () => {
            const currentTheme = document.documentElement.getAttribute('data-theme');
            setTheme(currentTheme === 'dark' ? 'light' : 'dark');
        });
    }
});
