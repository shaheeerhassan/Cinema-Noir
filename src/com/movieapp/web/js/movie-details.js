const urlParams = new URLSearchParams(window.location.search);
const movieId = urlParams.get('id');

async function loadMovieDetails() {
    if (!movieId) {
        window.location.href = 'index.html';
        return;
    }

    try {
        const details = await apiFetch(`/movies/details?id=${movieId}`);
        renderDetails(details);
        checkWatchlistStatus();
    } catch (error) {
        console.error('Failed to load movie details:', error);
    }
}

function renderDetails(details) {
    const { movie, actors, directors, genres, reviews, averageRating } = details;
    
    document.getElementById('movieTitle').textContent = movie.title;
    document.getElementById('yearBadge').textContent = movie.releaseYear;
    document.getElementById('ratingBadge').innerHTML = `<span class="material-symbols-outlined text-secondary text-sm fill mr-1">star</span>${averageRating.toFixed(1)}`;
    document.getElementById('description').textContent = movie.description;
    
    const posterName = movie.posterUrl.split('/').pop();
    const posterPath = `../resources/posters/${posterName}`;
    document.getElementById('posterImg').src = posterPath;
    document.getElementById('heroBackdrop').src = posterPath;
    
    // Cast
    const castGrid = document.getElementById('castGrid');
    castGrid.innerHTML = actors.map(actor => {
        const photoName = actor.photoUrl.split('/').pop();
        const photoPath = `../resources/actors/${photoName}`;
        return `
        <div class="flex flex-col items-center text-center group">
            <div class="w-20 h-20 md:w-24 md:h-24 rounded-full overflow-hidden border-2 border-white/10 group-hover:border-secondary transition-colors duration-300 mb-3">
                <img src="${photoPath}" alt="${actor.fullName}" class="w-full h-full object-cover grayscale group-hover:grayscale-0 transition-all" onerror="this.src='https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?q=80&w=1000&auto=format&fit=crop'" />
            </div>
            <p class="text-sm font-bold group-hover:text-secondary transition-colors">${actor.fullName}</p>
        </div>
    `;}).join('');
    
    // Genres
    const genresList = document.getElementById('genresList');
    genresList.innerHTML = genres.map(g => `
        <span class="px-4 py-1.5 rounded-lg bg-surface-container-high border border-white/5 text-sm font-medium hover:border-secondary/50 transition cursor-default">${g.genreName}</span>
    `).join('');
    
    // Reviews
    const reviewsList = document.getElementById('reviewsList');
    if (reviews.length === 0) {
        reviewsList.innerHTML = '<p class="text-on-surface-variant italic">No reviews yet. Be the first to share your thoughts.</p>';
    } else {
        reviewsList.innerHTML = reviews.map(r => `
            <div class="bg-surface-container-low/40 border border-white/5 p-5 rounded-xl">
                <div class="flex items-center justify-between mb-3">
                    <div class="flex items-center gap-2">
                        <div class="w-8 h-8 rounded-full bg-secondary/10 flex items-center justify-center text-secondary font-bold text-xs">
                            ${r.username[0].toUpperCase()}
                        </div>
                        <span class="font-bold text-sm">${r.username}</span>
                    </div>
                    <div class="flex items-center gap-1 bg-black/40 px-2 py-1 rounded text-xs">
                        <span class="material-symbols-outlined text-secondary text-[14px] fill">star</span>
                        <span>${r.rating.toFixed(1)}</span>
                    </div>
                </div>
                <p class="text-on-surface-variant text-sm leading-relaxed">${r.comment}</p>
            </div>
        `).join('');
    }

    // Show review form if logged in
    const user = getLoggedInUser();
    if (user) {
        document.getElementById('reviewForm').classList.remove('hidden');
    }
}

async function checkWatchlistStatus() {
    const user = getLoggedInUser();
    if (!user) return;
    
    try {
        const watchlist = await apiFetch(`/watchlist?userId=${user.userId}`);
        const isInWatchlist = watchlist.some(m => m.movieId == movieId);
        const btn = document.getElementById('watchlistBtn');
        if (isInWatchlist) {
            btn.textContent = 'Remove from Watchlist';
            btn.classList.add('bg-error-container', 'text-white');
            btn.classList.remove('bg-secondary', 'text-on-secondary');
        } else {
            btn.textContent = 'Add to Watchlist';
            btn.classList.remove('bg-error-container', 'text-white');
            btn.classList.add('bg-secondary', 'text-on-secondary');
        }
    } catch (error) {
        console.error('Watchlist check error:', error);
    }
}

async function toggleWatchlist() {
    const user = getLoggedInUser();
    if (!user) {
        window.location.href = 'login.html';
        return;
    }

    const btn = document.getElementById('watchlistBtn');
    const action = btn.textContent.includes('Add') ? 'add' : 'remove';

    try {
        await apiFetch('/watchlist', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userId: user.userId, movieId: movieId, action: action })
        });
        checkWatchlistStatus();
    } catch (error) {
        alert('Operation failed: ' + error.message);
    }
}

async function submitReview() {
    const user = getLoggedInUser();
    const rating = parseFloat(document.getElementById('ratingInput').value);
    const comment = document.getElementById('commentInput').value;

    if (isNaN(rating) || rating < 1.0 || rating > 10.0) {
        alert('Please enter a rating between 1.0 and 10.0');
        return;
    }

    if (!comment) {
        alert('Please write a comment');
        return;
    }

    try {
        await apiFetch('/reviews', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                userId: user.userId,
                movieId: movieId,
                rating: rating,
                comment: comment
            })
        });
        document.getElementById('commentInput').value = '';
        loadMovieDetails(); // Refresh reviews
    } catch (error) {
        alert('Failed to submit review: ' + error.message);
    }
}

document.addEventListener('DOMContentLoaded', loadMovieDetails);
