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
        $scope.pointsFiltersParams = [{ usedBy: null, value: 'Дата установки' }, { usedBy: null, value: 'Адрес' },
            { usedBy: null, value: 'Собственник' }, { usedBy: null, value: 'Счётчик' }];
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
        if (index < $scope.pointsFiltersParams.length) {
            for (var i = 0; i < $scope.pointsFiltersParams.length; i++) {
                if ($scope.pointsFiltersParams[i].usedBy == null) {
                    $scope.pointsFilters[index] = new Filter($scope.pointsFiltersParams[i], '');
                    $scope.pointsFiltersParams[i].usedBy = index;
                    break;
                }
            }
        }
    };

    $scope.removePointsFilter = function (index) {
        $scope.pointsFilters[index].parameter.usedBy = null;
        if ($scope.pointsFilters.length - 1 > index) {
            for (var i = index + 1; i < pointsFilters.length; i++) {
                pointsFilters[i - 1] = pointsFilters[i];
            }
        }
        $scope.pointsFilters[$scope.pointsFilters.length - 1] = null;
    };

    $scope.filterPointsParams = function (x, index) {
        return x.usedBy == null || x.usedBy == index;
    };

    $scope.filterPoints = function () {

    }
});

entityStatementsEditor.directive('pointsFiltersDirective', function() {
    return {
        template:
        "<button class='btn btn-primary' ng-click='addPointsFilter();'>Добавить фильтр</button>" +
        "<div ng-repeat='singleFilter in pointsFilters track by $index'>" +
        "<select class='form-control' ng-model='pointsFilters[$index].parameter' required " +
        "ng-options='x for x.value in pointsFiltersParams | filter:{ usedBy : $index || null }'></select>" +
        "<input type='text' class='form-control' ng-model='pointsFilters[$index].value'>" +
        "<button class='btn btn-danger' ng-click='removePointsFilter($index);'>-</button><br>" +
        "</div>" +
        "<button class='btn btn-primary' ng-click='filterPoints();'>Применить</button>"
    }
});

function Filter(parameter, value) {
    this.parameter = parameter;
    this.value = value;
}