/**
 * Created by root on 18.05.16.
 */
var personsEditor = angular.module("personsEditor", []);
personsEditor.controller('personsEditorController', function($scope, $http, $routeParams) {
    angular.element(document).ready(function () {
        $scope.needRegistrationAddress = true;
        $scope.needLivingAddress = true;

        if ($routeParams['id'] != null && $routeParams['id'].trim() != '') {
            $http({
                url:'/persons/editor/',
                method:'GET',
                params: { id: $routeParams['id'] }
            }).then(function(response){
                $scope.person = response.data;
                $scope.lastName = $scope.person.lastName;
                $scope.firstName = $scope.person.firstName;
                $scope.middleName = $scope.person.middleName;
                $scope.phone = $scope.person.phone;
                $scope.email = $scope.person.email;
                $scope.personalAccount = $scope.person.personalAccount;
                $scope.passportNumber = $scope.person.passport.passportNumber;
                $scope.placeOfIssue = $scope.person.passport.placeOfIssue;
                $scope.dateOfIssue = $scope.person.passport.dateOfIssue;

                $scope.regionLiv = $scope.person.livingAddress.region;
                $scope.cityLiv = $scope.person.livingAddress.city;
                $scope.streetLiv = $scope.person.livingAddress.street;
                $scope.buildingLiv = $scope.person.livingAddress.building;
                $scope.apartmentLiv = $scope.person.livingAddress.apartment;
                $scope.indexLiv = $scope.person.livingAddress.index;

                $scope.regionReg = $scope.person.passport.registrationAddress.region;
                $scope.cityReg = $scope.person.passport.registrationAddress.city;
                $scope.streetReg = $scope.person.passport.registrationAddress.street;
                $scope.buildingReg = $scope.person.passport.registrationAddress.building;
                $scope.apartmentReg = $scope.person.passport.registrationAddress.apartment;
                $scope.indexReg = $scope.person.passport.registrationAddress.index;
            }, function(response){
                alert(JSON.stringify(response));
            });
            $scope.isUpdate = true;
        } else {
            $scope.isUpdate = false;
        }
    });

    function buildPerson() {
        var livingAddr = {};
        if ($scope.needLivingAddress) {
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
        if ($scope.needRegistrationAddress) {
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
            if ($scope.needRegistrationAddress) {
                registrationAddr.id = $scope.person.passport.registrationAddress.id;
            }
            if ($scope.needLivingAddress) {
                livingAddr.id = $scope.person.livingAddress.id;
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
    
    $scope.changeNeedRegistrationAddressFlag = function () {
        loadAddresses();
        $scope.needRegistrationAddress = !$scope.needRegistrationAddress;
    };
    
    $scope.changeNeedLivingAddressFlag = function () {
        loadAddresses();
        $scope.needLivingAddress = !$scope.needLivingAddress;
    };

    $scope.add = function () {
        var person = buildPerson();
        $http({
            url: '/persons/editor/',
            method: 'POST',
            params: {
                person: person
            }
        }).then(function (response) {
            alert(response.data);
        }, function (response) {
            alert(JSON.stringify(response));
        });
    };

    $scope.update = function () {
        var personToUpdate = buildPerson();
        $http({
            url: '/persons/editor/',
            method: 'PUT',
            params: {
                id: $scope.person.id,
                person: personToUpdate
            }
        }).then(function (response) {
            alert(response.data);
        }, function (response) {
            alert(JSON.stringify(response));
        });
    };
});