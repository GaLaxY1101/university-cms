document.addEventListener('DOMContentLoaded', function () {
    // Get the modal and form elements
    var confirmationModal = new bootstrap.Modal(document.getElementById('confirmationModal'));
    var deleteForm = document.getElementById('deleteForm');
    var adminIdInput = document.getElementById('adminId');

    // Handle the click event for delete buttons
    document.querySelectorAll('.fa-trash').forEach(function (button) {
        button.addEventListener('click', function (event) {
            var adminId = this.closest('tr').querySelector('td:first-child').innerText;
            deleteForm.action = '/administrators/admins/delete/' + adminId;
            adminIdInput.value = adminId;
            confirmationModal.show();
        });
    });
});


document.addEventListener("DOMContentLoaded", function() {
    var successMessage = document.getElementById('successMessage')?.textContent || '';
    var errorMessage = document.getElementById('errorMessage')?.textContent || '';

    if (successMessage || errorMessage) {
        var modal = new bootstrap.Modal(document.getElementById('messageModal'));
        modal.show();
    }
});
