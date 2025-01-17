document.getElementById('selectAll').onclick = function() {
  var checkboxes = document.getElementsByName('selectedIds');
  for (var checkbox of checkboxes) {
    checkbox.checked = this.checked;
  }
}

document.getElementById('searchInput').addEventListener('input', function() {
  var searchInput = document.getElementById('searchInput').value.toLowerCase();
  var searchColumn = document.getElementById('searchColumn').value;
  var tableRows = document.getElementById('customerTableBody').getElementsByTagName('tr');

  for (var i = 0; i < tableRows.length; i++) {
    var cells = tableRows[i].getElementsByTagName('td');
    var columnValue = '';

    switch (searchColumn) {
      case 'id':
        columnValue = cells[1].innerText.toLowerCase();
        break;
      case 'username':
        columnValue = cells[2].innerText.toLowerCase();
        break;
      case 'fullname':
        columnValue = cells[3].innerText.toLowerCase();
        break;
      case 'email':
        columnValue = cells[4].innerText.toLowerCase();
        break;
      case 'address':
        columnValue = cells[5].innerText.toLowerCase();
        break;
      case 'phoneNumber':
        columnValue = cells[7].innerText.toLowerCase();
        break;
    }

    if (columnValue.includes(searchInput)) {
      tableRows[i].style.display = '';
    } else {
      tableRows[i].style.display = 'none';
    }
  }
});