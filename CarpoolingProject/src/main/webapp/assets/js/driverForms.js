/**
 * Driver Forms JavaScript
 * Handles form interactions and validations
 */

// Initialize when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    initForms();
});

function initForms() {
    // Add real-time validation to forms
    setupFormValidation();
    
    // Enhance datetime inputs
    enhanceDateTimeInputs();
    
    // Setup any form-specific interactions
    setupFormInteractions();
}

function setupFormValidation() {
    var forms = document.querySelectorAll('form');
    
    forms.forEach(function(form) {
        var inputs = form.querySelectorAll('input[required], select[required]');
        
        inputs.forEach(function(input) {
            // Add validation styles on blur
            input.addEventListener('blur', function() {
                validateField(this);
            });
            
            // Remove error state when user starts typing
            input.addEventListener('input', function() {
                if (this.validity.valid) {
                    this.classList.remove('error');
                }
            });
        });
        
        // Enhanced form submission
        form.addEventListener('submit', function(e) {
            if (!validateForm(this)) {
                e.preventDefault();
                showFormErrors(this);
            }
        });
    });
}

function validateField(field) {
    if (!field.validity.valid) {
        field.classList.add('error');
        showFieldError(field);
    } else {
        field.classList.remove('error');
        clearFieldError(field);
    }
}

function validateForm(form) {
    var isValid = true;
    var requiredFields = form.querySelectorAll('input[required], select[required]');
    
    requiredFields.forEach(function(field) {
        if (!field.value.trim()) {
            field.classList.add('error');
            showFieldError(field);
            isValid = false;
        } else {
            field.classList.remove('error');
            clearFieldError(field);
        }
    });
    
    // Additional validation for specific fields
    var priceField = form.querySelector('input[type="number"][min="0.01"]');
    if (priceField && priceField.value && parseFloat(priceField.value) <= 0) {
        priceField.classList.add('error');
        showFieldError(priceField, 'Price must be greater than 0');
        isValid = false;
    }
    
    var seatsField = form.querySelector('input[type="number"][min="1"][max="6"]');
    if (seatsField && seatsField.value) {
        var seats = parseInt(seatsField.value);
        if (seats < 1 || seats > 6) {
            seatsField.classList.add('error');
            showFieldError(seatsField, 'Seats must be between 1 and 6');
            isValid = false;
        }
    }
    
    return isValid;
}

function showFieldError(field, customMessage) {
    var errorMessage = customMessage || getFieldErrorMessage(field);
    var existingError = field.parentNode.querySelector('.field-error');
    
    if (existingError) {
        existingError.textContent = errorMessage;
    } else {
        var errorElement = document.createElement('div');
        errorElement.className = 'field-error';
        errorElement.style.cssText = 'color: var(--danger); font-size: 0.8rem; margin-top: 0.25rem;';
        errorElement.textContent = errorMessage;
        field.parentNode.appendChild(errorElement);
    }
}

function clearFieldError(field) {
    var existingError = field.parentNode.querySelector('.field-error');
    if (existingError) {
        existingError.remove();
    }
}

function getFieldErrorMessage(field) {
    if (field.validity.valueMissing) {
        return 'This field is required';
    }
    if (field.validity.typeMismatch) {
        return 'Please enter a valid value';
    }
    if (field.validity.rangeUnderflow) {
        return 'Value is too low';
    }
    if (field.validity.rangeOverflow) {
        return 'Value is too high';
    }
    return 'Please check this field';
}

function showFormErrors(form) {
    var firstError = form.querySelector('.error');
    if (firstError) {
        firstError.focus();
        
        // Show a general error message
        var existingMessage = form.querySelector('.form-error-message');
        if (!existingMessage) {
            var errorMessage = document.createElement('div');
            errorMessage.className = 'message error form-error-message';
            errorMessage.textContent = 'Please fix the errors in the form before submitting.';
            form.insertBefore(errorMessage, form.firstChild);
        }
    }
}

function enhanceDateTimeInputs() {
    var datetimeInputs = document.querySelectorAll('input[type="datetime-local"]');
    
    datetimeInputs.forEach(function(input) {
        // Set min date to current date/time
        var now = new Date();
        var year = now.getFullYear();
        var month = String(now.getMonth() + 1).padStart(2, '0');
        var day = String(now.getDate()).padStart(2, '0');
        var hours = String(now.getHours()).padStart(2, '0');
        var minutes = String(now.getMinutes()).padStart(2, '0');
        
        input.min = year + '-' + month + '-' + day + 'T' + hours + ':' + minutes;
        
        // Add change listener
        input.addEventListener('change', function() {
            validateDateTime(this);
        });
    });
}

function validateDateTime(input) {
    var selectedDate = new Date(input.value);
    var now = new Date();
    
    if (selectedDate < now) {
        input.classList.add('error');
        showFieldError(input, 'Please select a future date and time');
        return false;
    }
    
    input.classList.remove('error');
    clearFieldError(input);
    return true;
}

function setupFormInteractions() {
    // Auto-format plate numbers (uppercase)
    var plateInputs = document.querySelectorAll('input[name="plateNumber"]');
    plateInputs.forEach(function(input) {
        input.addEventListener('input', function() {
            this.value = this.value.toUpperCase();
        });
    });
    
    // Price field formatting
    var priceInputs = document.querySelectorAll('input[name="price"]');
    priceInputs.forEach(function(input) {
        input.addEventListener('blur', function() {
            if (this.value) {
                var value = parseFloat(this.value);
                if (!isNaN(value)) {
                    this.value = value.toFixed(2);
                }
            }
        });
    });
}

// Utility function to show loading state on form submission
function setFormLoading(form, isLoading) {
    var submitButton = form.querySelector('button[type="submit"]');
    var allButtons = form.querySelectorAll('button, .btn');
    
    if (isLoading) {
        allButtons.forEach(function(button) {
            button.disabled = true;
            var originalText = button.innerHTML;
            button.setAttribute('data-original-text', originalText);
            button.innerHTML = '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="spin"><path d="M21 12a9 9 0 11-6.219-8.56"/></svg> Loading...';
        });
    } else {
        allButtons.forEach(function(button) {
            button.disabled = false;
            var originalText = button.getAttribute('data-original-text');
            if (originalText) {
                button.innerHTML = originalText;
            }
        });
    }
}

// Add CSS for spinning animation
var style = document.createElement('style');
style.textContent = `
    .spin {
        animation: spin 1s linear infinite;
    }
    @keyframes spin {
        from { transform: rotate(0deg); }
        to { transform: rotate(360deg); }
    }
`;
document.head.appendChild(style);