// Edit Trip JavaScript

document.addEventListener('DOMContentLoaded', function() {
    // Profile Dropdown Toggle
    const profileBtn = document.getElementById('profileButton');
    const profileDropdown = document.getElementById('profileDropdown');
    
    if (profileBtn && profileDropdown) {
        profileBtn.addEventListener('click', function(e) {
            e.stopPropagation();
            const isVisible = profileDropdown.style.display === 'block';
            profileDropdown.style.display = isVisible ? 'none' : 'block';
        });
        
        // Close dropdown when clicking outside
        document.addEventListener('click', function() {
            profileDropdown.style.display = 'none';
        });
        
        // Prevent dropdown from closing when clicking inside
        profileDropdown.addEventListener('click', function(e) {
            e.stopPropagation();
        });
    }
    
    // Car selection functionality
    const carOptions = document.querySelectorAll('.car-option');
    const carIdInput = document.getElementById('carId');
    
    if (carOptions.length > 0 && carIdInput) {
        carOptions.forEach(option => {
            option.addEventListener('click', function() {
                const carId = this.getAttribute('data-car-id');
                
                // Update selected state
                carOptions.forEach(opt => opt.classList.remove('selected'));
                this.classList.add('selected');
                
                // Update hidden input
                carIdInput.value = carId;
                
                // Add visual feedback
                this.style.transform = 'scale(0.98)';
                setTimeout(() => {
                    this.style.transform = '';
                }, 150);
            });
        });
    }
    
    // Form validation and enhancement
    const tripForm = document.querySelector('.trip-form');
    
    if (tripForm) {
        tripForm.addEventListener('submit', function(e) {
            // Basic form validation
            const departureCity = document.getElementById('departureCity');
            const arrivalCity = document.getElementById('arrivalCity');
            const dateTime = document.getElementById('dateTime');
            const price = document.getElementById('price');
            const seats = document.getElementById('seatsAvailable');
            const carId = document.getElementById('carId');
            
            // Validate cities are different
            if (departureCity.value === arrivalCity.value) {
                e.preventDefault();
                showFieldError(departureCity, 'Departure and arrival cities must be different');
                showFieldError(arrivalCity, 'Departure and arrival cities must be different');
                return;
            }
            
            // Validate date is in the future
            const selectedDate = new Date(dateTime.value);
            const now = new Date();
            if (selectedDate <= now) {
                e.preventDefault();
                showFieldError(dateTime, 'Trip date must be in the future');
                return;
            }
            
            // Validate price is reasonable
            if (price.value < 0 || price.value > 10000) {
                e.preventDefault();
                showFieldError(price, 'Price must be between 0 and 10,000 MAD');
                return;
            }
            
            // Validate seats
            if (seats.value < 1 || seats.value > 8) {
                e.preventDefault();
                showFieldError(seats, 'Seats must be between 1 and 8');
                return;
            }
            
            // Add loading state
            const submitBtn = this.querySelector('button[type="submit"]');
            if (submitBtn) {
                submitBtn.disabled = true;
                submitBtn.innerHTML = `
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="spin">
                        <line x1="12" y1="2" x2="12" y2="6"/>
                        <line x1="12" y1="18" x2="12" y2="22"/>
                        <line x1="4.93" y1="4.93" x2="7.76" y2="7.76"/>
                        <line x1="16.24" y1="16.24" x2="19.07" y2="19.07"/>
                        <line x1="2" y1="12" x2="6" y2="12"/>
                        <line x1="18" y1="12" x2="22" y2="12"/>
                        <line x1="4.93" y1="19.07" x2="7.76" y2="16.24"/>
                        <line x1="16.24" y1="7.76" x2="19.07" y2="4.93"/>
                    </svg>
                    Updating Trip...
                `;
            }
        });
    }
    
    // Auto-hide success/error messages after 5 seconds
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.opacity = '0';
            alert.style.transition = 'opacity 0.5s ease';
            setTimeout(() => {
                if (alert.parentNode) {
                    alert.parentNode.removeChild(alert);
                }
            }, 500);
        }, 5000);
    });
    
    // Enhanced input interactions
    const formInputs = document.querySelectorAll('.form-input');
    formInputs.forEach(input => {
        // Add focus styles
        input.addEventListener('focus', function() {
            this.parentElement.classList.add('focused');
        });
        
        input.addEventListener('blur', function() {
            this.parentElement.classList.remove('focused');
        });
        
        // Real-time validation for required fields
        if (input.hasAttribute('required')) {
            input.addEventListener('blur', function() {
                if (!this.value.trim()) {
                    showFieldError(this, 'This field is required');
                } else {
                    clearFieldError(this);
                }
            });
        }
        
        // Special validation for price
        if (input.name === 'price') {
            input.addEventListener('blur', function() {
                if (this.value < 0) {
                    showFieldError(this, 'Price cannot be negative');
                } else if (this.value > 10000) {
                    showFieldError(this, 'Price seems too high');
                } else {
                    clearFieldError(this);
                }
            });
        }
        
        // Special validation for seats
        if (input.name === 'seatsAvailable') {
            input.addEventListener('blur', function() {
                if (this.value < 1 || this.value > 8) {
                    showFieldError(this, 'Seats must be between 1 and 8');
                } else {
                    clearFieldError(this);
                }
            });
        }
    });
    
    // City validation - ensure they're different
    const departureInput = document.getElementById('departureCity');
    const arrivalInput = document.getElementById('arrivalCity');
    
    if (departureInput && arrivalInput) {
        const validateCities = () => {
            if (departureInput.value && arrivalInput.value && departureInput.value === arrivalInput.value) {
                showFieldError(departureInput, 'Cities must be different');
                showFieldError(arrivalInput, 'Cities must be different');
            } else {
                clearFieldError(departureInput);
                clearFieldError(arrivalInput);
            }
        };
        
        departureInput.addEventListener('blur', validateCities);
        arrivalInput.addEventListener('blur', validateCities);
    }
    
    // Utility functions
    function showFieldError(field, message) {
        clearFieldError(field);
        
        field.style.borderColor = 'var(--danger)';
        
        const errorElement = document.createElement('div');
        errorElement.className = 'field-error';
        errorElement.style.color = 'var(--danger)';
        errorElement.style.fontSize = '0.8rem';
        errorElement.style.marginTop = '0.25rem';
        errorElement.textContent = message;
        
        field.parentNode.appendChild(errorElement);
    }
    
    function clearFieldError(field) {
        field.style.borderColor = '';
        
        const existingError = field.parentNode.querySelector('.field-error');
        if (existingError) {
            existingError.remove();
        }
    }
    
    // Add CSS for spinning animation
    const style = document.createElement('style');
    style.textContent = `
        @keyframes spin {
            from { transform: rotate(0deg); }
            to { transform: rotate(360deg); }
        }
        .spin {
            animation: spin 1s linear infinite;
        }
        .form-group.focused .form-label {
            color: var(--ocean-bright);
        }
        .field-error {
            animation: slideDown 0.3s ease-out;
        }
    `;
    document.head.appendChild(style);
    
    // Set minimum datetime to current time
    const dateTimeInput = document.getElementById('dateTime');
    if (dateTimeInput) {
        const now = new Date();
        // Format to YYYY-MM-DDTHH:MM
        const localDateTime = new Date(now.getTime() - now.getTimezoneOffset() * 60000)
            .toISOString()
            .slice(0, 16);
        dateTimeInput.min = localDateTime;
    }
    
    // Keyboard shortcuts
    document.addEventListener('keydown', function(e) {
        // Ctrl/Cmd + S to save
        if ((e.ctrlKey || e.metaKey) && e.key === 's') {
            e.preventDefault();
            const submitBtn = document.querySelector('button[type="submit"]');
            if (submitBtn) {
                submitBtn.click();
            }
        }
        
        // Escape key to cancel
        if (e.key === 'Escape') {
            const cancelBtn = document.querySelector('a[href*="/admin/dashboard"]');
            if (cancelBtn) {
                cancelBtn.click();
            }
        }
    });
    
    console.log('Edit Trip page initialized successfully');
});

// Utility functions for trip editing
const TripEditor = {
    // Calculate suggested price based on distance
    calculateSuggestedPrice: function(departure, arrival) {
        // This is a simplified calculation - in reality you'd use a distance API
        const basePrice = 50;
        const pricePerKm = 0.8;
        
        // Mock distance calculation (would be replaced with real API)
        const distances = {
            'casablanca-marrakech': 240,
            'rabat-casablanca': 90,
            'fes-meknes': 60,
            'tanger-rabat': 240
        };
        
        const key = `${departure.toLowerCase()}-${arrival.toLowerCase()}`;
        const distance = distances[key] || 100;
        
        return Math.round((basePrice + (distance * pricePerKm)) / 5) * 5; // Round to nearest 5
    },
    
    // Auto-fill price based on route
    setupPriceSuggestion: function() {
        const departureInput = document.getElementById('departureCity');
        const arrivalInput = document.getElementById('arrivalCity');
        const priceInput = document.getElementById('price');
        
        if (departureInput && arrivalInput && priceInput) {
            const calculatePrice = () => {
                if (departureInput.value && arrivalInput.value) {
                    const suggestedPrice = this.calculateSuggestedPrice(
                        departureInput.value, 
                        arrivalInput.value
                    );
                    
                    // Only suggest if no price is set yet
                    if (!priceInput.value || priceInput.value === '0') {
                        priceInput.value = suggestedPrice;
                        priceInput.dispatchEvent(new Event('change'));
                    }
                }
            };
            
            departureInput.addEventListener('blur', calculatePrice);
            arrivalInput.addEventListener('blur', calculatePrice);
        }
    }
};

// Initialize price suggestions
document.addEventListener('DOMContentLoaded', function() {
    TripEditor.setupPriceSuggestion();
});