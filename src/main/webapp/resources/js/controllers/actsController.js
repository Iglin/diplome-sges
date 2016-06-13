/**
 * Created by user on 10.06.2016.
 */
var acts = angular.module("actsTable", []);
acts.controller('actsController', function($scope, $http){
    refreshTable();

    function refreshTable() {
        $http({
            url:'/acts/table/',
            method:'GET'
        }).then(function(response){
            $scope.acts = response.data;
        }, function(response){
            showAlert(response);
        });
    }

    $scope.delete = function(){
        var idsToDelete = [];
        var j = 0;
        for (var i = 0; i < $scope.acts.length; i++) {
            var cb = document.getElementById($scope.acts[i].number);
            if (cb.checked) {
                idsToDelete[j] = $scope.acts[i].number;
                j++;
            }
        }
        if (j != 0) {
            $http({
                url:'/acts/table/',
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