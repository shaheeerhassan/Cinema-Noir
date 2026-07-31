const BASE_URL = 'http://localhost:8080/api';

async function apiFetch(endpoint, options = {}) {
    try {
        const response = await fetch(`${BASE_URL}${endpoint}`, options);
        if (!response.ok) {
            const error = await response.json().catch(() => ({}));
            throw new Error(error.message || `API error: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error(`Fetch error for ${endpoint}:`, error);
        throw error;
    }
}

function getLoggedInUser() {
    const userJson = localStorage.getItem('user');
    return userJson ? JSON.parse(userJson) : null;
}

function logout() {
    localStorage.removeItem('user');
    window.location.href = 'login.html';
}

function updateAuthUI() {
    const user = getLoggedInUser();
    const userInfo = document.getElementById('userInfo');
    const authButtons = document.getElementById('authButtons');
    const usernameSpan = document.getElementById('username');

    if (user) {
        if (userInfo) userInfo.classList.remove('hidden');
        if (authButtons) authButtons.classList.add('hidden');
        if (usernameSpan) usernameSpan.textContent = user.username;
    } else {
        if (userInfo) userInfo.classList.add('hidden');
        if (authButtons) authButtons.classList.remove('hidden');
    }
}

document.addEventListener('DOMContentLoaded', updateAuthUI);
