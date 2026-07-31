async function loadMovies() {
    try {
        const movies = await apiFetch('/movies');
        displayMovies(movies);
        
        if (movies.length > 0) {
            const heroMovie = movies[Math.floor(Math.random() * movies.length)];
            const heroImage = document.getElementById('heroImage');
            if (heroImage && heroMovie.posterUrl) {
                const posterName = heroMovie.posterUrl.split('/').pop();
                heroImage.style.backgroundImage = `url('../resources/posters/${posterName}')`;
            }
        }
    } catch (error) {
        console.error('Failed to load movies:', error);
    }
}

function displayMovies(movies) {
    const grid = document.getElementById('movieGrid');
    if (!grid) return;
    
    grid.innerHTML = movies.map(movie => {
        const posterName = movie.posterUrl.split('/').pop();
        const posterPath = `../resources/posters/${posterName}`;
        
        return `
        <div class="group relative bg-surface-container-low rounded-xl overflow-hidden border border-white/5 hover:border-secondary/50 transition-all duration-300 hover:shadow-glow cursor-pointer" onclick="window.location.href='movie-details.html?id=${movie.movieId}'">
            <div class="aspect-[2/3] overflow-hidden">
                <img src="${posterPath}" alt="${movie.title}" class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-500" onerror="this.src='https://images.unsplash.com/photo-1485846234645-a62644f84728?q=80&w=2059&auto=format&fit=crop'" />
            </div>
            <div class="p-4 bg-gradient-to-t from-black via-black/40 to-transparent absolute bottom-0 left-0 w-full translate-y-2 group-hover:translate-y-0 transition-transform">
                <div class="flex items-center gap-1.5 mb-1">
                    <span class="material-symbols-outlined text-secondary text-sm fill">star</span>
                    <span class="text-xs font-bold">${movie.averageRating.toFixed(1)}</span>
                </div>
                <h3 class="font-bold text-sm line-clamp-1 group-hover:text-secondary transition-colors">${movie.title}</h3>
                <p class="text-[10px] text-on-surface-variant uppercase tracking-widest mt-1">${movie.releaseYear}</p>
            </div>
        </div>
    `;}).join('');
}

async function applyFilters() {
    const keyword = document.getElementById('searchInput').value;
    const year = document.getElementById('yearFilter').value;
    const genreId = document.getElementById('genreFilter').value;
    
    let url = '/movies';
    if (keyword) {
        url = `/movies?keyword=${encodeURIComponent(keyword)}`;
    } else if (year || genreId) {
        url = `/movies?year=${year}&genreId=${genreId}`;
    }
    
    try {
        const movies = await apiFetch(url);
        displayMovies(movies);
        scrollToMovies();
    } catch (error) {
        console.error('Filter error:', error);
    }
}

function scrollToMovies() {
    document.getElementById('moviesSection')?.scrollIntoView({ behavior: 'smooth' });
}

async function loadGenres() {
    try {
        const genres = await apiFetch('/genres');
        const select = document.getElementById('genreFilter');
        if (select) {
            select.innerHTML = '<option value="">All Genres</option>' + 
                genres.map(g => `<option value="${g.genreId}">${g.genreName}</option>`).join('');
        }
    } catch (error) {
        console.error('Failed to load genres:', error);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    loadMovies();
    loadGenres();
    
    // Allow enter key on search
    document.getElementById('searchInput')?.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') applyFilters();
    });
});
