/**
 * Created by user on 31.05.2016.
 */
var personStatementsEditor = angular.module("personStatementsEditor", []);
personStatementsEditor.controller('personStatementsEditorController', function($scope, $http, $routeParams) {

    angular.element(document).ready(function () {
        $scope.newPoint = false;
        $scope.ownerType = 'person';
        if ($routeParams['id'] != null && $routeParams['id'].trim() != '') {
            $http({
                url:'/person_statements/editor/',
                method:'GET',
                params: { id: $routeParams['id'] }
            }).then(function(response){
                var paramsMap = response.data;
                $scope.statement = paramsMap['statement'];
                var arr = $scope.statement.date.split('-');
                $scope.statement.date = new Date(arr[0], --arr[1], arr[2]);
                $scope.points = paramsMap['points'];
                for (var i = 0; i < $scope.points.length; i++) {
                    if ($scope.points[i].id == $scope.statement.meteringPoint.id) {
                        $scope.statement.meteringPoint = $scope.points[i];
                        break;
                    }
                }
            }, function(response){
                alert(JSON.stringify(response));
            });
            $scope.isUpdate = true;
        } else {
            $http({
                url:'/person_statements/editor/',
                method:'GET'
            }).then(function(response){
                var paramsMap = response.data;
                $scope.points = paramsMap['points'];
            }, function(response){
                alert(JSON.stringify(response));
            });
            $scope.isUpdate = false;
        }
        $scope.statement = {};
        $scope.pointsFilters = [];
        $scope.pointsFiltersParams = [];
        $scope.pointsFiltersModel = ['Дата установки', 'Адрес', 'Собственник', 'Счётчик'];
        refreshPointsFilters();
        $scope.personsFilters = [];
        $scope.personsFiltersParams = [];
        $scope.personsFiltersModel = ['Имя', 'Номер паспорта', 'Персональный счёт', 'Телефон', 'E-mail', 'Адрес'];
        refreshPersonsFilters();
    });

    $scope.addPointsFilter = function () {
        var index = $scope.pointsFilters.length;
        var maxIndex = $scope.pointsFiltersModel.length - 1;
        if (index <= maxIndex) {
            $scope.pointsFilters[index] = new Filter($scope.pointsFiltersParams[index][0]);
            refreshPointsFilters();
        }
    };

    $scope.pointsFiltersChange = function (index) {
        $scope.pointsFilters[index].values = {};
        refreshPointsFilters();
    };

    $scope.removePointsFilter = function (index) {
        $scope.pointsFilters.splice(index, 1);
        refreshPointsFilters();
    };

    $scope.filterPoints = function () {
        var filtersMap = { filters: {} };
        for (var i = 0; i < $scope.pointsFilters.length; i++) {
            filtersMap.filters[$scope.pointsFilters[i].parameter] = $scope.pointsFilters[i].values;
        }
        $http({
            url: '/points/filter/',
            method: 'POST',
            params: {
                filters: filtersMap,
                ownerType: 'person'
            }
        }).then(function (response) {
            $scope.points = response.data;
        }, function (response) {
            alert(JSON.stringify(response));
        });
    };

    function refreshPointsFilters() {
        var maxFilters = $scope.pointsFiltersModel.length;
        for (var i = 0; i < maxFilters; i++) {
            $scope.pointsFiltersParams[i] = [];
            for (var j = 0; j < maxFilters; j++) {
                $scope.pointsFiltersParams[i][j] = $scope.pointsFiltersModel[j];
            }
        }
        for (var i = 0; i < maxFilters; i++) {
            for (var j = 0; j < $scope.pointsFilters.length; j++) {
                if ($scope.pointsFiltersModel[i] == $scope.pointsFilters[j].parameter) {
                    var parameter = $scope.pointsFilters[j].parameter;
                    for (var k = 0; k < maxFilters; k++) {
                        if (k != j) {
                            var index = $scope.pointsFiltersParams[k].indexOf(parameter);
                            if (index != -1) {
                                $scope.pointsFiltersParams[k].splice(index, 1);
                            }
                        }
                    }
                }
            }
        }
    }

    $scope.pickPoint = function (point) {
        $scope.statement.meteringPoint = point;
    };

    $scope.pickPointFromDB = function () {
        $scope.newPoint = false;
        $scope.point = null;
    };

    $scope.regNewPoint = function () {
        $scope.newPoint = true;
        $scope.noMeter = true;
        $scope.noInstallationDate = true;
        $http({
            url:'/points/editor/',
            method:'GET'
        }).then(function(response){
            var paramsMap = response.data;
            $scope.addresses = paramsMap['addresses'];
            $scope.meters = paramsMap['meters'];
            $scope.enterpriseEntries = paramsMap['enterpriseEntries'];
            $scope.persons = paramsMap['persons'];

            $scope.metersSelect = { opt: $scope.meters[0].id };
            $scope.enterpriseEntriesSelect = { opt: $scope.enterpriseEntries[0].id };
            $scope.personsSelect = { opt: $scope.persons[0].id };
            $scope.addressesSelect = { opt: $scope.addresses[0].id };
        }, function(response){
            alert(JSON.stringify(response));
        });
        $scope.newAddress = false;
        $scope.editAddress = false;
        $scope.point = {};
        $scope.point.installationDate = new Date();

        $scope.statement.meteringPoint = {};
    };

    $scope.pickAddressFromDB = function () {
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
        if ($scope.newPoint) {
            if ($scope.point.owner == null) {
                alert('Необходимо выбрать собственника!');
                stopImmediatePropagation();
            }
            if ($scope.noInstallationDate) {
                $scope.point.installationDate = null;
            }
            if ($scope.newAddress) {
                $scope.point.address.id = null;
            } else if (!$scope.editAddress) {
                $scope.point.address = findObjectById($scope.addresses, $scope.addressesSelect.opt);
            }
            $scope.point.enterpriseEntry = findObjectById($scope.enterpriseEntries, $scope.enterpriseEntriesSelect.opt);

            if (!$scope.noMeter) {
                $scope.point.meter = findObjectById($scope.meters, $scope.metersSelect.opt);
            } else {
                $scope.point.meter = null;
            }
            $scope.statement.meteringPoint = $scope.point;
        }
    }

    $scope.pickPerson = function (person) {
        $scope.point.owner = person;
    };

    $scope.addPersonsFilter = function () {
        var index = $scope.personsFilters.length;
        var maxIndex = $scope.personsFiltersModel.length - 1;
        if (index <= maxIndex) {
            $scope.personsFilters[index] = new Filter($scope.personsFiltersParams[index][0]);
            refreshPersonsFilters();
        }
    };

    $scope.personsFiltersChange = function (index) {
        $scope.personsFilters[index].values = {};
        refreshPersonsFilters();
    };

    $scope.removePersonsFilter = function (index) {
        $scope.personsFilters.splice(index, 1);
        refreshPersonsFilters();
    };

    $scope.filterPersons = function () {
        var filtersMap = { filters: {} };
        for (var i = 0; i < $scope.personsFilters.length; i++) {
            filtersMap.filters[$scope.personsFilters[i].parameter] = $scope.personsFilters[i].values;
        }
        $http({
            url: '/persons/filter/',
            method: 'POST',
            params: {
                filters: filtersMap
            }
        }).then(function (response) {
            $scope.persons = response.data;
        }, function (response) {
            alert(JSON.stringify(response));
        });
    };

    function refreshPersonsFilters() {
        var maxFilters = $scope.personsFiltersModel.length;
        for (var i = 0; i < maxFilters; i++) {
            $scope.personsFiltersParams[i] = [];
            for (var j = 0; j < maxFilters; j++) {
                $scope.personsFiltersParams[i][j] = $scope.personsFiltersModel[j];
            }
        }
        for (var i = 0; i < maxFilters; i++) {
            for (var j = 0; j < $scope.personsFilters.length; j++) {
                if ($scope.personsFiltersModel[i] == $scope.personsFilters[j].parameter) {
                    var parameter = $scope.personsFilters[j].parameter;
                    for (var k = 0; k < maxFilters; k++) {
                        if (k != j) {
                            var index = $scope.personsFiltersParams[k].indexOf(parameter);
                            if (index != -1) {
                                $scope.personsFiltersParams[k].splice(index, 1);
                            }
                        }
                    }
                }
            }
        }
    }

    $scope.add = function () {
        prepareToSend();
        $http({
            url: '/person_statements/editor/',
            method: 'POST',
            params: {
                statement: $scope.statement
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
            url: '/person_statements/editor/',
            method: 'PUT',
            params: {
                statement: $scope.statement
            }
        }).then(function (response) {
            alert(response.data);
        }, function (response) {
            alert(JSON.stringify(response));
        });
    };
});

function Filter(parameter) {
    this.parameter = parameter;
    this.values = {};
}

function findObjectById(arr, id) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i].id == id) {
            return arr[i];
        }
    }
    alert('No such id!');
    return null;
}