/**
 * Created by user on 28.05.2016.
 */
var entityStatementsEditor = angular.module("entityStatementsEditor", []);
entityStatementsEditor.controller('entityStatementsEditorController', function($scope, $http, $routeParams) {

    angular.element(document).ready(function () {
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
        $scope.pointsFilters = [];
        $scope.pointsFiltersParams = [];
        $scope.pointsFiltersModel = ['Дата установки', 'Адрес', 'Собственник', 'Счётчик'];
        refreshPointsFilters();
    });

    $scope.add = function () {
        alert(JSON.stringify($scope.statement));
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
    }
});

entityStatementsEditor.directive('pointsFiltersDirective', function() {
    return {
        template:
        ""
    }
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

