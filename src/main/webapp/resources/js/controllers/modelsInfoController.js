/**
 * Created by user on 24.05.2016.
 */
var modelsInfo = angular.module("modelsInfo", []);
modelsInfo.controller('modelsInfoController', function($scope, $http, $routeParams){
    function refreshTable() {
        $http({
            url:'/models/info/',
            method:'GET',
            params: { id: $routeParams['id'] }
        }).then(function(response){
            $scope.model = response.data;
        }, function(response){
            showAlert(response);
        });
    }
    refreshTable();
    
    $scope.outputLogical = function (flag) {
        if (flag) {
            return "Да";
        } else {
            return "Нет";
        }
    }

    $scope.outputPhase = function (threePhase) {
        if (threePhase) {
            return "трёхфазный";
        } else {
            return "однофазный"
        }
    };
});