document.addEventListener('DOMContentLoaded', function () {
    // Get the modal and form elements
    var confirmationModal = new bootstrap.Modal(document.getElementById('confirmationModal'));
    var deleteForm = document.getElementById('deleteForm');
    var studentIdInput = document.getElementById('studentId');

    // Handle the click event for delete buttons
    document.querySelectorAll('.fa-trash').forEach(function (button) {
        button.addEventListener('click', function (event) {
            var studentId = this.closest('tr').querySelector('td:first-child').innerText;
            deleteForm.action = '/administrators/students/delete/' + studentId;
            studentIdInput.value = studentId;
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

document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.edit-icon').forEach(function (button) {
        button.addEventListener('click', function (event) {
            var studentId = this.closest('tr').querySelector('td:first-child').innerText;
            deleteForm.action = '/administrators/students/delete/' + studentId;
            studentIdInput.value = studentId;
            confirmationModal.show();
        });
    });
});

