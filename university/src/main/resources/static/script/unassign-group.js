document.addEventListener('DOMContentLoaded', function () {
    // Get the modal and form elements
    var confirmationModal = new bootstrap.Modal(document.getElementById('confirmationModal'));
    var deleteForm = document.getElementById('deleteForm');
    var studentIdInput = document.getElementById('studentId');
    var groupIdInput = document.getElementById('groupId');

    // Handle the click event for delete buttons
    document.querySelectorAll('.fa-trash').forEach(function (button) {
        button.addEventListener('click', function (event) {

            console.log("groupIdInput: " + groupIdInput);
            console.log("groupIdInput.value: " + groupIdInput.value );
            console.log("groupIdInput.innerText: " + groupIdInput.innerText );

            var groupId = groupIdInput.value;
            var studentId = this.closest('tr').querySelector('td:first-child').innerText;
            deleteForm.action = '/administrators/groups/edit/unassign-group/' + studentId + '?groupId=' + groupId;

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