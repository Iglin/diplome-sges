/**
 * Created by user on 01.06.2016.
 */
/**
 * Created by user on 28.05.2016.
 */
var agreementsEditor = angular.module("agreementsEditor", []);
agreementsEditor.controller('agreementsEditorController', function($scope, $http, $routeParams) {
    $scope.services = [];
    $scope.extraServices = [];
    angular.element(document).ready(function () {
        $scope.ownerType = 'entity';
        if ($routeParams['id'] != null && $routeParams['id'].trim() != '') {
            $http({
                url:'/agreements/editor/',
                method:'GET',
                params: { id: $routeParams['id'] }
            }).then(function(response){
                var paramsMap = response.data;
                $scope.agreement = paramsMap['agreement'];
                $scope.servicesModel = paramsMap['services'];
                if ($scope.agreement.meteringPoint.owner.name == null) {
                    $scope.ownerType = 'person';
                }
                var arr = $scope.agreement.date.split('-');
                $scope.agreement.date = new Date(arr[0], --arr[1], arr[2]);
                $scope.points = paramsMap['points'];
                for (var i = 0; i < $scope.points.length; i++) {
                    if ($scope.points[i].id == $scope.agreement.meteringPoint.id) {
                        $scope.agreement.meteringPoint = $scope.points[i];
                        break;
                    }
                }
                $scope.services = [];
                for (var i = 0; i < $scope.agreement.services.length; i++) {
                    $scope.extraServices[i] = $scope.servicesModel;
                    var usedService = {};
                    for (var j = 0; j < $scope.extraServices[i].length; j++) {
                        if ($scope.extraServices[i][j].id == $scope.agreement.services[i].extraService.id) {
                            usedService = $scope.extraServices[i][j];
                            break;
                        }
                    }
                    $scope.services[i] = {
                        service: usedService,
                        count: $scope.agreement.services[i].count,
                        coefficient: $scope.agreement.services[i].coefficient
                    };
                 /*   $scope.services[i] = new Service($scope.agreement.services[i].extraService,
                        $scope.agreement.services[i].count, 
                        $scope.agreement.services[i].coefficient);*/
                }
                refreshServices();
            }, function(response){
                alert(JSON.stringify(response));
            });
            $scope.isUpdate = true;
        } else {
            $http({
                url:'/agreements/editor/',
                method:'GET'
            }).then(function(response){
                var paramsMap = response.data;
                $scope.points = paramsMap['points'];
                $scope.servicesModel = paramsMap['services'];
                refreshServices();
            }, function(response){
                alert(JSON.stringify(response));
            });
            $scope.isUpdate = false;
        }
        $scope.agreement = {};
        $scope.pointsFilters = [];
        $scope.pointsFiltersParams = [];
        $scope.pointsFiltersModel = ['Дата установки', 'Адрес', 'Собственник', 'Счётчик'];
        refreshPointsFilters();

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
                ownerType: $scope.ownerType
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
        $scope.agreement.meteringPoint = point;
    };

    $scope.addService = function () {
        var index = $scope.services.length;
        var maxIndex = $scope.servicesModel.length - 1;
        if (index <= maxIndex) {
            $scope.services[index] = new Service($scope.extraServices[index][0], 1, 1);
            refreshServices();
        }

    };

    $scope.servicesChange = function (index) {
        $scope.services[index].count = 1;
        $scope.services[index].coefficient = 1;
        refreshServices();
    };

    $scope.removeService = function (index) {
        $scope.services.splice(index, 1);
        refreshServices();
    };

    function refreshServices() {
        var maxFilters = $scope.servicesModel.length;
        for (var i = 0; i < maxFilters; i++) {
            $scope.extraServices[i] = [];
            for (var j = 0; j < maxFilters; j++) {
                $scope.extraServices[i][j] = $scope.servicesModel[j];
            }
        }

        for (var i = 0; i < maxFilters; i++) {
            for (var j = 0; j < $scope.services.length; j++) {
                if ($scope.servicesModel[i] == $scope.services[j].service) {
                    var service = $scope.services[j].service;
                    for (var k = 0; k < maxFilters; k++) {
                        if (k != j) {
                            var index = $scope.extraServices[k].indexOf(service);
                            if (index != -1) {
                                $scope.extraServices[k].splice(index, 1);
                            }
                        }
                    }
                }
            }
        }
    }

    function prepareToSend() {
        $scope.agreement.services = [];
        for (var i = 0; i < $scope.services.length; i++) {
            $scope.agreement.services[i] = {};
            $scope.agreement.services[i].extraService = $scope.services[i].service;

            $scope.agreement.services[i].count = $scope.services[i].count;
            $scope.agreement.services[i].coefficient = $scope.services[i].coefficient;
        }

    }

    $scope.add = function () {
        prepareToSend();
        alert(JSON.stringify($scope.agreement));
        if ($scope.agreement.meteringPoint == null) {
            alert('Необходимо выбрать точку учёта!');
            stopImmediatePropagation();
        } else {
            if ($scope.agreement.services == null || $scope.agreement.services.length == 0) {
                alert('Необходимо выбрать хотя бы одну услугу!');
                stopImmediatePropagation();
            } else {
                $http({
                    url: '/agreements/editor/',
                    method: 'POST',
                    params: {
                        agreement: $scope.agreement
                    }
                }).then(function (response) {
                    alert(response.data);
                }, function (response) {
                    alert(JSON.stringify(response));
                });
            }
        }
    };

    $scope.update = function () {
        prepareToSend();
        alert(JSON.stringify($scope.agreement.services));
        if ($scope.agreement.meteringPoint == null) {
            alert('Необходимо выбрать точку учёта!');
            stopImmediatePropagation();
        } else {
            if ($scope.agreement.services == null || $scope.agreement.services.length == 0) {
                alert('Необходимо выбрать хотя бы одну услугу!');
                stopImmediatePropagation();
            } else {
                $http({
                    url: '/agreements/editor/',
                    method: 'PUT',
                    params: {
                        agreement: $scope.agreement
                    }
                }).then(function (response) {
                    alert(response.data);
                }, function (response) {
                    alert(JSON.stringify(response));
                });
            }
        }
    };
});

function Filter(parameter) {
    this.parameter = parameter;
    this.values = {};
}

function Service(service, count, coefficient) {
    this.service = service;
    this.count = count;
    this.coefficient = coefficient;
}