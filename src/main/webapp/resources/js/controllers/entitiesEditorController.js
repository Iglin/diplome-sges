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

    function buildEntity() {
        var livingAddr = {};
        if ($scope.newLivingAddress || $scope.editLivingAddress) {
            livingAddr = {
                region: $scope.regionLiv,
                city: $scope.cityLiv,
                street: $scope.streetLiv,
                building: $scope.buildingLiv,
                apartment: $scope.apartmentLiv,
                index: $scope.indexLiv
            };
        } else {
            livingAddr = JSON.parse($scope.livingAddress);
        }

        var registrationAddr = {};
        if ($scope.newRegistrationAddress || $scope.editRegistrationAddress) {
            registrationAddr = {
                region: $scope.regionReg,
                city: $scope.cityReg,
                street: $scope.streetReg,
                building: $scope.buildingReg,
                apartment: $scope.apartmentReg,
                index: $scope.indexReg
            };
        } else {
            registrationAddr = JSON.parse($scope.registrationAddress);
        }

        if ($scope.isUpdate) {
            if ($scope.editLivingAddress) {
                livingAddr.id = $scope.addressIdFromReq;
            }
        }
        var passportData = {
            passportNumber: $scope.passportNumber,
            placeOfIssue: $scope.placeOfIssue,
            dateOfIssue: $scope.dateOfIssue,
            registrationAddress: registrationAddr
        };
        return {
            lastName: $scope.lastName,
            firstName: $scope.firstName,
            middleName: $scope.middleName,
            phone: $scope.phone,
            email: $scope.email,
            personalAccount: $scope.personalAccount,
            livingAddress: livingAddr,
            passport: passportData
        };
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

    $scope.add = function () {
        if ($scope.newAddress) { $scope.entity.address.id = null; }
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
        if ($scope.newAddress) {
            $scope.entity.address.id = null;
        }
        $http({
            url: '/entities/editor/',
            method: 'PUT',
            params: {
              //  id: $scope.entity.id,
                entity: $scope.entity
            }
        }).then(function (response) {
            alert(response.data);
        }, function (response) {
            alert(JSON.stringify(response));
        });
    };
});