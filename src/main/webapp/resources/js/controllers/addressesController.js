/**
 * Created by user on 13.05.2016.
 */
var addresses = angular.module("addressesTable", []);
addresses.controller('addressesController', function($scope, $http){
    angular.element(document).ready(function () {
        $http({
            url:'/addresses/table/',
            method:'GET'
        }).then(function(response){
            $scope.addresses = response.data;
        }, function(response){
            alert(JSON.stringify(response));
        });
    });
});