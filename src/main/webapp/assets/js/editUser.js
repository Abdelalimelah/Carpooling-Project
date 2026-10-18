// Edit User JavaScript

document.addEventListener('DOMContentLoaded', function() {
    // Role selection and license field toggle
    const roleSelect = document.getElementById('role');
    const licenseRow = document.getElementById('licenceRow');
    const licenseInput = document.getElementById('licenceNumber');
    
    function toggleLicenseField() {
        const isDriver = roleSelect.value === 'DRIVER';
        
        if (isDriver) {
            licenseRow.style.display = 'block';
            licenseInput.setAttribute('required', 'required');
            // Add animation for smooth appearance
            licenseRow.style.animation = 'fadeInUp 0.3s ease-out';
        } else {
            licenseRow.style.display = 'none';
            licenseInput.removeAttribute('required');
            licenseInput.value = licenseInput.value; // Keep value but don't require
        }
    }
    
    // Initialize on page load
    toggleLicenseField();
    
    // Add event listener for role changes
    if (roleSelect) {
        roleSelect.addEventListener('change', toggleLicenseField);
    }
    
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
    
    // Form validation and enhancement
    const userForm = document.querySelector('.user-form');
    
    if (userForm) {
        userForm.addEventListener('submit', function(e) {
            // Basic form validation
            const email = document.getElementById('email');
            const role = document.getElementById('role');
            const license = document.getElementById('licenceNumber');
            
            // Validate email format
            if (email && !isValidEmail(email.value)) {
                e.preventDefault();
                showFieldError(email, 'Please enter a valid email address');
                return;
            }
            
            // Validate license if driver role is selected
            if (role.value === 'DRIVER' && license && !license.value.trim()) {
                e.preventDefault();
                showFieldError(license, 'License number is required for driver role');
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
                    Updating User...
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
    const formInputs = document.querySelectorAll('.form-input, .form-select');
    formInputs.forEach(input => {
        // Add focus styles
        input.addEventListener('focus', function() {
            this.parentElement.classList.add('focused');
        });
        
        input.addEventListener('blur', function() {
            this.parentElement.classList.remove('focused');
        });
        
        // Real-time validation for email
        if (input.type === 'email') {
            input.addEventListener('blur', function() {
                if (this.value && !isValidEmail(this.value)) {
                    showFieldError(this, 'Please enter a valid email address');
                } else {
                    clearFieldError(this);
                }
            });
        }
        
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
    });
    
    // Utility functions
    function isValidEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }
    
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
    `;
    document.head.appendChild(style);
    
    console.log('Edit User page initialized successfully');
});

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
    
    // Escape key to go back
    if (e.key === 'Escape') {
        const backBtn = document.querySelector('a[href*="/admin/dashboard"]');
        if (backBtn) {
            backBtn.click();
        }
    }
});