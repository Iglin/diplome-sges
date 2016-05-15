var addressesModal = angular.module('cinemaEditor',[]);
addressesModal.controller('addressesModalController', function($scope, $http, $uibModal){
        var toDelete = new Array;
        Array.prototype.contains = function(el){
            return this.indexOf(el) > -1;
        };

        var updateTable = function(){
            $http({
                method:'GET',
                url:'/addresses/'
            }).then(function(response){
                $scope.addresses = response.data;
            }, function(response){
                alert(JSON.stringify(response.data));
            });
        };

        $scope.add = function(){
            var modalInstance = $uibModal.open({
                animation: 'true',
                templateUrl:'addAddressModal.html',
                controller: 'ModalAddAddressController',
                resolve: {
                    region:function(){
                        return $scope.region;
                    },
                    city:function(){
                        return $scope.city;
                    },
                    street:function(){
                        return $scope.street;
                    },
                    building:function(){
                        return $scope.building;
                    },
                    apartment:function(){
                        return $scope.apartment;
                    },
                    index:function(){
                        return $scope.index;
                    }
                },
                size:'md'
            });
            modalInstance.result.then(function(cinema){
                $http({
                    url:'/addresses/',
                    method:'POST',
                    params:{region: $scope.region, city: $scope.city, street: $scope.street, building: $scope.building,
                        apartment: $scope.apartment, index: $scope.index}
                }).then(function(response){
                    updateTable();
                }, function(resp){
                    alert(JSON.stringify(resp));
                });
            });
        };

        updateTable();
    })
    .controller('ModalAddAddressController', function($scope, $http, $uibModalInstance, region, city, street,
                                                     building, apartment, index){
        $scope.region = region;
        $scope.city = city;
        $scope.street = street;
        $scope.building = building;
        $scope.apartment = apartment;
        $scope.index = index;

        $scope.cancel = function(){
            $uibModalInstance.dismiss('cancel');
        };

        $scope.save = function(){
            var address = {region: $scope.region, city: $scope.city, street: $scope.street, building: $scope.building,
                apartment: $scope.apartment, index: $scope.index};
            $uibModalInstance.close(address);
        }
    });