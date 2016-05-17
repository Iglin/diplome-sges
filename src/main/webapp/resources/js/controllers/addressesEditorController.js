/**
 * Created by user on 15.05.2016.
 */
var addresses = angular.module("addressesEditor", []);
addresses.controller('addressesEditorController', function($scope, $http) {
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
});