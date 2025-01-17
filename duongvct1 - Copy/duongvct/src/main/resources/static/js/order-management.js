document.getElementById('selectAll').onclick = function() {
  var checkboxes = document.getElementsByName('selectedIds');
  for (var checkbox of checkboxes) {
    checkbox.checked = this.checked;
  }
}

//function searchOrders() {
//        const searchInput = document.getElementById('searchInput').value;
//        const searchColumn = document.getElementById('searchColumn').value;
//        window.location.href = `/admin/order?searchColumn=${searchColumn}&searchValue=${searchInput}`;
//}