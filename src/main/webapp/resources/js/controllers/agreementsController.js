/**
 * Created by user on 01.06.2016.
 */
var agreements = angular.module("agreementsTable", []);
agreements.controller('agreementsController', function($scope, $http){
    refreshTable();

    function refreshTable() {
        $http({
            url:'/agreements/table/',
            method:'GET'
        }).then(function(response){
            $scope.agreements = response.data;
        }, function(response){
            showAlert(response);
        });
    }
    
    $scope.generateReceipt = function (x) {
        $http({
            url:'/agreements/receipt/',
            method:'GET', 
            params: {'agreementNumber': x}
        }).then(function(response){
            
        }, function(response){
            showAlert(response);
        });
    };

    $scope.generateAct = function (x) {
        $http({
            url:'/agreements/act/',
            method:'GET',
            params: {'agreementNumber': x}
        }).then(function(response){

        }, function(response){
            showAlert(response);
        });
    };

    $scope.delete = function(){
        var idsToDelete = [];
        var j = 0;
        for (var i = 0; i < $scope.agreements.length; i++) {
            var cb = document.getElementById($scope.agreements[i].number);
            if (cb.checked) {
                idsToDelete[j] = $scope.agreements[i].number;
                j++;
            }
        }
        if (j != 0) {
            $http({
                url:'/agreements/table/',
                method:'DELETE',
                params:{ids: idsToDelete}
            }).then(function(response){
                refreshTable();
                showAlert(response);
            }, function(response){
                showAlert(response);
            });
        }
    };
});