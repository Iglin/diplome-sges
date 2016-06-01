/**
 * Created by user on 01.06.2016.
 */
var servicesEditor = angular.module("servicesEditor", []);
servicesEditor.controller('servicesEditorController', function($scope, $http, $routeParams) {
    if ($routeParams['id'] != null && $routeParams['id'].trim() != '') {
        $http({
            url:'/extra_services/editor/',
            method:'GET',
            params: { id: $routeParams['id'] }
        }).then(function(response){
            $scope.service = response.data;
        }, function(response){
            alert(JSON.stringify(response));
        });
        $scope.isUpdate = true
    } else {
        $scope.isUpdate = false;
    }

    $scope.add = function () {
        $http({
            url: '/extra_services/editor/',
            method: 'POST',
            params: {
                service: $scope.service
            }
        }).then(function (response) {
            alert(response.data);
        }, function (response) {
            alert(JSON.stringify(response));
        });
    };

    $scope.update = function () {
        $http({
            url: '/extra_services/editor/',
            method: 'PUT',
            params: {
                service: $scope.service
            }
        }).then(function (response) {
            alert(response.data);
        }, function (response) {
            alert(JSON.stringify(response));
        });
    };
});