document.addEventListener('DOMContentLoaded', function() {
    const selectAllCheckbox = document.getElementById('selectAll');
    const checkboxes = document.querySelectorAll('input[type="checkbox"][name="selectedIds"]');

    selectAllCheckbox.addEventListener('change', function() {
        checkboxes.forEach(checkbox => {
            checkbox.checked = selectAllCheckbox.checked;
        });
    });
});
document.getElementById('searchInput').addEventListener('input', function() {
  var searchInput = document.getElementById('searchInput').value.toLowerCase();
  var searchColumn = document.getElementById('searchColumn').value;
  var tableRows = document.getElementById('shipmentTableBody').getElementsByTagName('tr');

  for (var i = 0; i < tableRows.length; i++) {
    var cells = tableRows[i].getElementsByTagName('td');
    var columnValue = '';

    switch (searchColumn) {
      case 'id':
        columnValue = cells[1].innerText.toLowerCase();
        break;
      case 'order':
        columnValue = cells[2].innerText.toLowerCase();
        break;
      case 'customer':
        columnValue = cells[3].innerText.toLowerCase();
        break;
      case 'shipper':
        columnValue = cells[4].innerText.toLowerCase();
        break;
      case 'shipmentStatus':
        columnValue = cells[5].innerText.toLowerCase();
        break;
    }

    if (columnValue.includes(searchInput)) {
      tableRows[i].style.display = '';
    } else {
      tableRows[i].style.display = 'none';
    }
  }
});