/**
 * Created by user on 28.05.2016.
 */
var entityStatementsEditor = angular.module("entityStatementsEditor", []);
entityStatementsEditor.controller('entityStatementsEditorController', function($scope, $http, $routeParams) {

    angular.element(document).ready(function () {
        $scope.newPoint = false;
        if ($routeParams['id'] != null && $routeParams['id'].trim() != '') {
            $http({
                url:'/entity_statements/editor/',
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
                url:'/entity_statements/editor/',
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
        $scope.entitiesFilters = [];
        $scope.entitiesFiltersParams = [];
        $scope.entitiesFiltersModel = ['Наименование', 'Дата регистрации', 'Персональный счёт', 'Телефон', 'E-mail',
            'Адрес', 'ОГРН', 'ИНН', 'КПП'];
        refreshEntitiesFilters();
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
                filters: filtersMap
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
            $scope.entities = paramsMap['entities'];

            $scope.metersSelect = { opt: $scope.meters[0].id };
            $scope.enterpriseEntriesSelect = { opt: $scope.enterpriseEntries[0].id };
            $scope.entitiesSelect = { opt: $scope.entities[0].id };
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

    $scope.pickEntity = function (entity) {
        $scope.point.owner = entity;
    };

    $scope.addEntitiesFilter = function () {
        var index = $scope.entitiesFilters.length;
        var maxIndex = $scope.entitiesFiltersModel.length - 1;
        if (index <= maxIndex) {
            $scope.entitiesFilters[index] = new Filter($scope.entitiesFiltersParams[index][0]);
            refreshEntitiesFilters();
        }
    };

    $scope.entitiesFiltersChange = function (index) {
        $scope.entitiesFilters[index].values = {};
        refreshEntitiesFilters();
    };

    $scope.removeEntitiesFilter = function (index) {
        $scope.entitiesFilters.splice(index, 1);
        refreshEntitiesFilters();
    };

    $scope.filterEntities = function () {
        var filtersMap = { filters: {} };
        for (var i = 0; i < $scope.entitiesFilters.length; i++) {
            filtersMap.filters[$scope.entitiesFilters[i].parameter] = $scope.entitiesFilters[i].values;
        }
        $http({
            url: '/entities/filter/',
            method: 'POST',
            params: {
                filters: filtersMap
            }
        }).then(function (response) {
            $scope.entities = response.data;
        }, function (response) {
            alert(JSON.stringify(response));
        });
    };

    function refreshEntitiesFilters() {
        var maxFilters = $scope.entitiesFiltersModel.length;
        for (var i = 0; i < maxFilters; i++) {
            $scope.entitiesFiltersParams[i] = [];
            for (var j = 0; j < maxFilters; j++) {
                $scope.entitiesFiltersParams[i][j] = $scope.entitiesFiltersModel[j];
            }
        }
        for (var i = 0; i < maxFilters; i++) {
            for (var j = 0; j < $scope.entitiesFilters.length; j++) {
                if ($scope.entitiesFiltersModel[i] == $scope.entitiesFilters[j].parameter) {
                    var parameter = $scope.entitiesFilters[j].parameter;
                    for (var k = 0; k < maxFilters; k++) {
                        if (k != j) {
                            var index = $scope.entitiesFiltersParams[k].indexOf(parameter);
                            if (index != -1) {
                                $scope.entitiesFiltersParams[k].splice(index, 1);
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
            url: '/entity_statements/editor/',
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
            url: '/entity_statements/editor/',
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
    switch (parameter) {
        case 'Счётчик':
            this.values = {};
            this.values['manufacturer'] = '';
            this.values['model'] = '';
            this.values['serialNumber'] = '';
            break;
        case 'Адрес':
            this.values = {};
            this.values['region'] = '';
            this.values['city'] = '';
            this.values['street'] = '';
            this.values['building'] = '';
            this.values['apartment'] = '';
            this.values['index'] = null;
            break;
        case 'Дата подключения':
            this.values = {};
            this.values['dateTo'] = '';
            this.values['dateFrom'] = '';
            break;
        case 'Собственник':
            this.values = {};
            this.values['personalAccount'] = '';
            break;
    }
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