document.addEventListener('DOMContentLoaded', function () {
    const cards = Array.from(document.querySelectorAll('.catalog-card'));
    const search = document.getElementById('bookSearch');
    const category = document.getElementById('categoryFilter');
    const author = document.getElementById('authorFilter');
    const language = document.getElementById('languageFilter');
    const rating = document.getElementById('ratingFilter');
    const sort = document.getElementById('sortBooks');
    const price = document.getElementById('priceRange');
    const priceValue = document.getElementById('priceValue');
    const count = document.getElementById('resultsCount');
    const empty = document.getElementById('emptyState');
    const pageButtons = Array.from(document.querySelectorAll('[data-page]'));
    let currentPage = 1;
    const pageSize = 8;

    function applyFilters() {
        const query = search.value.trim().toLowerCase();
        const maxPrice = Number(price.value);
        const minimumRating = Number(rating.value);
        let visible = cards.filter(function (card) {
            const matchesQuery = !query || (card.dataset.title + ' ' + card.dataset.author).toLowerCase().includes(query);
            return matchesQuery && (!category.value || card.dataset.category === category.value) && (!author.value || card.dataset.author === author.value) && (!language.value || card.dataset.language === language.value) && Number(card.dataset.rating) >= minimumRating && Number(card.dataset.price) <= maxPrice;
        });
        visible.sort(function (first, second) {
            if (sort.value === 'price-low') return Number(first.dataset.price) - Number(second.dataset.price);
            if (sort.value === 'price-high') return Number(second.dataset.price) - Number(first.dataset.price);
            if (sort.value === 'rating') return Number(second.dataset.rating) - Number(first.dataset.rating);
            if (sort.value === 'popular') return Number(second.dataset.popular) - Number(first.dataset.popular);
            return cards.indexOf(first) - cards.indexOf(second);
        });
        cards.forEach(function (card) { card.classList.add('d-none'); });
        const start = (currentPage - 1) * pageSize;
        visible.slice(start, start + pageSize).forEach(function (card) { card.classList.remove('d-none'); });
        count.textContent = visible.length;
        empty.classList.toggle('d-none', visible.length > 0);
        pageButtons.forEach(function (button) {
            const page = button.dataset.page;
            button.classList.toggle('active', page === String(currentPage));
            if (page === 'previous') button.disabled = currentPage === 1;
            if (page === 'next') button.disabled = currentPage >= Math.max(1, Math.ceil(visible.length / pageSize));
        });
    }

    [search, category, author, language, rating, sort, price].forEach(function (control) { control.addEventListener('input', function () { currentPage = 1; applyFilters(); }); });
    document.querySelectorAll('.sidebar-link').forEach(function (button) { button.addEventListener('click', function () { category.value = button.dataset.category; document.querySelectorAll('.sidebar-link').forEach(function (item) { item.classList.remove('active'); }); button.classList.add('active'); currentPage = 1; applyFilters(); }); });
    price.addEventListener('input', function () { priceValue.textContent = '₹' + Number(price.value).toLocaleString('en-IN'); });
    pageButtons.forEach(function (button) { button.addEventListener('click', function () { const maxPage = Math.max(1, Math.ceil(cards.filter(function (card) { return !card.classList.contains('d-none'); }).length / pageSize)); if (button.dataset.page === 'previous') currentPage = Math.max(1, currentPage - 1); else if (button.dataset.page === 'next') currentPage = Math.min(maxPage, currentPage + 1); else currentPage = Number(button.dataset.page); applyFilters(); }); });
    document.querySelectorAll('.wishlist-btn').forEach(function (button) { button.addEventListener('click', function () { button.classList.toggle('is-saved'); button.querySelector('i').classList.toggle('far'); button.querySelector('i').classList.toggle('fas'); }); });
    if (window.AOS) AOS.refresh();
    applyFilters();
});
