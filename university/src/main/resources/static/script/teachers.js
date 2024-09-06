document.addEventListener('DOMContentLoaded', function () {
    // Get the modal and form elements
    var confirmationModal = new bootstrap.Modal(document.getElementById('confirmationModal'));
    var deleteForm = document.getElementById('deleteForm');
    var teacherIdInput = document.getElementById('teacherId');

    // Handle the click event for delete buttons
    document.querySelectorAll('.fa-trash').forEach(function (button) {
        button.addEventListener('click', function (event) {
            var teacherId = this.closest('tr').querySelector('td:first-child').innerText;
            deleteForm.action = '/administrators/teachers/delete/' + teacherId;
            teacherIdInput.value = teacherId;
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

$(document).ready(function () {
    $('.disciplines-select').select2({
        placeholder: 'Select a group',
        allowClear: true
    });
});
