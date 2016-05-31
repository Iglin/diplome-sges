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
            alert(JSON.stringify(response));
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
            alert(JSON.stringify(response));
        });
    }

    function prepareToSend() {
        $scope.meter.model = findObjectById($scope.models, $scope.modelsSelect.opt);
    }

    $scope.add = function () {
        prepareToSend();
        $http({
            url: '/meters/editor/',
            method: 'POST',
            params: {
                meter: $scope.meter
            }
        }).then(function (response) {
            alert(response.data);
        }, function (response) {
            alert(JSON.stringify(response));
        });
    };

    $scope.update = function () {
        prepareToSend();
        $http({
            url: '/meters/editor/',
            method: 'PUT',
            params: {
                meter: $scope.meter
            }
        }).then(function (response) {
            alert(response.data);
        }, function (response) {
            alert(JSON.stringify(response));
        });
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