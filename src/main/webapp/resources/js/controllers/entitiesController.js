/**
 * Created by user on 23.05.2016.
 */
var entities = angular.module("entitiesTable", []);
entities.controller('entitiesController', function($scope, $http){
    refreshTable();
    
    function refreshTable() {
        $http({
            url:'/entities/table/',
            method:'GET'
        }).then(function(response){
            $scope.entities = response.data;
        }, function(response){
            showAlert(response);
        });
    }

    $scope.delete = function(){
        var idsToDelete = [];
        var j = 0;
        for (var i = 0; i < $scope.entities.length; i++) {
            var cb = document.getElementById($scope.entities[i].id);
            if (cb.checked) {
                idsToDelete[j] = $scope.entities[i].id;
                j++;
            }
        }
        if (j != 0) {
            $http({
                url:'/entities/table/',
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