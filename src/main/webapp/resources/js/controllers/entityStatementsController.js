/**
 * Created by user on 28.05.2016.
 */
var entityStatements = angular.module("entityStatementsTable", []);
entityStatements.controller('entityStatementsController', function($scope, $http){
    refreshTable();

    function refreshTable() {
        $http({
            url:'/entity_statements/table/',
            method:'GET'
        }).then(function(response){
            $scope.entityStatements = response.data;
        }, function(response){
            alert(JSON.stringify(response));
        });
    }

    $scope.delete = function(){
        var idsToDelete = [];
        var j = 0;
        for (var i = 0; i < $scope.entityStatements.length; i++) {
            var cb = document.getElementById($scope.entityStatements[i].id);
            if (cb.checked) {
                idsToDelete[j] = $scope.entityStatements[i].id;
                j++;
            }
        }
        if (j != 0) {
            $http({
                url:'/entity_statements/table/',
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