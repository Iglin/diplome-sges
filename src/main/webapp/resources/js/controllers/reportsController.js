/**
 * Created by user on 06.06.2016.
 */
var reports = angular.module("reports", []);
reports.controller('reportsController', function($scope, $http){
    function validatePeriod() {
        if ($scope.dateFrom == null || $scope.dateTo == null) {
            showSimpleAlert(false, 'Необходимо указать период.');
            return false;
        }
        if ($scope.dateFrom > $scope.dateTo) {
            showSimpleAlert(false, 'Неверно задан период.');
            return false;
        }
        return true;
    }

    $scope.agreements = function(isEntity) {
        if (validatePeriod()) {
            $http({
                url: '/reports/make/',
                method: 'GET',
                params: { dateFrom: $scope.dateFrom, dateTo: $scope.dateTo, isEntity: isEntity }
            }).then(function (response) {
            }, function (response) {
                showAlert(response);
            });
        }
    };

    $scope.acts = function(isEntity) {
        if (validatePeriod()) {
            $http({
                url: '/reports/make/',
                method: 'PUT',
                params: { dateFrom: $scope.dateFrom, dateTo: $scope.dateTo, isEntity: isEntity }
            }).then(function (response) {
            }, function (response) {
                showAlert(response);
            });
        }
    };

    $scope.commercial = function() {
        if (validatePeriod()) {
            $http({
                url: '/reports/make/',
                method: 'POST',
                params: { dateFrom: $scope.dateFrom, dateTo: $scope.dateTo }
            }).then(function (response) {
            }, function (response) {
                showAlert(response);
            });
        }
    }
});
