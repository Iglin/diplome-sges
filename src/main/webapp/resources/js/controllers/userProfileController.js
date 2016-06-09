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
        showAlert(response);
    });

    function validateInput() {
        if ($scope.user == null) {
            showSimpleAlert(false, "Необходимо ввести логин и пароль.");
            return false;
        }
        if (isEmpty($scope.user.login)) {
            showSimpleAlert(false, "Необходимо ввести логин.");
            return false;
        } else if ($scope.user.login.length < 4 || $scope.user.login.length > 16) {
            showSimpleAlert(false, "Допустимая длина имени пользователя от 4 до 16 символов.");
            return false;
        } else if ($scope.user.login.indexOf(' ') != -1) {
            showSimpleAlert(false, "Имя пользователя не может содержать пробелы.");
            return false;
        }
        if (isEmpty($scope.user.password)) {
            showSimpleAlert(false, "Необходимо ввести пароль.");
            return false;
        } else if ($scope.user.password.length < 4 || $scope.user.password.length > 16) {
            showSimpleAlert(false, "Допустимая длина пароля от 4 до 16 символов.");
            return false;
        } else if ($scope.user.password.indexOf(' ') != -1) {
            showSimpleAlert(false, "Пароль не может содержать пробелы.");
            return false;
        }
        return true;
    }

    $scope.update = function () {
        if (validateInput()) {
            if ($scope.user.password == $scope.password2) {
                $http({
                    url: '/user/',
                    method: 'PUT',
                    params: {
                        user: $scope.user
                    }
                }).then(function (response) {
                    window.location.href = "/logout";
                }, function (response) {
                    showAlert(response);
                });
            } else {
                showSimpleAlert(false, 'Пароли не совпадают!');
            }
        }
    };
});