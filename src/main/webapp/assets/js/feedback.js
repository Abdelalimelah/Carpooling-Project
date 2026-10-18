document.addEventListener('DOMContentLoaded', function() {
            const stars = document.querySelectorAll('.star');
            const ratingInput = document.getElementById('rating');
            const ratingText = document.getElementById('ratingText');
            
            const ratingLabels = {
                1: 'Poor',
                2: 'Fair', 
                3: 'Good',
                4: 'Very Good',
                5: 'Excellent'
            };

            stars.forEach(star => {
                star.addEventListener('click', () => {
                    const rating = parseInt(star.getAttribute('data-value'));
                    ratingInput.value = rating;
                    
                    // Update stars appearance
                    stars.forEach(s => {
                        const starValue = parseInt(s.getAttribute('data-value'));
                        if (starValue <= rating) {
                            s.classList.add('selected');
                            s.style.color = '#FFD700';
                        } else {
                            s.classList.remove('selected');
                            s.style.color = '#e2e8f0';
                        }
                    });
                    
                    // Update rating text
                    ratingText.textContent = ratingLabels[rating] || 'Select a rating';
                    ratingText.style.color = '#1e293b';
                });

                // Hover effect
                star.addEventListener('mouseenter', function() {
                    const hoverRating = parseInt(this.getAttribute('data-value'));
                    stars.forEach(s => {
                        const starValue = parseInt(s.getAttribute('data-value'));
                        if (starValue <= hoverRating) {
                            s.style.color = '#FFD700';
                        }
                    });
                });

                star.addEventListener('mouseleave', function() {
                    const currentRating = parseInt(ratingInput.value) || 0;
                    stars.forEach(s => {
                        const starValue = parseInt(s.getAttribute('data-value'));
                        if (starValue > currentRating) {
                            s.style.color = '#e2e8f0';
                        }
                    });
                });
            });
        });