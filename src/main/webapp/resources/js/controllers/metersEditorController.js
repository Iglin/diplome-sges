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
            $scope.meter = response.data;
            $scope.modelIdFromReq = $scope.meters.model.id;
        }, function(response){
            alert(JSON.stringify(response));
        });
        $scope.isUpdate = true;
        loadModels();
    } else {
        $scope.isUpdate = false;
        loadModels();
    }


    function loadModels() {
        if ($scope.models == null) {
            $http({
                url:'/models/table/',
                method:'GET'
            }).then(function(response){
                $scope.models = response.data;
            }, function(response){
                alert(JSON.stringify(response));
            });
        }
    }

    function prepareToSend() {
        $scope.meter.model = $scope.models[$scope.modelId];
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