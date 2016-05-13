/**
 * Created by user on 13.05.2016.
 */
var halls = angular.module("owners", []);
halls.controller('ownersController', function($scope, $http){
    $scope.all = function(){
        $http({
            url:'/owners/',
            method:'GET'
        }).then(function(response){
            $scope.owners = response.data;
        }, function(response){
            alert(JSON.stringify(response));
        });
    };
    $scope.add = function(){
        $http({
            url:'/owners/',
            method:'POST',
            params:{phone: $scope.phone, email: $scope.email, personal_acc: $scope.personal_acc}
        }).then(function(response){
            alert(response.data);
            $scope.phone = '';
            $scope.email = '';
            $scope.personal_acc = '' ;
        }, function(response){
            alert(JSON.stringify(response));
        });
    };
    $scope.delete = function(){
        $http({
            url:'/owners/'+$scope.idDelete,
            method:'DELETE'
        }).then(function(response){
            alert(response.data);
            $scope.idDelete = '';
        }, function(response){
            alert(JSON.stringify(response));
        });
    };
    $scope.update = function(){
        $http({
            url:'/owners/'+$scope.id_up,
            method:'PUT',
            params:{phone: $scope.phone_up, email: $scope.email_up, personal_acc: $scope.personal_acc_up}
        }).then(function(response){
            alert(response.data);
            $scope.personal_acc_up = '';
            $scope.phone_up = '';
            $scope.email_up = '';
            $scope.id_up = '' ;
        }, function(response){
            alert(JSON.stringify(response));
        });
    };
});