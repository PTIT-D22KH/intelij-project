document.getElementById('selectAll').onclick = function() {
  var checkboxes = document.getElementsByName('selectedIds');
  for (var checkbox of checkboxes) {
    checkbox.checked = this.checked;
  }
}