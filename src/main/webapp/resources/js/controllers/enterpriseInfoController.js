/**
 * Created by user on 25.05.2016.
 */
var enterpriseInfo = angular.module("enterpriseInfo", []);
enterpriseInfo.controller('enterpriseInfoController', function($scope, $http, $routeParams){
    function refreshTable() {
        $http({
            url:'/enterprise/info/',
            method:'GET',
            params: { id: $routeParams['id'] }
        }).then(function(response){
            $scope.enterprise = response.data;
        }, function(response){
            showAlert(response);
        });
    }
    refreshTable();
});