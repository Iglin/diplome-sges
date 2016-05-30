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
                $scope.points = paramsMap['points'];
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
            $scope.pointsFilters[index] = new Filter($scope.pointsFiltersParams[index][0], {});
            refreshPointsFilters();
        }
    };

    $scope.pointsFiltersChange = function () {
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

        alert(JSON.stringify(filtersMap));
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
});

entityStatementsEditor.directive('pointsFiltersDirective', function() {
    return {
        template:
        ""
    }
});

function Filter(parameter, values) {
    this.parameter = parameter;
    this.values = values;
}

