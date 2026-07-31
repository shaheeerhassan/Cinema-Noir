async function loadActors() {
    try {
        const actors = await apiFetch('/actors');
        displayActors(actors);
    } catch (error) {
        console.error('Failed to load actors:', error);
    }
}

function displayActors(actors) {
    const grid = document.getElementById('actorsGrid');
    const empty = document.getElementById('emptyState');
    if (!grid) return;

    if (actors.length === 0) {
        grid.innerHTML = '';
        empty.classList.remove('hidden');
        return;
    }

    empty.classList.add('hidden');
    grid.innerHTML = actors.map(actor => {
        const photoName = actor.photoUrl.split('/').pop();
        const photoPath = `../resources/actors/${photoName}`;
        return `
        <div class="bg-surface-container-low border border-white/5 rounded-2xl p-5 flex flex-col items-center text-center group hover:border-secondary transition-all duration-300 cursor-pointer" onclick="openActorMovies(${actor.actorId}, '${actor.fullName}')">
            <div class="w-24 h-24 md:w-32 md:h-32 rounded-full overflow-hidden border-2 border-white/10 group-hover:border-secondary transition-colors duration-500 mb-4">
                <img src="${photoPath}" alt="${actor.fullName}" class="w-full h-full object-cover grayscale group-hover:grayscale-0 group-hover:scale-110 transition-all duration-500" onerror="this.src='https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?q=80&w=1000&auto=format&fit=crop'" />
            </div>
            <h3 class="font-bold text-lg group-hover:text-secondary transition-colors">${actor.fullName}</h3>
            <p class="text-xs text-on-surface-variant uppercase tracking-widest mt-1">Actor</p>
        </div>
    `;}).join('');
}

async function openActorMovies(actorId, actorName) {
    document.getElementById('modalTitle').textContent = `Movies starring ${actorName}`;
    const content = document.getElementById('modalContent');
    content.innerHTML = '<div class="col-span-full text-center py-10"><p>Loading movies...</p></div>';
    document.getElementById('moviesModal').classList.remove('hidden');
    document.body.style.overflow = 'hidden';

    try {
        const movies = await apiFetch(`/movies/actor?id=${actorId}`);
        if (movies.length === 0) {
            content.innerHTML = '<div class="col-span-full text-center py-10"><p>No movies found for this actor.</p></div>';
        } else {
            content.innerHTML = movies.map(movie => {
                const posterName = movie.posterUrl.split('/').pop();
                const posterPath = `../resources/posters/${posterName}`;
                return `
                <div class="group cursor-pointer" onclick="window.location.href='movie-details.html?id=${movie.movieId}'">
                    <div class="aspect-[2/3] rounded-lg overflow-hidden border border-white/10 group-hover:border-secondary transition-all">
                        <img src="${posterPath}" alt="${movie.title}" class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-500" onerror="this.src='https://images.unsplash.com/photo-1485846234645-a62644f84728?q=80&w=2059&auto=format&fit=crop'" />
                    </div>
                    <p class="mt-2 text-xs font-bold line-clamp-1 group-hover:text-secondary transition-colors">${movie.title}</p>
                    <p class="text-[10px] text-on-surface-variant">${movie.releaseYear}</p>
                </div>
            `;}).join('');
        }
    } catch (error) {
        content.innerHTML = `<div class="col-span-full text-center py-10 text-error"><p>Error loading movies: ${error.message}</p></div>`;
    }
}

function closeModal() {
    document.getElementById('moviesModal').classList.add('hidden');
    document.body.style.overflow = '';
}

async function searchActors() {
    const keyword = document.getElementById('actorSearch').value;
    try {
        const url = keyword ? `/actors?keyword=${encodeURIComponent(keyword)}` : '/actors';
        const actors = await apiFetch(url);
        displayActors(actors);
    } catch (error) {
        console.error('Actor search error:', error);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    loadActors();
    document.getElementById('actorSearch')?.addEventListener('input', (e) => {
        searchActors();
    });
});
