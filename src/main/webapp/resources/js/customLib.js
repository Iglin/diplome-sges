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
    return !!(Math.floor(x) == x && $.isNumeric(x));
}

function isEmpty(str) {
    return str == null ? true : !str.replace(/^\s+/g, '').length;
}

function isValidFloat(x, min, max) {
    if (x == null) return false;
    if (typeof x === 'string') {
        if (isEmpty(x)) return false;
        var num = Number(x);
        if (num == 'NaN') return false;
        if (min != null) {
            if (num < min) return false;
        }
        if (max != null) {
            if (num > max) return false;
        }
    } else {
        if (min != null) {
            if (x < min) return false;
        }
        if (max != null) {
            if (x > max) return false;
        }
    }
    return true;
}

function isValidInt(x, min, max) {
    if (x == null) return false;
    if (typeof x === 'string') {
        if (isEmpty(x)) return false;
        var num = Number(x);
        if (num == 'NaN') return false;
        if (!isInteger(num)) return false;
        if (min != null) {
            if (num < min) return false;
        }
        if (max != null) {
            if (num > max) return false;
        }
    } else {
        if (!isInteger(x)) return false;
        if (min != null) {
            if (x < min) return false;
        }
        if (max != null) {
            if (x > max) return false;
        }
    }
    return true;
}

function isValidCode(str, len) {
    if (str == null) return false;
    if (typeof str === 'string') {
        if (str.length != len) return false;
        return /^\d+$/.test(str);
    } else {
        // Код прочитан из базы
        return true;
    }
}

function isValidFlexibleCode(str, minLen, maxLen) {
    if (str == null) return false;
    if (typeof str === 'string') {
        if (str.length < minLen || str.length > maxLen) return false;
        return /^\d+$/.test(str);
    } else return true; // Код прочитан из базы
}

function isValidAddress(address) {
    if (address == null) {
        showSimpleAlert(false, "Необходимо ввести данные об адресе.");
        return false;
    }
    if (isEmpty(address.region)) {
        showSimpleAlert(false,"Необходимо ввести регион.");
        return false;
    }
    if (isEmpty(address.city)) {
        showSimpleAlert(false,"Необходимо ввести город.");
        return false;
    }
    if (isEmpty(address.street)) {
        showSimpleAlert(false,"Необходимо ввести улицу.");
        return false;
    }
    if (isEmpty(address.building)) {
        showSimpleAlert(false,"Необходимо ввести номер дома.");
        return false;
    }
    if (!isValidFlexibleCode(address.index, 6, 7)) {
        showSimpleAlert(false,"Некорректно указан индекс.");
        return false;
    }
    return true;
}

function findObjectById(arr, id) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i].id == id) {
            return arr[i];
        }
    }
    alert('No such id!');
    return null;
}

function isValidPhone(phone) {
    if (phone == null) return false;
    if (phone.indexOf("-") == 0 || phone.indexOf("-") == phone.length) return false;
    var str = phone;
    str = str.replace(/ /g,"");
    str = str.replace("(","");
    str = str.replace(")","");
    if (str.indexOf("-") == 0 || str.indexOf("-") == str.length) return false;
    str = str.replace("-","");
    if (str.indexOf('+') > -1) {
        if (str.indexOf('+') != 0) return false;
        str = str.replace("+","");
        return !!isValidCode(str, 11);
    } else return !!(str.indexOf('8') == 0 && isValidCode(str, 11));

}