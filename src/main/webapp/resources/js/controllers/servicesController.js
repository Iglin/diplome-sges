/**
 * Created by user on 01.06.2016.
 */
var services = angular.module("servicesTable", []);
services.controller('servicesController', function($scope, $http){
    refreshTable();
    function refreshTable() {
        $http({
            url:'/extra_services/table/',
            method:'GET'
        }).then(function(response){
            $scope.services = response.data;
        }, function(response){
            alert(JSON.stringify(response));
        });
    }

    $scope.delete = function(){
        var idsToDelete = [];
        var j = 0;
        for (var i = 0; i < $scope.services.length; i++) {
            var cb = document.getElementById($scope.services[i].id);
            if (cb.checked) {
                idsToDelete[j] = $scope.services[i].id;
                j++;
            }
        }
        if (j != 0) {
            $http({
                url:'/extra_services/table/',
                method:'DELETE',
                params:{ids: idsToDelete}
            }).then(function(response){
                refreshTable();
                alert(response.data);
            }, function(response){
                alert(JSON.stringify(response));
            });
        }
    };
});