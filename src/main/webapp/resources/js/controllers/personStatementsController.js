/**
 * Created by user on 31.05.2016.
 */
var personStatements = angular.module("personStatementsTable", []);
personStatements.controller('personStatementsController', function($scope, $http){
    refreshTable();

    function refreshTable() {
        $http({
            url:'/person_statements/table/',
            method:'GET'
        }).then(function(response){
            $scope.personStatements = response.data;
        }, function(response){
            alert(JSON.stringify(response));
        });
    }

    $scope.delete = function(){
        var idsToDelete = [];
        var j = 0;
        for (var i = 0; i < $scope.personStatements.length; i++) {
            var cb = document.getElementById($scope.personStatements[i].id);
            if (cb.checked) {
                idsToDelete[j] = $scope.personStatements[i].id;
                j++;
            }
        }
        if (j != 0) {
            $http({
                url:'/person_statements/table/',
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