
// ========================================
// Review System
// ========================================
function initReviewSystem() {
    const openReviewBtn = document.getElementById('openReviewBtn');
    const reviewModal = document.getElementById('reviewModal');
    const closeReview = document.getElementById('closeReview');
    const reviewForm = document.getElementById('reviewForm');
    const starRating = document.getElementById('starRating');
    const stars = starRating?.querySelectorAll('.star');
    const reviewRatingInput = document.getElementById('reviewRating');

    if (!openReviewBtn || !reviewModal) return;

    // Open review modal
    openReviewBtn.addEventListener('click', () => {
        reviewModal.classList.add('active');
        updateStarDisplay(5); // Default to 5 stars
    });

    // Close review modal
    closeReview.addEventListener('click', () => {
        reviewModal.classList.remove('active');
    });

    // Close when clicking outside
    reviewModal.addEventListener('click', (e) => {
        if (e.target === reviewModal) {
            reviewModal.classList.remove('active');
        }
    });

    // Star rating interaction
    if (stars) {
        let selectedRating = 5;

        stars.forEach(star => {
            star.addEventListener('click', () => {
                selectedRating = parseInt(star.dataset.rating);
                reviewRatingInput.value = selectedRating;
                updateStarDisplay(selectedRating);
            });

            star.addEventListener('mouseenter', () => {
                const rating = parseInt(star.dataset.rating);
                updateStarDisplay(rating);
            });
        });

        starRating.addEventListener('mouseleave', () => {
            updateStarDisplay(selectedRating);
        });

        function updateStarDisplay(rating) {
            stars.forEach((star, index) => {
                if (index < rating) {
                    star.style.color = '#f59e0b';
                } else {
                    star.style.color = '#d1d5db';
                }
            });
        }
    }

    // Handle form submission
    if (reviewForm) {
        reviewForm.addEventListener('submit', (e) => {
            e.preventDefault();

            const name = document.getElementById('reviewName').value;
            const rating = document.getElementById('reviewRating').value;
            const text = document.getElementById('reviewText').value;

            // Save review to localStorage
            const review = {
                name: name,
                rating: parseInt(rating),
                text: text,
                date: new Date().toISOString(),
                verified: true
            };

            let reviews = JSON.parse(localStorage.getItem('customerReviews') || '[]');
            reviews.unshift(review); // Add to beginning
            localStorage.setItem('customerReviews', JSON.stringify(reviews));

            // Show success notification
            showNotification('Thank you for your review! It has been submitted successfully. 🎉');

            // Reset form and close modal
            reviewForm.reset();
            reviewRatingInput.value = 5;
            updateStarDisplay(5);
            reviewModal.classList.remove('active');
        });
    }
}

// Load and display user reviews on about page
function loadUserReviews() {
    const reviewsContainer = document.getElementById('userReviews');
    if (!reviewsContainer) return;

    const reviews = JSON.parse(localStorage.getItem('customerReviews') || '[]');

    if (reviews.length === 0) return;

    reviews.forEach(review => {
        const initial = review.name.charAt(0).toUpperCase();
        const stars = '★'.repeat(review.rating) + '☆'.repeat(5 - review.rating);

        // Generate random gradient background
        const gradients = [
            'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
            'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)',
            'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)',
            'linear-gradient(135deg, #fa709a 0%, #fee140 100%)',
            'linear-gradient(135deg, #a8edea 0%, #fed6e3 100%)',
            'linear-gradient(135deg, #ffecd2 0%, #fcb69f 100%)'
        ];
        const randomGradient = gradients[Math.floor(Math.random() * gradients.length)];

        const reviewCard = document.createElement('div');
        reviewCard.style.cssText = 'background: white; border-radius: 16px; padding: 2rem; box-shadow: 0 4px 20px rgba(0,0,0,0.1); transition: transform 0.3s ease;';

        reviewCard.innerHTML = `
            <div style="display: flex; align-items: center; gap: 1rem; margin-bottom: 1.5rem;">
                <div style="width: 60px; height: 60px; border-radius: 50%; background: ${randomGradient}; display: flex;  align-items: center; justify-content: center; color: white; font-size: 1.5rem; font-weight: 700;">${initial}</div>
                <div>
                    <h4 style="margin: 0; font-size: 1.125rem; color: var(--neutral-900);">${review.name}</h4>
                    <div style="color: #f59e0b; font-size: 1rem;">${stars}</div>
                </div>
            </div>
            <p style="color: var(--neutral-700); line-height: 1.6; font-style: italic;">"${review.text}"</p>
            <div style="color: var(--neutral-500); font-size: 0.875rem; margin-top: 1rem;">Verified Purchase</div>
        `;

        reviewsContainer.appendChild(reviewCard);
    });
}

// Initialize review system on page load (add to existing DOMContentLoaded)
const originalInit = document.addEventListener;
if (typeof initReviewSystem === 'function') {
    document.addEventListener('DOMContentLoaded', () => {
        initReviewSystem();
        loadUserReviews();
    });
}
