/**
 * Created by user on 13.05.2016.
 */
var test = angular.module("test", []);
test.controller('testController', function($scope, $http){
    $scope.test = function(){
        $http({
            url:'/test/',
            method:'GET'
        }).then(function(response){
            $scope.result = response.data;
        }, function(response){
            alert(JSON.stringify(response));
        });
    };
});
