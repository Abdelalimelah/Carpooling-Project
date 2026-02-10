// Trip Details JavaScript

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
    
    // Enhanced table interactions
    const tableRows = document.querySelectorAll('.data-table tbody tr');
    tableRows.forEach(row => {
        row.addEventListener('click', function(e) {
            // Don't trigger if clicking on buttons or links
            if (e.target.tagName === 'BUTTON' || e.target.tagName === 'A' || e.target.closest('button') || e.target.closest('a')) {
                return;
            }
            
            // Toggle selected state
            this.classList.toggle('selected');
        });
    });
    
    // Confirm delete action
    const deleteForm = document.querySelector('form[onsubmit*="confirm"]');
    if (deleteForm) {
        deleteForm.addEventListener('submit', function(e) {
            const message = this.getAttribute('onsubmit').match(/return confirm\('([^']+)'\)/)[1];
            if (!confirm(message)) {
                e.preventDefault();
            }
        });
    }
    
    // Add loading states for form submissions
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function() {
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
                    Processing...
                `;
            }
        });
    });
    
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
        .data-table tr.selected {
            background: var(--bg-light) !important;
            box-shadow: inset 0 0 0 2px var(--ocean-bright);
        }
    `;
    document.head.appendChild(style);
    
    // Trip status color coding based on date
    function updateTripStatus() {
        const tripDateTime = document.querySelector('.info-value')?.textContent;
        if (tripDateTime) {
            // You could add logic here to dynamically update status based on current date
            console.log('Trip date/time:', tripDateTime);
        }
    }
    
    // Initialize trip status
    updateTripStatus();
    
    // Keyboard shortcuts
    document.addEventListener('keydown', function(e) {
        // Escape key to go back
        if (e.key === 'Escape') {
            const backBtn = document.querySelector('.btn-back');
            if (backBtn) {
                backBtn.click();
            }
        }
        
        // Ctrl/Cmd + E to edit
        if ((e.ctrlKey || e.metaKey) && e.key === 'e') {
            e.preventDefault();
            const editBtn = document.querySelector('a[href*="action=edit"]');
            if (editBtn) {
                editBtn.click();
            }
        }
    });
    
    console.log('Trip Details page initialized successfully');
});

// Utility functions for trip details
const TripDetails = {
    // Format date for better display
    formatDate: function(dateString) {
        return new Date(dateString).toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    },
    
    // Calculate time until trip
    getTimeUntilTrip: function(dateString) {
        const tripDate = new Date(dateString);
        const now = new Date();
        const diff = tripDate - now;
        
        if (diff < 0) {
            return 'Trip has passed';
        }
        
        const days = Math.floor(diff / (1000 * 60 * 60 * 24));
        const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        
        if (days > 0) {
            return `In ${days} day${days > 1 ? 's' : ''} and ${hours} hour${hours > 1 ? 's' : ''}`;
        } else {
            return `In ${hours} hour${hours > 1 ? 's' : ''}`;
        }
    },
    
    // Export bookings data
    exportBookings: function() {
        const table = document.querySelector('.data-table');
        if (!table) return;
        
        let csv = [];
        const rows = table.querySelectorAll('tr');
        
        for (let i = 0; i < rows.length; i++) {
            const row = [], cols = rows[i].querySelectorAll('td, th');
            
            for (let j = 0; j < cols.length; j++) {
                // Skip actions column
                if (j === cols.length - 1) continue;
                
                let data = cols[j].innerText.replace(/(\r\n|\n|\r)/gm, "").replace(/(\s\s)/gm, " ");
                data = data.replace(/"/g, '""');
                row.push('"' + data + '"');
            }
            
            csv.push(row.join(","));
        }
        
        const csvString = csv.join("\n");
        const blob = new Blob([csvString], { type: 'text/csv' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        
        a.setAttribute('hidden', '');
        a.setAttribute('href', url);
        a.setAttribute('download', `trip-${window.location.pathname.split('/').pop()}-bookings.csv`);
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    }
};