/**
 * Created by user on 23.05.2016.
 */
var entitiesEditor = angular.module("entitiesEditor", []);
entitiesEditor.controller('entitiesEditorController', function($scope, $http, $routeParams) {
    if ($routeParams['id'] != null && $routeParams['id'].trim() != '') {
        $http({
            url:'/entities/editor/',
            method:'GET',
            params: { id: $routeParams['id'] }
        }).then(function(response){
            var paramsMap = response.data;
            $scope.addresses = paramsMap['addresses'];
            $scope.entity = paramsMap['entity'];
            var arr = $scope.entity.registrationDate.split('-');
            $scope.entity.registrationDate = new Date(arr[0], --arr[1], arr[2]);
            for (var i = 0; i < $scope.addresses.length; i++) {
                if ($scope.addresses[i].id == $scope.entity.address.id) {
                    $scope.entity.address = $scope.addresses[i];
                    break;
                }
            }
        }, function(response){
            showAlert(response);
        });
        $scope.isUpdate = true;
        $scope.newAddress = false;
        $scope.editAddress = true;
    } else {
        $http({
            url:'/entities/editor/',
            method:'GET'
        }).then(function(response){
            var paramsMap = response.data;
            $scope.addresses = paramsMap['addresses'];
        }, function(response){
            showAlert(response);
        });
        $scope.entity = {};
        $scope.isUpdate = false;
        $scope.newAddress = false;
        $scope.editAddress = false;
    }


    function loadAddresses() {
        $http({
            url:'/addresses/table/',
            method:'GET'
        }).then(function(response){
            $scope.addresses = response.data;
        }, function(response){
            showAlert(response);
        });
    }

    $scope.pickAddress = function(address) {
        $scope.entity.address = address;
    };

    $scope.pickAddressFromDB = function () {
        $scope.newAddress = false;
        $scope.editAddress = false;
    };

    $scope.setNewAddress = function () {
        $scope.newAddress = true;
        $scope.editAddress = false;
        $scope.entity.address = {};
    };

    $scope.setEditAddress = function () {
        $scope.newAddress = false;
        $scope.editAddress = true;
    };

    function validateInputs() {
        if ($scope.entity == null) {
            showSimpleAlert(false, "Необходимо ввести данные о юр. лице.");
            return false;
        }
        if (isEmpty($scope.entity.name)) {
            showSimpleAlert(false, "Необходимо ввести название.");
            return false;
        }
        if (!isValidPhone($scope.entity.phone)) {
            showSimpleAlert(false, "Некорректно указан телефон.");
            return false;
        }
        if ($scope.entity.email == null) {
            showSimpleAlert(false, "Некорректно указан e-mail.");
            return false;
        }
        if (!isValidCode($scope.entity.personalAccount, 8)) {
            showSimpleAlert(false, "Некорректно указан персональный счёт.");
            return false;
        }
        if (!isValidCode($scope.entity.ogrn, 13)) {
            showSimpleAlert(false, "Некорректно указан ОГРН.");
            return false;
        }
        if (!isValidFlexibleCode($scope.entity.inn, 10, 12)) {
            showSimpleAlert(false, "Некорректно указан ИНН.");
            return false;
        }
        if (!isValidCode($scope.entity.kpp, 9)) {
            showSimpleAlert(false, "Некорректно указан КПП.");
            return false;
        }
        if ($scope.entity.registrationDate == null) {
            showSimpleAlert(false, "Необходимо ввести дату выдачи паспорта.");
            return false;
        }

        if ($scope.entity.address == null) {
            showSimpleAlert(false, "Необходимо ввести данные об адресе.");
            return false;
        } else {
            if ($scope.newAddress || $scope.editAddress) {
                if (!isValidAddress($scope.entity.address)) {
                    showSimpleAlert(false, "Некорректно указан адрес.");
                    return false;
                }
            }
        }
        return true;
    }

    $scope.add = function () {
        if (validateInputs()) {
            $http({
                url: '/entities/editor/',
                method: 'POST',
                params: {
                    entity: $scope.entity
                }
            }).then(function (response) {
                showAlert(response);
                $scope.entity = {};
                loadAddresses();
            }, function (response) {
                showAlert(response);
            });
        }
    };

    $scope.update = function () {
        if (validateInputs()) {
            $http({
                url: '/entities/editor/',
                method: 'PUT',
                params: {
                    entity: $scope.entity
                }
            }).then(function (response) {
                showAlert(response);
                loadAddresses();
                for (var i = 0; i < $scope.addresses.length; i++) {
                    if ($scope.addresses[i].id == $scope.entity.address.id) {
                        $scope.entity.address = $scope.addresses[i];
                        break;
                    }
                }
            }, function (response) {
                showAlert(response);
            });
        }
    };
});