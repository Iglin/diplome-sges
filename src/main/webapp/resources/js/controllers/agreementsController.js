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
            alert(JSON.stringify(response));
        });
    }

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
                alert(response.data);
            }, function(response){
                alert(JSON.stringify(response));
            });
        }
    };
});