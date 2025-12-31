console.log("Script loaded");

let currentTheme = getTheme();

document.addEventListener("DOMContentLoaded", function() {
    // Apply current theme on page load
    changePageTheme(currentTheme, "");

    const changeThemeButton = document.getElementById("theme_change_button");
    if (changeThemeButton) {
        changeThemeButton.addEventListener("click", (event) => {
            const oldTheme = currentTheme;
            console.log("Theme change button clicked");

            if (currentTheme === "dark") {
                currentTheme = "light";
            } else {
                currentTheme = "dark";
            }
            changePageTheme(currentTheme, oldTheme);
        });
    } else {
        console.error("Theme change button not found");
    }

    // Force initial theme application
    if (currentTheme === "dark") {
        document.documentElement.classList.add("dark");
        document.documentElement.classList.remove("light");
        // Update button state if present
        const btn = document.getElementById("theme_change_button");
        if (btn) {
            btn.setAttribute('aria-pressed', 'true');
            btn.querySelector('i')?.classList.remove('fa-circle-half-stroke');
            btn.querySelector('i')?.classList.add('fa-sun');
        }
    } else {
        document.documentElement.classList.add("light");
        document.documentElement.classList.remove("dark");
        const btn = document.getElementById("theme_change_button");
        if (btn) {
            btn.setAttribute('aria-pressed', 'false');
            btn.querySelector('i')?.classList.remove('fa-circle-half-stroke');
            btn.querySelector('i')?.classList.add('fa-moon');
        }
    }
});

function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

function getTheme() {
    let theme = localStorage.getItem("theme");
    return theme === null ? "light" : theme;
}

function changePageTheme(theme, oldTheme) {
    setTheme(theme);
    if (oldTheme) {
        document.documentElement.classList.remove(oldTheme);
    }
    if (theme === "dark") {
        document.documentElement.classList.add("dark");
        document.documentElement.classList.remove("light");
    } else {
        document.documentElement.classList.add("light");
        document.documentElement.classList.remove("dark");
    }
    console.log("Theme changed to:", theme, "Classes:", document.documentElement.className);

    // Update toggle button appearance and aria state
    const btn = document.getElementById("theme_change_button");
    if (btn) {
        if (theme === 'dark') {
            btn.setAttribute('aria-pressed', 'true');
            btn.querySelector('i')?.classList.remove('fa-moon');
            btn.querySelector('i')?.classList.add('fa-sun');
            btn.classList.remove('bg-gray-100', 'text-gray-700');
            btn.classList.add('bg-gray-800', 'text-gray-200');
        } else {
            btn.setAttribute('aria-pressed', 'false');
            btn.querySelector('i')?.classList.remove('fa-sun');
            btn.querySelector('i')?.classList.add('fa-moon');
            btn.classList.remove('bg-gray-800', 'text-gray-200');
            btn.classList.add('bg-gray-100', 'text-gray-700');
        }
    }

    // Send theme to server
    fetch('/set-theme', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ theme: theme }),
    }).then(response => {
        if (response.ok) {
            console.log('Theme saved to server');
        } else {
            console.error('Failed to save theme to server');
        }
    });
}
