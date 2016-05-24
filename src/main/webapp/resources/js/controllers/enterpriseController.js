/**
 * Created by user on 25.05.2016.
 */
var enterprise = angular.module("enterpriseTable", []);
enterprise.controller('enterpriseController', function($scope, $http){
    refreshTable();

    function refreshTable() {
        $http({
            url:'/enterprise/table/',
            method:'GET'
        }).then(function(response){
            $scope.enterpriseEntries = response.data;
        }, function(response){
            alert(JSON.stringify(response));
        });
    }
    
    $scope.setActual = function (entryId) {
        $http({
            url:'/enterprise/table/',
            method:'PUT',
            params: { id: entryId }
        }).then(function(response){
            $scope.enterpriseEntries = response.data;
        }, function(response){
            alert(JSON.stringify(response));
        });
    };

    $scope.delete = function(){
        var idsToDelete = [];
        var j = 0;
        for (var i = 0; i < $scope.enterpriseEntries.length; i++) {
            var cb = document.getElementById($scope.enterpriseEntries[i].id);
            if (cb.checked) {
                idsToDelete[j] = $scope.enterpriseEntries[i].id;
                j++;
            }
        }
        if (j != 0) {
            $http({
                url:'/enterprise/table/',
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