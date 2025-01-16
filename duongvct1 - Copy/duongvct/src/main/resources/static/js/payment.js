$(document).ready(function() {
    function calculateAmounts() {
        var totalAmount = parseFloat($('#totalAmount').val());
        var discount = parseFloat($('#discount').val());
        var paidAmount = parseFloat($('#paidAmount').val());
        var amountAfterDiscount = totalAmount - discount;
        var rebateAmount = paidAmount - amountAfterDiscount;
        $('#amountAfterDiscount').val(amountAfterDiscount.toFixed(2));
        $('#rebateAmount').val(rebateAmount.toFixed(2));
    }

    $('#discount, #paidAmount').on('input', function() {
        calculateAmounts();
    });

    calculateAmounts();
});