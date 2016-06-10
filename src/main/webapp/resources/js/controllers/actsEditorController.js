/**
 * Created by user on 10.06.2016.
 */
var actsEditor = angular.module("actsEditor", []);
actsEditor.controller('actsEditorController', function($scope, $http, $routeParams) {
    if ($routeParams['id'] != null && $routeParams['id'].trim() != '') {
        $http({
            url:'/acts/editor/',
            method:'GET',
            params: { id: $routeParams['id'] }
        }).then(function(response){
            var paramsMap = response.data;
            $scope.agreements = paramsMap['agreements'];
            $scope.act = paramsMap['act'];
            var arr = $scope.act.date.split('-');
            $scope.act.date = new Date(arr[0], --arr[1], arr[2]);
            for (var i = 0; i < $scope.agreements.length; i++) {
                if ($scope.agreements[i].id == $scope.act.agreement.id) {
                    $scope.act.agreement = $scope.agreements[i];
                    break;
                }
            }
        }, function(response){
            showAlert(response);
        });
        $scope.isUpdate = true;
        $scope.newAddress = false;
        $scope.editAddress = true;
    } else {
        $http({
            url:'/acts/editor/',
            method:'GET'
        }).then(function(response){
            var paramsMap = response.data;
            $scope.agreements = paramsMap['agreements'];
        }, function(response){
            showAlert(response);
        });
        $scope.act = {};
        $scope.isUpdate = false;
        $scope.newAddress = false;
        $scope.editAddress = false;
    }

    $scope.pickAgreement = function(agr) {
        $scope.act.agreement = agr;
    };


    function validateInputs() {
        if ($scope.act == null) {
            showSimpleAlert(false, "Необходимо ввести данные об акте.");
            return false;
        }
        if (!isValidInt($scope.act.number, 1, null)) {
            showSimpleAlert(false, "Некорректно указан номер акта.");
            return false;
        }
        if ($scope.act.date == null) {
            showSimpleAlert(false, "Необходимо ввести дату.");
            return false;
        }

        if ($scope.act.agreement == null) {
            showSimpleAlert(false, "Необходимо выбрать договор, к которому прилагается данный акт.");
            return false;
        }
        return true;
    }

    $scope.add = function () {
        if (validateInputs()) {
            $http({
                url: '/acts/editor/',
                method: 'POST',
                params: {
                    act: $scope.act
                }
            }).then(function (response) {
                showAlert(response);
                $scope.act = {};
            }, function (response) {
                showAlert(response);
            });
        }
    };

    $scope.update = function () {
        if (validateInputs()) {
            $http({
                url: '/acts/editor/',
                method: 'PUT',
                params: {
                    act: $scope.act
                }
            }).then(function (response) {
                showAlert(response);
            }, function (response) {
                showAlert(response);
            });
        }
    };
});