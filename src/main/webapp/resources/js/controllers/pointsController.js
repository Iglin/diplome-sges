/**
 * Created by user on 25.05.2016.
 */
var points = angular.module("pointsTable", []);
points.controller('pointsController', function($scope, $http){
    refreshTable();

    function refreshTable() {
        $http({
            url:'/points/table/',
            method:'GET'
        }).then(function(response){
            $scope.points = response.data;
        }, function(response){
            alert(JSON.stringify(response));
        });
    }

    $scope.delete = function(){
        var idsToDelete = [];
        var j = 0;
        for (var i = 0; i < $scope.points.length; i++) {
            var cb = document.getElementById($scope.points[i].id);
            if (cb.checked) {
                idsToDelete[j] = $scope.points[i].id;
                j++;
            }
        }
        if (j != 0) {
            $http({
                url:'/points/table/',
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