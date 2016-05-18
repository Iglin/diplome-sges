/**
 * Created by root on 18.05.16.
 */
var persons = angular.module("personsTable", []);
persons.controller('personsController', function($scope, $http){
    function refreshTable() {
        $http({
            url:'/persons/table/',
            method:'GET'
        }).then(function(response){
            $scope.persons = response.data;
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
        for (var i = 0; i < $scope.persons.length; i++) {
            var cb = document.getElementById($scope.persons[i].id);
            if (cb.checked) {
                idsToDelete[j] = $scope.persons[i].id;
                j++;
            }
        }
        if (j != 0) {
            $http({
                url:'/persons/table/',
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