/**
 * Created by user on 13.05.2016.
 */
var addresses = angular.module("addresses", []);
addresses.controller('addressesController', function($scope, $http){
    $http({
        url:'/addresses/',
        method:'GET'
    }).then(function(response){
        $scope.addresses = response;
    }, function(response){
        alert(JSON.stringify(response));
    });

    $scope.add = function(){
        $http({
            url:'/addresses/',
            method:'POST',
            params:{region: $scope.region, city: $scope.city, street: $scope.street, building: $scope.building,
                apartment: $scope.apartment, index: $scope.index}
        }).then(function(response){
            alert(response.data);
            $scope.region = '';
            $scope.city = '';
            $scope.street = '';
            $scope.building = '';
            $scope.apartment = '';
            $scope.index = '';
        }, function(response){
            alert(JSON.stringify(response));
        });
    };
});