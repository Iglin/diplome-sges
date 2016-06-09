/**
 * Created by user on 26.05.2016.
 */
var pointsInfo = angular.module("pointsInfo", []);
pointsInfo.controller('pointsInfoController', function($scope, $http, $routeParams){
    $http({
        url:'/points/info/',
        method:'GET',
        params: { id: $routeParams['id'] }
    }).then(function(response){
        $scope.point = response.data;
        if ($scope.point.owner.hasOwnProperty('name')) {
            $scope.isPerson = false;
        } else {
            $scope.isPerson = true;
        }
    }, function(response){
        showAlert(response);
    });
});