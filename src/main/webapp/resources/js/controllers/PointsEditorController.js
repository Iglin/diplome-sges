/**
 * Created by user on 25.05.2016.
 */
var pointsEditor = angular.module("pointsEditor", []);
pointsEditor.controller('pointsEditorController', function($scope, $http, $routeParams) {
   // loadAddresses();
   // loadEnterprise();
   // loadEntities();
   // loadPersons();
   // loadMeters();
    if ($routeParams['id'] != null && $routeParams['id'].trim() != '') {
        $http({
            url:'/points/editor/',
            method:'GET',
            params: { id: $routeParams['id'] }
        }).then(function(response){
            var paramsMap = response.data;
            $scope.point = paramsMap['point'];
            $scope.addresses = paramsMap['addresses'];
            $scope.meters = paramsMap['meters'];
            $scope.enterpriseEntries = paramsMap['enterpriseEntries'];
            $scope.entities = paramsMap['entities'];
            $scope.persons = paramsMap['persons'];
            
            $scope.addressIdFromReq = $scope.point.address.id;
            
            $scope.ownerIdFromReq = $scope.point.owner.id;
            $scope.meterIdFromReq = $scope.point.meter.id;
            $scope.enterpriseEntryIdFromReq = $scope.point.enterpriseEntry.id;
            if ($scope.point.owner.hasOwnProperty('name')) {
                $scope.ownerType = 'Юр. лицо';
            } else {
                $scope.ownerType = 'Физ. лицо';
            }
        }, function(response){
            alert(JSON.stringify(response));
        });
        $scope.isUpdate = true;
        $scope.newAddress = false;
        $scope.editAddress = true;

    } else {
        $http({
            url:'/points/editor/',
            method:'GET'
        }).then(function(response){
            var paramsMap = response.data;
            $scope.addresses = paramsMap['addresses'];
            $scope.meters = paramsMap['meters'];
            $scope.enterpriseEntries = paramsMap['enterpriseEntries'];
            $scope.entities = paramsMap['entities'];
            $scope.persons = paramsMap['persons'];
        }, function(response){
            alert(JSON.stringify(response));
        });
        $scope.isUpdate = false;
        $scope.newAddress = false;
        $scope.editAddress = false;
        $scope.ownerType = 'Юр. лицо';
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

    function loadEntities() {
        if ($scope.entities == null) {
            $http({
                url:'/entities/table/',
                method:'GET'
            }).then(function(response){
                $scope.entities = response.data;
            }, function(response){
                alert(JSON.stringify(response));
            });
        }
    }

    function loadPersons() {
        if ($scope.addresses == null) {
            $http({
                url:'/persons/table/',
                method:'GET'
            }).then(function(response){
                $scope.persons = response.data;
            }, function(response){
                alert(JSON.stringify(response));
            });
        }
    }

    function loadMeters() {
        if ($scope.addresses == null) {
            $http({
                url:'/meters/table/',
                method:'GET'
            }).then(function(response){
                $scope.meters = response.data;
            }, function(response){
                alert(JSON.stringify(response));
            });
        }
    }

    function loadEnterprise() {
        if ($scope.enterpriseEntries == null) {
            $http({
                url:'/enterprise/table/',
                method:'GET'
            }).then(function(response){
                $scope.enterpriseEntries = response.data;
            }, function(response){
                alert(JSON.stringify(response));
            });
        }
    }

    $scope.pickAddressFromDB = function () {
      //  loadAddresses();
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
        if ($scope.newAddress) {
            $scope.point.address.id = null;
        } else if (!$scope.editAddress) {
            $scope.point.address = $scope.addresses[$scope.addressId];
        }
        $scope.point.enterpriseEntry = $scope.enterpriseEntries[$scope.enterpriseEntryIndex];
        $scope.point.meter = $scope.meters[$scope.meterIndex];
        if ($scope.ownerType == 'Юр. лицо') {
            $scope.point.owner = $scope.entities[$scope.entityIndex];
        } else {
            $scope.point.owner = $scope.persons[$scope.personIndex];
        }
    }

    $scope.add = function () {
        prepareToSend();
        $http({
            url: '/points/editor/',
            method: 'POST',
            params: {
                point: $scope.point
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
            url: '/points/editor/',
            method: 'PUT',
            params: {
                point: $scope.point
            }
        }).then(function (response) {
            alert(response.data);
        }, function (response) {
            alert(JSON.stringify(response));
        });
    };
});