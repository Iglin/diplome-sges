/**
 * Created by user on 13.05.2016.
 */
var addresses = angular.module("addressesTable", []);
addresses.controller('addressesController', function($scope, $http){
    function refreshTable() {
        $http({
            url:'/addresses/table/',
            method:'GET'
        }).then(function(response){
            $scope.addresses = response.data;
        }, function(response){
            alert(JSON.stringify(response));
        });
    }
    angular.element(document).ready(function () {
        refreshTable();
    });

    $scope.delete = function(){
        var idsToDelete = [];
        var j = 0;
        for (var i = 0; i < $scope.addresses.length; i++) {
            var cb = document.getElementById($scope.addresses[i].id);
            if (cb.checked) {
                idsToDelete[j] = $scope.addresses[i].id;
                j++;
            }
        }
        if (j != 0) {
            $http({
                url:'/addresses/table/',
                method:'DELETE',
                params:{ids: idsToDelete}
            }).then(function(response){
                refreshTable();
                showAlert(response);
            }, function(response){
                alert(JSON.stringify(response));
            });
        }
    };
});