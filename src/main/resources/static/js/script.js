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
    if (theme === "dark") {
        document.documentElement.classList.add("dark");
    } else {
        document.documentElement.classList.remove("dark");
    }
    console.log("Theme changed to:", theme, "Classes:", document.documentElement.className);

    // Update toggle button aria state
    const btn = document.getElementById("theme_change_button");
    if (btn) {
        if (theme === 'dark') {
            btn.setAttribute('aria-pressed', 'true');
        } else {
            btn.setAttribute('aria-pressed', 'false');
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
