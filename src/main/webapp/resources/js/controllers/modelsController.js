/**
 * Created by user on 24.05.2016.
 */
var models = angular.module("modelsTable", []);
models.controller('modelsController', function($scope, $http){
    refreshTable();
    function refreshTable() {
        $http({
            url:'/models/table/',
            method:'GET'
        }).then(function(response){
            $scope.models = response.data;
        }, function(response){
            alert(JSON.stringify(response));
        });
    }

    $scope.outputPhase = function (threePhase) {
        if (threePhase) {
            return "трёхфазный";
        } else {
            return "однофазный"
        }
    };

    $scope.delete = function(){
        var idsToDelete = [];
        var j = 0;
        for (var i = 0; i < $scope.models.length; i++) {
            var cb = document.getElementById($scope.models[i].id);
            if (cb.checked) {
                idsToDelete[j] = $scope.models[i].id;
                j++;
            }
        }
        if (j != 0) {
            $http({
                url:'/models/table/',
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