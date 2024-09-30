document.addEventListener('DOMContentLoaded', function () {
    // Get form and button elements
    var addStudentForm = document.getElementById('addStudentForm');
    var studentSelect = document.getElementById('studentToAddId');

    // Handle form submission
    addStudentForm.addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent form from submitting immediately

        // Get selected student ID
        var selectedStudentId = studentSelect.value;

        // Update form action
        addStudentForm.action = '/administrators/groups/add-student/' + selectedStudentId;

        // Submit the form with updated action
        addStudentForm.submit();
    });
});


