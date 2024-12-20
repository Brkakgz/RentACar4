const BASE_PATH = "http://localhost:8080/api/auth/";

// Login işlemi
async function login() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch(BASE_PATH + "login", {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });

        if (!response.ok) {
            throw new Error("Login failed!");
        }

        const data = await response.json();
        localStorage.setItem('jwtToken', data.token);

        if (data.role === 'ADMIN') {
            window.location.href = "admin/admin-dashboard.html";
        } else {
            window.location.href = "user/user-dashboard.html";
        }

    } catch (error) {
        alert("Login failed: " + error.message);
    }
}

// Register işlemi
async function register() {
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch(BASE_PATH + "register", {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ firstName, lastName, username, password })
        });

        if (!response.ok) {
            throw new Error("Registration failed!");
        }

        alert("Registration successful! You can now login.");
        window.location.href = "login.html";

    } catch (error) {
        alert("Registration failed: " + error.message);
    }
}
