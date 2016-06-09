/**
 * Created by user on 25.05.2016.
 */
var enterpriseEditor = angular.module("enterpriseEditor", []);
enterpriseEditor.controller('enterpriseEditorController', function($scope, $http, $routeParams) {
    if ($routeParams['id'] != null && $routeParams['id'].trim() != '') {
        $http({
            url:'/enterprise/editor/',
            method:'GET',
            params: { id: $routeParams['id'] }
        }).then(function(response){
            $scope.enterprise = response.data;
        }, function(response){
            showAlert(response);
        });
        $scope.isUpdate = true;
        $scope.newAddress = false;
        $scope.editAddress = true;
    } else {

        $scope.isUpdate = false;
        $scope.newAddress = false;
        $scope.editAddress = false;
        loadAddresses();
    }


    function loadAddresses() {
        if ($scope.addresses == null) {
            $http({
                url:'/addresses/table/',
                method:'GET'
            }).then(function(response){
                $scope.addresses = response.data;
                if ($scope.isUpdate) {
                    $scope.addressesSelect = { opt: $scope.enterprise.bankAddress.id };
                } else {
                    $scope.addressesSelect = { opt: $scope.addresses[0].id };
                }
            }, function(response){
                showAlert(response);
            });
        }
    }

    $scope.pickAddressFromDB = function () {
        loadAddresses();
        if ($scope.isUpdate) {
            $scope.addressesSelect = { opt: $scope.enterprise.bankAddress.id };
        } else {
            $scope.addressesSelect = { opt: $scope.addresses[0].id };
        }
        $scope.newAddress = false;
        $scope.editAddress = false;
    };

    $scope.setNewAddress = function () {
        $scope.newAddress = true;
        $scope.editAddress = false;
    };

    $scope.setEditAddress = function () {
        $scope.newAddress = false;
        $scope.editAddress = true;
    };

    function prepareToSend() {
        if ($scope.enterprise == null) {
            showSimpleAlert(false, "Необходимо ввести данные предприятия.");
            return false;
        }
        if ($scope.newAddress) {
            if (!isValidAddress($scope.enterprise.bankAddress)) {
                showSimpleAlert(false, "Не удалось зарегистрировать адрес банка.");
                return false;
            }
            $scope.enterprise.bankAddress.id = null;
        } else if (!$scope.editAddress) {
            $scope.enterprise.bankAddress = findObjectById($scope.addresses, $scope.addressesSelect.opt);
        } else {
            if (!isValidAddress($scope.enterprise.bankAddress)) {
                showSimpleAlert(false, "Не удалось зарегистрировать адрес банка.");
                return false;
            }
        }

        if (isEmpty($scope.enterprise.name)) {
            showSimpleAlert(false, "Необходимо ввести название предприятия.");
            return false;
        }
        if (isEmpty($scope.enterprise.director)) {
            showSimpleAlert(false, "Необходимо ввести имя директора.");
            return false;
        }
        if (isEmpty($scope.enterprise.registryChief)) {
            showSimpleAlert(false, "Необходимо ввести имя начальника служба учёта.");
            return false;
        }
        if (!isValidCode($scope.enterprise.correspondentAccount, 20)) {
            showSimpleAlert(false, "Некорректно указан к/с.");
            return false;
        }
        if (!isValidCode($scope.enterprise.checkingAccount, 20)) {
            showSimpleAlert(false, "Некорректно указан р/с.");
            return false;
        }

        if (isEmpty($scope.enterprise.bank)) {
            showSimpleAlert(false, "Необходимо ввести название банка.");
            return false;
        }
        if (!isValidCode($scope.enterprise.bankBik, 9)) {
            showSimpleAlert(false, "Некорректно указан БИК банка.");
            return false;
        }
        if (!isValidFlexibleCode($scope.enterprise.bankInn, 10, 12)) {
            showSimpleAlert(false, "Некорректно указан ИНН банка.");
            return false;
        }
        if (!isValidCode($scope.enterprise.bankKpp, 9)) {
            showSimpleAlert(false, "Некорректно указан КПП банка.");
            return false;
        }
        
        return true;
    }

    $scope.add = function () {
        if (prepareToSend()) {
            $http({
                url: '/enterprise/editor/',
                method: 'POST',
                params: {
                    enterprise: $scope.enterprise
                }
            }).then(function (response) {
                showAlert(response);
                $scope.enterprise = {};
            }, function (response) {
                showAlert(response);
            });
        }
    };

    $scope.update = function () {
        if (prepareToSend()) {
            $http({
                url: '/enterprise/editor/',
                method: 'PUT',
                params: {
                    enterprise: $scope.enterprise
                }
            }).then(function (response) {
                showAlert(response);
            }, function (response) {
                showAlert(response);
            });
        }
    };
});