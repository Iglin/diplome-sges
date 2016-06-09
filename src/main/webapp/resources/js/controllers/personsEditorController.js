/**
 * Created by root on 18.05.16.
 */
var personsEditor = angular.module("personsEditor", []);
personsEditor.controller('personsEditorController', function($scope, $http, $routeParams) {
    angular.element(document).ready(function () {

        if ($routeParams['id'] != null && $routeParams['id'].trim() != '') {
            $http({
                url:'/persons/editor/',
                method:'GET',
                params: { id: $routeParams['id'] }
            }).then(function(response){
                var paramsMap = response.data;
                $scope.addresses = paramsMap['addresses'];
                $scope.person = paramsMap['person'];
                var arr = $scope.person.passport.dateOfIssue.split('-');
                $scope.person.passport.dateOfIssue = new Date(arr[0], --arr[1], arr[2]);
                var exitFlag = 0;
                for (var i = 0; i < $scope.addresses.length; i++) {
                    if ($scope.addresses[i].id == $scope.person.livingAddress.id) {
                        $scope.person.livingAddress = $scope.addresses[i];
                        exitFlag++;
                    }
                    if ($scope.addresses[i].id == $scope.person.passport.registrationAddress.id) {
                        $scope.person.passport.registrationAddress = $scope.addresses[i];
                        exitFlag++;
                    }
                    if (exitFlag > 1) break;
                }
            }, function(response){
                showAlert(response);
            });
            $scope.isUpdate = true;
            $scope.newRegistrationAddress = false;
            $scope.editRegistrationAddress = true;
            $scope.newLivingAddress = false;
            $scope.editLivingAddress = true;
        } else {
            $http({
                url:'/persons/editor/',
                method:'GET'
            }).then(function(response){
                var paramsMap = response.data;
                $scope.addresses = paramsMap['addresses'];
            }, function(response){
                showAlert(response);
            });
            $scope.person = {};
            $scope.person.passport = {};
            $scope.isUpdate = false;
            $scope.newRegistrationAddress = false;
            $scope.editRegistrationAddress = false;
            $scope.newLivingAddress = false;
            $scope.editLivingAddress = false;
        }
    });

    $scope.pickLivingAddress = function(address) {
        $scope.person.livingAddress = address;
    };

    $scope.pickRegistrationAddress = function(address) {
        $scope.person.passport.registrationAddress = address;
    };

    $scope.pickLivingAddressFromDB = function () {
        $scope.newLivingAddress = false;
        $scope.editLivingAddress = false;
    };

    $scope.setNewLivingAddress = function () {
        $scope.newLivingAddress = true;
        $scope.editLivingAddress = false;
        $scope.person.livingAddress = {};
    };

    $scope.setEditLivingAddress = function () {
        $scope.newLivingAddress = false;
        $scope.editLivingAddress = true;
    };

    $scope.pickRegistrationAddressFromDB = function () {
        $scope.newRegistrationAddress = false;
        $scope.editRegistrationAddress = false;
    };
    
    $scope.setNewRegistrationAddress = function () {
        $scope.newRegistrationAddress = true;
        $scope.editRegistrationAddress = false;
        $scope.person.passport.registrationAddress = {};
    };

    $scope.setEditRegistrationAddress = function () {
        $scope.newRegistrationAddress = false;
        $scope.editRegistrationAddress = true;
    };
    
    function validateInputs() {
        if ($scope.person == null) {
            showSimpleAlert(false, "Необходимо ввести данные о физ. лице.");
            return false;
        }
        if ($scope.person.passport == null) {
            showSimpleAlert(false, "Необходимо ввести паспортные данные.");
            return false;
        }
        if (isEmpty($scope.person.lastName)) {
            showSimpleAlert(false, "Необходимо ввести фамилию.");
            return false;
        }
        if (isEmpty($scope.person.firstName)) {
            showSimpleAlert(false, "Необходимо ввести имя.");
            return false;
        }
        if (!isValidPhone($scope.person.phone)) {
            showSimpleAlert(false, "Некорректно указан телефон.");
            return false;
        }
        if ($scope.person.email == null) {
            showSimpleAlert(false, "Некорректно указан e-mail.");
            return false;
        }

        if (!isValidCode($scope.person.personalAccount, 8)) {
            showSimpleAlert(false, "Некорректно указан персональный счёт.");
            return false;
        }
        if ($scope.person.passport.passportNumber == null) {
            showSimpleAlert(false, "Необходимо ввести номер паспорта.");
            return false;
        } else {
            if (typeof $scope.person.passport.passportNumber === 'string') {
                $scope.person.passport.passportNumber = $scope.person.passport.passportNumber.replace(" ", "");
                if (!isValidCode($scope.person.passport.passportNumber, 10)) {
                    showSimpleAlert(false, "Некорректно указан номер паспорта.");
                    return false;
                }
            }
        }
        if (isEmpty($scope.person.passport.placeOfIssue)) {
            showSimpleAlert(false, "Необходимо ввести место выдачи паспорта.");
            return false;
        }
        if ($scope.person.passport.dateOfIssue == null) {
            showSimpleAlert(false, "Необходимо ввести дату выдачи паспорта.");
            return false;
        }

        if ($scope.person.livingAddress == null) {
            showSimpleAlert(false, "Необходимо ввести данные об адресе проживания.");
            return false;
        } else {
            if ($scope.newLivingAddress || $scope.editLivingAddress) {
                if (!isValidAddress($scope.person.livingAddress)) {
                    showSimpleAlert(false, "Ошибка в адресе проживания.");
                    return false;
                }
            }
        }
        if ($scope.person.passport.registrationAddress == null) {
            showSimpleAlert(false, "Необходимо ввести данные об адресе регистрации.");
            return false;
        } else {
            if ($scope.newRegistrationAddress || $scope.editRegistrationAddress) {
                if (!isValidAddress($scope.person.passport.registrationAddress)) {
                    showSimpleAlert(false, "Ошибка в адресе регистрации.");
                    return false;
                }
            }
        }
        return true;
    }

    $scope.add = function () {
        if (validateInputs()) {
            $http({
                url: '/persons/editor/',
                method: 'POST',
                params: {
                    person: $scope.person
                }
            }).then(function (response) {
                showAlert(response);
                $scope.person = {};
                $scope.person.passport = {};
            }, function (response) {
                showAlert(response);
            });
        }
    };

    $scope.update = function () {
        if (validateInputs()) {
            $http({
                url: '/persons/editor/',
                method: 'PUT',
                params: {
                    id: $scope.person.id,
                    person: $scope.person
                }
            }).then(function (response) {
                showAlert(response);
            }, function (response) {
                showAlert(response);
            });
        }
    };
});