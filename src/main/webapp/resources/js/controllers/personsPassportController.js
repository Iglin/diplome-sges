/**
 * Created by root on 18.05.16.
 */
var personsPassport = angular.module("personsPassport", []);
personsPassport.controller('personsPassportController', function($scope, $http, $routeParams){
    function refreshTable() {
        $http({
            url:'/persons/passport/',
            method:'GET',
            params: { id: $routeParams['id'] }
        }).then(function(response){
            $scope.passport = response.data;
        }, function(response){
            showAlert(response);
        });
    }
    angular.element(document).ready(function () {
        refreshTable();
    });
});