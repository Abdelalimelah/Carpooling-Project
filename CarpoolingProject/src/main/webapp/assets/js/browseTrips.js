// Browse Trips Page JavaScript

document.addEventListener('DOMContentLoaded', function() {
    // Initialize date picker with tomorrow as minimum date
    const dateInput = document.getElementById('date');
    if (dateInput && !dateInput.value) {
        const tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);
        dateInput.min = tomorrow.toISOString().split('T')[0];
    }

    // Add real-time validation for city inputs
    const departureInput = document.getElementById('departureCity');
    const arrivalInput = document.getElementById('arrivalCity');

    if (departureInput && arrivalInput) {
        // Prevent same city for departure and arrival
        departureInput.addEventListener('input', function() {
            if (this.value && arrivalInput.value && this.value.toLowerCase() === arrivalInput.value.toLowerCase()) {
                this.setCustomValidity('Departure and arrival cities cannot be the same');
            } else {
                this.setCustomValidity('');
            }
        });

        arrivalInput.addEventListener('input', function() {
            if (this.value && departureInput.value && this.value.toLowerCase() === departureInput.value.toLowerCase()) {
                this.setCustomValidity('Departure and arrival cities cannot be the same');
            } else {
                this.setCustomValidity('');
            }
        });
    }

    // Add loading state to search button
    const searchForm = document.querySelector('.filters-form');
    if (searchForm) {
        searchForm.addEventListener('submit', function() {
            const submitButton = this.querySelector('button[type="submit"]');
            if (submitButton) {
                submitButton.disabled = true;
                submitButton.innerHTML = `
                    <div class="spinner" style="width:18px;height:18px;border:2px solid rgba(255,255,255,0.3);border-top-color:white;border-radius:50%;animation:spin 1s linear infinite"></div>
                    Searching...
                `;
            }
        });
    }

    // Add smooth scroll to results section after search
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.toString() && document.querySelector('.results-section')) {
        setTimeout(() => {
            document.querySelector('.results-section').scrollIntoView({ 
                behavior: 'smooth',
                block: 'start'
            });
        }, 500);
    }
});

// CSS for spinner animation
const style = document.createElement('style');
style.textContent = `
    @keyframes spin {
        to { transform: rotate(360deg); }
    }
`;
document.head.appendChild(style);