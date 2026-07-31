async function loadWatchlist() {
    const user = getLoggedInUser();
    if (!user) {
        window.location.href = 'login.html';
        return;
    }

    try {
        const movies = await apiFetch(`/watchlist?userId=${user.userId}`);
        displayWatchlist(movies);
    } catch (error) {
        console.error('Failed to load watchlist:', error);
    }
}

function displayWatchlist(movies) {
    const grid = document.getElementById('watchlistGrid');
    const empty = document.getElementById('emptyState');
    if (!grid) return;

    if (movies.length === 0) {
        grid.classList.add('hidden');
        empty.classList.remove('hidden');
        return;
    }

    empty.classList.add('hidden');
    grid.classList.remove('hidden');
    grid.innerHTML = movies.map(movie => {
        const posterName = movie.posterUrl.split('/').pop();
        const posterPath = `../resources/posters/${posterName}`;
        
        return `
        <div class="group relative bg-surface-container-low rounded-xl overflow-hidden border border-white/5 hover:border-secondary/50 transition-all duration-300 hover:shadow-glow">
            <div class="aspect-[2/3] overflow-hidden cursor-pointer" onclick="window.location.href='movie-details.html?id=${movie.movieId}'">
                <img src="${posterPath}" 
                     alt="${movie.title}" 
                     class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-500" 
                     onerror="this.src='https://images.unsplash.com/photo-1485846234645-a62644f84728?q=80&w=2059&auto=format&fit=crop'" />
            </div>
            <div class="p-4 bg-gradient-to-t from-black via-black/40 to-transparent absolute bottom-0 left-0 w-full translate-y-2 group-hover:translate-y-0 transition-transform">
                <h3 class="font-bold text-sm line-clamp-1 group-hover:text-secondary transition-colors">${movie.title}</h3>
                <div class="flex items-center justify-between mt-1">
                    <p class="text-[10px] text-on-surface-variant uppercase tracking-widest">${movie.releaseYear}</p>
                    <button onclick="removeFromWatchlist(${movie.movieId})" class="text-error-container hover:text-white transition-colors">
                        <span class="material-symbols-outlined text-sm fill">delete</span>
                    </button>
                </div>
            </div>
        </div>
    `;}).join('');
}

async function removeFromWatchlist(movieId) {
    const user = getLoggedInUser();
    try {
        await apiFetch('/watchlist', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userId: user.userId, movieId: movieId, action: 'remove' })
        });
        loadWatchlist();
    } catch (error) {
        alert('Failed to remove: ' + error.message);
    }
}

document.addEventListener('DOMContentLoaded', loadWatchlist);
