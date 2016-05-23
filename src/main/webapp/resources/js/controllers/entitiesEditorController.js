/**
 * Created by user on 23.05.2016.
 */
var entitiesEditor = angular.module("entitiesEditor", []);
entitiesEditor.controller('entitiesEditorController', function($scope, $http, $routeParams) {
  //  angular.element(document).ready(function () {
    if ($routeParams['id'] != null && $routeParams['id'].trim() != '') {
        $http({
            url:'/entities/editor/',
            method:'GET',
            params: { id: $routeParams['id'] }
        }).then(function(response){
            $scope.entity = response.data;
            $scope.addressIdFromReq = $scope.entity.address.id;
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
  //  });


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
        $scope.needToReadAddress = false;
        if ($scope.newAddress) {
            $scope.entity.address.id = null;
        } else if (!$scope.editAddress) {
            $scope.entity.address = $scope.addresses[$scope.addressId];
        }
    }

    $scope.add = function () {
        prepareToSend();
        $http({
            url: '/entities/editor/',
            method: 'POST',
            params: {
                entity: $scope.entity
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
            url: '/entities/editor/',
            method: 'PUT',
            params: {
                entity: $scope.entity
            }
        }).then(function (response) {
            alert(response.data);
        }, function (response) {
            alert(JSON.stringify(response));
        });
    };
});