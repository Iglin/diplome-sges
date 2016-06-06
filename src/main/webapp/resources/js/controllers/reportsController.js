/**
 * Created by user on 06.06.2016.
 */
var reports = angular.module("reports", []);
reports.controller('reportsController', function($scope, $http){
    $scope.test = function(){
        $http({
            url:'/reports/',
            method:'GET'
        }).then(function(response){
            $scope.result = response.data;
        }, function(response){
            alert(JSON.stringify(response));
        });
    };

    $scope.fillDataBase = function(){
        $http({
            url:'/reports/',
            method:'PUT'
        }).then(function(response){
            alert(response.data);
        }, function(response){
            alert(JSON.stringify(response));
        });
    };
});
