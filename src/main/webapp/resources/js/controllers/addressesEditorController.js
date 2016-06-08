/**
 * Created by user on 15.05.2016.
 */
var addressesEditor = angular.module("addressesEditor", []);
addressesEditor.controller('addressesEditorController', function($scope, $http, $routeParams) {
    angular.element(document).ready(function () {
        if ($routeParams['id'] != null && $routeParams['id'].trim() != '') {
            $http({
                url:'/addresses/editor/',
                method:'GET',
                params: { id: $routeParams['id'] }
            }).then(function(response){
                $scope.address = response.data;
                $scope.region = $scope.address.region;
                $scope.city = $scope.address.city;
                $scope.street = $scope.address.street;
                $scope.building = $scope.address.building;
                $scope.apartment = $scope.address.apartment;
                $scope.index = $scope.address.index;
            }, function(response){
                alert(JSON.stringify(response));
            });
            $scope.isUpdate = true;
        } else {
            $scope.isUpdate = false;
        }
    });
    
    function validateInput() {
        if ($scope.region == null || $scope.region.trim() == '') {
            showSimpleAlert(false,"Необходимо ввести регион!");
            return false;
        }
        if ($scope.city == null || $scope.city.trim() == '') {
            showSimpleAlert(false,"Необходимо ввести город!");
            return false;
        }
        if ($scope.street == null || $scope.street.trim() == '') {
            showSimpleAlert(false,"Необходимо ввести улицу!");
            return false;
        }
        if ($scope.building == null || $scope.building.trim() == '') {
            showSimpleAlert(false,"Необходимо ввести номер дома!");
            return false;
        }
        if ($scope.index == null || $scope.index.trim() == '') {
            showSimpleAlert(false,"Необходимо ввести индекс!");
            return false;
        }
        if ($scope.index.length < 6 || $scope.index.length > 7 || !isInteger($scope.index)) {
            showSimpleAlert(false,"Индекс должен состоять из 6 или 7 цифр!");
            return false;
        } else {
            var int = parseFloat($scope.index);
            alert(int);
            if (int < 100000) {
                showSimpleAlert(false,"Индекс должен состоять из 6 или 7 цифр!");
                return false;
            }
        }
        return true;
    }

    $scope.add = function () {
        if (validateInput()) {
            $http({
                url: '/addresses/editor/',
                method: 'POST',
                params: {
                    region: $scope.region, city: $scope.city, street: $scope.street, building: $scope.building,
                    apartment: $scope.apartment, index: $scope.index
                }
            }).then(function (response) {
                showAlert(response);
            }, function (response) {
                alert(JSON.stringify(response));
            });
        }
    };

    $scope.update = function () {
        if (validateInput()) {
            $http({
                url: '/addresses/editor/',
                method: 'PUT',
                params: {
                    id: $scope.address.id,
                    region: $scope.region, city: $scope.city, street: $scope.street, building: $scope.building,
                    apartment: $scope.apartment, index: $scope.index
                }
            }).then(function (response) {
                alert(response.data);
            }, function (response) {
                alert(JSON.stringify(response));
            });
        }
    };
});