/**
 * Created by user on 01.06.2016.
 */
var userProfile = angular.module("userProfile", []);
userProfile.controller('userProfileController', function($scope, $http, $routeParams) {
    $http({
        url:'/user/',
        method:'GET'
    }).then(function(response){
        $scope.user = response.data;
    }, function(response){
        alert(JSON.stringify(response));
    });

    $scope.update = function () {
        if ($scope.user.password == $scope.password2) {
            $http({
                url: '/user/',
                method: 'PUT',
                params: {
                    user: $scope.user
                }
            }).then(function (response) {
                //   alert(response.data);
            }, function (response) {
                alert(JSON.stringify(response));
            });
        } else {
            alert('Пароли не совпадают!');
        }
    };
});