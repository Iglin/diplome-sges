/**
 * Created by user on 08.06.2016.
 */
function showAlert(response) {
    showSimpleAlert(response.data.success, response.data.message);
}

function showSimpleAlert(success, message) {
    if (success) {
        $('#alert_div').append(
            '<div class="alert alert-success alert-dismissable">' +
            '<button type="button" class="close" ' +
            'data-dismiss="alert" aria-hidden="true">' +
            '&times;' +
            '</button>' +
            message +
            '</div>');
    } else {
        $('#alert_div').append(
            '<div class="alert alert-danger alert-dismissable">' +
            '<button type="button" class="close" ' +
            'data-dismiss="alert" aria-hidden="true">' +
            '&times;' +
            '</button>' +
            '<strong>Ошибка!</strong>   ' +
            message +
            '</div>');
    }
}

function isInteger(x) {
    if (Math.floor(x) == x && $.isNumeric(x)) {
        return true;
    } else {
        return false;
    }
}