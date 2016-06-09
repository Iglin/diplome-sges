/**
 * Created by user on 02.06.2016.
 */
var accounts = angular.module("accountsTable", []);
accounts.controller('accountsController', function($scope, $http){
    refreshTable();

    function refreshTable() {
        $http({
            url:'/accounts/table/',
            method:'GET'
        }).then(function(response){
            $scope.accounts = response.data;
        }, function(response){
            showAlert(response);
        });
    }

    $scope.delete = function(){
        var idsToDelete = [];
        var j = 0;
        for (var i = 0; i < $scope.accounts.length; i++) {
            var cb = document.getElementById($scope.accounts[i].id);
            if (cb.checked) {
                idsToDelete[j] = $scope.accounts[i].id;
                j++;
            }
        }
        if (j != 0) {
            $http({
                url:'/accounts/table/',
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