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

    $scope.add = function () {
        $http({
            url: '/models/editor/',
            method: 'POST',
            params: {
                model: $scope.model
            }
        }).then(function (response) {
            alert(response.data);
        }, function (response) {
            alert(JSON.stringify(response));
        });
    };

    $scope.update = function () {
        $http({
            url: '/models/editor/',
            method: 'PUT',
            params: {
                model: $scope.model
            }
        }).then(function (response) {
            alert(response.data);
        }, function (response) {
            alert(JSON.stringify(response));
        });
    };
});