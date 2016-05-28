/**
 * Created by user on 28.05.2016.
 */
var entityStatementsEditor = angular.module("entityStatementsEditor", []);
entityStatementsEditor.controller('entityStatementsEditorController', function($scope, $http, $routeParams) {

    angular.element(document).ready(function () {
        if ($routeParams['id'] != null && $routeParams['id'].trim() != '') {
            $http({
                url:'/points/editor/',
                method:'GET',
                params: { id: $routeParams['id'] }
            }).then(function(response){
                var paramsMap = response.data;
                $scope.statement = paramsMap['statement'];
                $scope.points = paramsMap['points'];

                if ($scope.point.owner.hasOwnProperty('name')) {
                    $scope.ownerType = 'Юр. лицо';
                    $scope.entitiesSelect = { opt: $scope.point.owner.id };
                    $scope.personsSelect = { opt: $scope.persons[0].id };
                } else {
                    $scope.ownerType = 'Физ. лицо';
                    $scope.entitiesSelect = { opt: $scope.entities[0].id };
                    $scope.personsSelect = { opt: $scope.point.owner.id };
                }
                $scope.metersSelect = { opt: $scope.point.meter.id };
                $scope.enterpriseEntriesSelect = { opt: $scope.point.enterpriseEntry.id };
                $scope.addressesSelect = { opt: $scope.point.address.id };
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

                $scope.metersSelect = { opt: $scope.meters[0].id };
                $scope.enterpriseEntriesSelect = { opt: $scope.enterpriseEntries[0].id };
                $scope.entitiesSelect = { opt: $scope.entities[0].id };
                $scope.personsSelect = { opt: $scope.persons[0].id };
                $scope.addressesSelect = { opt: $scope.addresses[0].id };
            }, function(response){
                alert(JSON.stringify(response));
            });
            $scope.isUpdate = false;
            $scope.newAddress = false;
            $scope.editAddress = false;
            $scope.ownerType = 'Юр. лицо';
        }
    });

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
            $scope.point.address = findObjectById($scope.addresses, $scope.addressesSelect.opt);
        }
        $scope.point.enterpriseEntry = findObjectById($scope.enterpriseEntries, $scope.enterpriseEntriesSelect.opt);

        $scope.point.meter = findObjectById($scope.meters, $scope.metersSelect.opt);
        if ($scope.ownerType == 'Юр. лицо') {
            $scope.point.owner = findObjectById($scope.entities, $scope.entitiesSelect.opt);
        } else {
            $scope.point.owner = findObjectById($scope.persons, $scope.personsSelect.opt);
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

function findObjectById(arr, id) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i].id == id) {
            return arr[i];
        }
    }
    alert('No such id!');
    return null;
}