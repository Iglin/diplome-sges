/**
 * Created by user on 25.05.2016.
 */
var metersEditor = angular.module("metersEditor", []);
metersEditor.controller('metersEditorController', function($scope, $http, $routeParams) {
    if ($routeParams['id'] != null && $routeParams['id'].trim() != '') {
        $http({
            url:'/meters/editor/',
            method:'GET',
            params: { id: $routeParams['id'] }
        }).then(function(response){
            var paramsMap = response.data;
            $scope.meter = paramsMap['meter'];
            $scope.models = paramsMap['models'];
            $scope.isUpdate = true;
            var arr = $scope.meter.lastCalibrationDate.split('-');
            $scope.meter.lastCalibrationDate = new Date(arr[0], --arr[1], arr[2]);
            $scope.modelsSelect = { opt: $scope.meter.model.id };
        }, function(response){
            showAlert(response);
        });
    } else {
        $http({
            url:'/meters/editor/',
            method:'GET'
        }).then(function(response){
            var paramsMap = response.data;
            $scope.models = paramsMap['models'];
            $scope.meter = {};
            $scope.isUpdate = false;
            $scope.modelsSelect = { opt: $scope.models[0].id };
        }, function(response){
            showAlert(response);
        });
    }

    function prepareToSend() {
        if ($scope.meter == null) {
            showSimpleAlert(false, "Необходимо ввести данные о счётчике.");
            return false;
        }
        $scope.meter.model = findObjectById($scope.models, $scope.modelsSelect.opt);

        if (isEmpty($scope.meter.serialNumber)) {
            showSimpleAlert(false, "Необходимо ввести серийный номер.");
            return false;
        }
        if (!isValidInt($scope.meter.productionYear, 1900, 2100)) {
            showSimpleAlert(false, "Некорректно указан год производства.");
            return false;
        }
        if ($scope.meter.lastCalibrationDate == null) {
            showSimpleAlert(false, "Необходимо ввести дату последней поверки.");
            return false;
        }
        if (!isValidFloat($scope.meter.startingReadout, 0, null)) {
            showSimpleAlert(false, "Некорректно указаны начальные показания.");
            return false;
        }

        return true;
    }

    $scope.add = function () {
        if (prepareToSend()) {
            $http({
                url: '/meters/editor/',
                method: 'POST',
                params: {
                    meter: $scope.meter
                }
            }).then(function (response) {
                showAlert(response);
                $scope.meter = {};
            }, function (response) {
                showAlert(response);
            });
        }
    };

    $scope.update = function () {
        if (prepareToSend()) {
            $http({
                url: '/meters/editor/',
                method: 'PUT',
                params: {
                    meter: $scope.meter
                }
            }).then(function (response) {
                showAlert(response);
            }, function (response) {
                showAlert(response);
            });
        }
    };
});

function findObjectById(arr, id) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i].id == id) {
            return arr[i];
        }
    }
    alert('No such id!');
    return null;
}