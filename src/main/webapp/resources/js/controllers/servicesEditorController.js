/**
 * Created by user on 01.06.2016.
 */
var servicesEditor = angular.module("servicesEditor", []);
servicesEditor.controller('servicesEditorController', function($scope, $http, $routeParams) {
    if ($routeParams['id'] != null && $routeParams['id'].trim() != '') {
        $http({
            url:'/extra_services/editor/',
            method:'GET',
            params: { id: $routeParams['id'] }
        }).then(function(response){
            $scope.service = response.data;
        }, function(response){
            showAlert(response);
        });
        $scope.isUpdate = true
    } else {
        $scope.isUpdate = false;
    }

    function validateInput() {
        if ($scope.service == null) {
            showSimpleAlert(false, "Необходимо ввести данные об услуге.");
            return false;
        }
        if (isEmpty($scope.service.name)) {
            showSimpleAlert(false, "Необходимо название услуги.");
            return false;
        }
        if (!isValidFloat($scope.service.price, 0, null)) {
            showSimpleAlert(false, "Некорректно указана цена.");
            return false;
        }
        return true;
    }

    $scope.add = function () {
        if (validateInput()) {
            $http({
                url: '/extra_services/editor/',
                method: 'POST',
                params: {
                    service: $scope.service
                }
            }).then(function (response) {
                showAlert(response);
                $scope.service = {};
            }, function (response) {
                showAlert(response);
            });
        }
    };

    $scope.update = function () {
        if (validateInput()) {
            $http({
                url: '/extra_services/editor/',
                method: 'PUT',
                params: {
                    service: $scope.service
                }
            }).then(function (response) {
                showAlert(response);
            }, function (response) {
                showAlert(response);
            });
        }
    };
});