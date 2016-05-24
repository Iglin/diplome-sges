/**
 * Created by user on 25.05.2016.
 */
var enterpriseEditor = angular.module("enterpriseEditor", []);
enterpriseEditor.controller('enterpriseEditorController', function($scope, $http, $routeParams) {
    if ($routeParams['id'] != null && $routeParams['id'].trim() != '') {
        $http({
            url:'/enterprise/editor/',
            method:'GET',
            params: { id: $routeParams['id'] }
        }).then(function(response){
            $scope.enterprise = response.data;
            $scope.addressIdFromReq = $scope.enterprise.bankAddress.id;
          //  alert(JSON.stringify($scope.enterprise.bankInn));
        }, function(response){
            alert(JSON.stringify(response));
        });
        $scope.isUpdate = true;
        $scope.newAddress = false;
        $scope.editAddress = true;
    } else {
        $scope.isUpdate = false;
        $scope.newAddress = false;
        $scope.editAddress = false;
        loadAddresses();
    }


    function loadAddresses() {
        if ($scope.addresses == null) {
            $http({
                url:'/addresses/table/',
                method:'GET'
            }).then(function(response){
                $scope.addresses = response.data;
            }, function(response){
                alert(JSON.stringify(response));
            });
        }
    }

    $scope.pickAddressFromDB = function () {
        loadAddresses();
        $scope.newAddress = false;
        $scope.editAddress = false;
    };

    $scope.setNewAddress = function () {
        $scope.newAddress = true;
        $scope.editAddress = false;
    };

    $scope.setEditAddress = function () {
        $scope.newAddress = false;
        $scope.editAddress = true;
    };

    function prepareToSend() {
     //   $scope.needToReadAddress = false;
        if ($scope.newAddress) {
            $scope.enterprise.bankAddress.id = null;
        } else if (!$scope.editAddress) {
            $scope.enterprise.bankAddress = $scope.addresses[$scope.addressId];
        }
    }

    $scope.add = function () {
        prepareToSend();
        $http({
            url: '/enterprise/editor/',
            method: 'POST',
            params: {
                enterprise: $scope.enterprise
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
            url: '/enterprise/editor/',
            method: 'PUT',
            params: {
                enterprise: $scope.enterprise
            }
        }).then(function (response) {
            alert(response.data);
        }, function (response) {
            alert(JSON.stringify(response));
        });
    };
});