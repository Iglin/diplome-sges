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
            alert(JSON.stringify(response));
        });
        $scope.isUpdate = true;
    } else {
        $scope.rolesSelect = { opt: $scope.roles[0].value };
        $scope.isUpdate = false;
    }

    function prepareToSend() {
        if ($scope.account.password != $scope.password2) {
            alert('Пароли не совпадают!');
            stopImmediatePropagation();
        } else {
            $scope.account.role = $scope.rolesSelect.opt;
        }
    }

    $scope.add = function () {
        prepareToSend();
        $http({
            url: '/accounts/editor/',
            method: 'POST',
            params: {
                account: $scope.account
            }
        }).then(function (response) {
            alert(response.data);
        }, function (response) {
            alert(JSON.stringify(response));
        });
    };

    $scope.update = function () {
        prepareToSend();
        $http({
            url: '/accounts/editor/',
            method: 'PUT',
            params: {
                account: $scope.account
            }
        }).then(function (response) {
            alert(response.data);
        }, function (response) {
            alert(JSON.stringify(response));
        });
    };
});