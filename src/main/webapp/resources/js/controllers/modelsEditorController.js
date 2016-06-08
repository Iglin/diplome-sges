/**
 * Created by user on 24.05.2016.
 */
var modelsEditor = angular.module("modelsEditor", []);
modelsEditor.controller('modelsEditorController', function($scope, $http, $routeParams) {
    if ($routeParams['id'] != null && $routeParams['id'].trim() != '') {
        $http({
            url:'/models/editor/',
            method:'GET',
            params: { id: $routeParams['id'] }
        }).then(function(response){
            $scope.model = response.data;
        }, function(response){
            alert(JSON.stringify(response));
        });
        $scope.isUpdate = true
    } else {
        $scope.isUpdate = false;
    }
    
    function validateInputs() {
        if ($scope.model == null) {
            showSimpleAlert(false, "Необходимо ввести данные о модели.");
            return false;
        }
        if (isEmpty($scope.model.name)) {
            showSimpleAlert(false, "Необходимо ввести название модели.");
            return false;
        }
        if (isEmpty($scope.model.manufacturer)) {
            showSimpleAlert(false, "Необходимо ввести производителя.");
            return false;
        }
        if (!isValidFloat($scope.model.price, 0, null)) {
            showSimpleAlert(false, "Некорректно указана цена.");
            return false;
        }
        if (!isValidInt($scope.model.calibrationPeriod, 1, null)) {
            showSimpleAlert(false, "Некорректно указан межпроверочный интервал.");
            return false;
        }
        if (!isValidInt($scope.model.ratesCount, 1, null)) {
            showSimpleAlert(false, "Некорректно указано кол-во тарифов.");
            return false;
        }
        if (isEmpty($scope.model.meterInterface)) {
            showSimpleAlert(false, "Необходимо ввести интерфейс.");
            return false;
        }
        if (!isValidInt($scope.model.atomicity, 1, null)) {
            showSimpleAlert(false, "Некорректно указана значность.");
            return false;
        }
        if (isEmpty($scope.model.accuracy)) {
            showSimpleAlert(false, "Необходимо ввести класс точности.");
            return false;
        }
        if (!isValidInt($scope.model.nominalCurrent, 0, null)) {
            showSimpleAlert(false, "Некорректно указан номинальный ток.");
            return false;
        }
        if (!isValidInt($scope.model.maximalCurrent, 0, null)) {
            showSimpleAlert(false, "Некорректно указан максимальный ток.");
            return false;
        }
        if (!isValidInt($scope.model.nominalVoltage, 0, null)) {
            showSimpleAlert(false, "Некорректно указано номинальное напряжение.");
            return false;
        }
        return true;
    }

    $scope.add = function () {
        if (validateInputs()) {
            $http({
                url: '/models/editor/',
                method: 'POST',
                params: {
                    model: $scope.model
                }
            }).then(function (response) {
                showAlert(response);
            }, function (response) {
                alert(JSON.stringify(response));
            });
        }
    };

    $scope.update = function () {
        if (validateInputs()) {
            $http({
                url: '/models/editor/',
                method: 'PUT',
                params: {
                    model: $scope.model
                }
            }).then(function (response) {
                showAlert(response);
            }, function (response) {
                alert(JSON.stringify(response));
            });
        }
    }
});