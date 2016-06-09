/**
 * Created by user on 02.06.2016.
 */
var accountsEditor = angular.module("accountsEditor", []);
accountsEditor.controller('accountsEditorController', function($scope, $http, $routeParams) {
    $scope.roles = [];
    $scope.roles[0] = { name: 'Администратор', value: 'ADMIN'};
    $scope.roles[1] = { name: 'Оператор', value: 'OPERATOR'};
    $scope.roles[2] = { name: 'Руководитель', value: 'DIRECTOR'};
    if ($routeParams['id'] != null && $routeParams['id'].trim() != '') {
        $http({
            url:'/accounts/editor/',
            method:'GET',
            params: { id: $routeParams['id'] }
        }).then(function(response){
            $scope.account = response.data;
            $scope.rolesSelect = { opt: $scope.account.role };
            $scope.password2 = $scope.account.password;
        }, function(response){
            showAlert(response);
        });
        $scope.isUpdate = true;
    } else {
        $scope.rolesSelect = { opt: $scope.roles[0].value };
        $scope.isUpdate = false;
    }

    function prepareToSend() {
        if ($scope.account == null) {
            showSimpleAlert(false, "Необходимо ввести учётные данные нового пользователя.");
            return false;
        }
        if (isEmpty($scope.account.login)) {
            showSimpleAlert(false, "Необходимо ввести логин для нового пользователя.");
            return false;
        } else if ($scope.account.login.length < 4 || $scope.account.login.length > 16) {
            showSimpleAlert(false, "Допустимая длина имени пользователя от 4 до 16 символов.");
            return false;
        } else if ($scope.account.login.indexOf(' ') != -1) {
            showSimpleAlert(false, "Имя пользователя не может содержать пробелы.");
            return false;
        }
        if (isEmpty($scope.account.password)) {
            showSimpleAlert(false, "Необходимо ввести пароль для нового пользователя.");
            return false;
        } else if ($scope.account.password.length < 4 || $scope.account.password.length > 16) {
            showSimpleAlert(false, "Допустимая длина пароля от 4 до 16 символов.");
            return false;
        } else if ($scope.account.password.indexOf(' ') != -1) {
            showSimpleAlert(false, "Пароль не может содержать пробелы.");
            return false;
        }

        if ($scope.account.password != $scope.password2) {
            showSimpleAlert(false, 'Пароли не совпадают!');
            return false;
        } else {
            $scope.account.role = $scope.rolesSelect.opt;
        }
        return true;
    }

    $scope.add = function () {
        if (prepareToSend()) {
            $http({
                url: '/accounts/editor/',
                method: 'POST',
                params: {
                    account: $scope.account
                }
            }).then(function (response) {
                showAlert(response);
                $scope.account = {};
                $scope.password2 = "";
            }, function (response) {
                showAlert(response);
            });
        }
    };

    $scope.update = function () {
        if (prepareToSend()) {
            $http({
                url: '/accounts/editor/',
                method: 'PUT',
                params: {
                    account: $scope.account
                }
            }).then(function (response) {
                showAlert(response);
            }, function (response) {
                showAlert(response);
            });
        }
    };
});