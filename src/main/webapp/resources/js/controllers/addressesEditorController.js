/**
 * Created by user on 15.05.2016.
 */
var addressesEditor = angular.module("addressesEditor", []);
addressesEditor.controller('addressesEditorController', function($scope, $http, $routeParams) {
    angular.element(document).ready(function () {
        if ($routeParams['id'] != null && $routeParams['id'].trim() != '') {
            $http({
                url:'/addresses/editor/',
                method:'GET',
                params: { id: $routeParams['id'] }
            }).then(function(response){
                $scope.address = response.data;
                $scope.region = $scope.address.region;
                $scope.city = $scope.address.city;
                $scope.street = $scope.address.street;
                $scope.building = $scope.address.building;
                $scope.apartment = $scope.address.apartment;
                $scope.index = $scope.address.index;
            }, function(response){
                alert(JSON.stringify(response));
            });
            $scope.isUpdate = true;
        } else {
            $scope.isUpdate = false;
        }
    });

    $scope.add = function () {
        $http({
            url: '/addresses/editor/',
            method: 'POST',
            params: {
                region: $scope.region, city: $scope.city, street: $scope.street, building: $scope.building,
                apartment: $scope.apartment, index: $scope.index
            }
        }).then(function (response) {
            alert(response.data);
            $scope.region = '';
            $scope.city = '';
            $scope.street = '';
            $scope.building = '';
            $scope.apartment = '';
            $scope.index = '';
        }, function (response) {
            alert(JSON.stringify(response));
        });
    };

    $scope.update = function () {
        $http({
            url: '/addresses/editor/',
            method: 'PUT',
            params: {
                id: $scope.address.id,
                region: $scope.region, city: $scope.city, street: $scope.street, building: $scope.building,
                apartment: $scope.apartment, index: $scope.index
            }
        }).then(function (response) {
            alert(response.data);
        }, function (response) {
            alert(JSON.stringify(response));
        });
    };
});