
document.addEventListener('DOMContentLoaded', function() {
	    const passengerRadio = document.getElementById('rolePassenger');
	    const driverRadio = document.getElementById('roleDriver');
	    const driverFields = document.getElementById('driverFields');
	    
	    function toggleDriverFields() {
	        if (driverRadio.checked) {
	            driverFields.style.display = 'block';
	            // Add required attribute to driver fields
	            document.getElementById('licenceNumber').required = true;
	            document.getElementById('carBrand').required = true;
	            document.getElementById('carModel').required = true;
	            document.getElementById('plateNumber').required = true;
	        } else {
	            driverFields.style.display = 'none';
	            // Remove required attribute from driver fields
	            document.getElementById('licenceNumber').required = false;
	            document.getElementById('carBrand').required = false;
	            document.getElementById('carModel').required = false;
	            document.getElementById('plateNumber').required = false;
	        }
	    }
	    
	    passengerRadio.addEventListener('change', toggleDriverFields);
	    driverRadio.addEventListener('change', toggleDriverFields);
	    
	    // Initialize on page load - check if driver was previously selected
	    const currentRole = '${selectedRole}';
	    if (currentRole === 'DRIVER') {
	        driverRadio.checked = true;
	        toggleDriverFields();
	    }

});