/**
 * Created by user on 25.05.2016.
 */
var meters = angular.module("metersTable", []);
meters.controller('metersController', function($scope, $http){
    refreshTable();
    function refreshTable() {
        $http({
            url:'/meters/table/',
            method:'GET'
        }).then(function(response){
            $scope.meters = response.data;
        }, function(response){
            alert(JSON.stringify(response));
        });
    }

    $scope.delete = function(){
        var idsToDelete = [];
        var j = 0;
        for (var i = 0; i < $scope.meters.length; i++) {
            var cb = document.getElementById($scope.meters[i].id);
            if (cb.checked) {
                idsToDelete[j] = $scope.meters[i].id;
                j++;
            }
        }
        if (j != 0) {
            $http({
                url:'/meters/table/',
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