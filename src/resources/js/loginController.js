angular.module("login-App", []).controller(
		"loginController",
		function($scope, $http,$location) {
			$scope.userInfo = {};
			$scope.validation = function() {
				$scope.message = "";
				if ($scope.userInfo.username == undefined
						|| $scope.userInfo.username == ""
						|| $scope.userInfo.username == null 
						|| $scope.userInfo.password == undefined
						|| $scope.userInfo.password == ""
						|| $scope.userInfo.password == null) {
					$scope.message = "Username or Password should not be empty!!";
					return false;
				}
				return true;
			}
			$scope.login = function() {
				var res = $scope.validation();
				if(!res){
					return;
				}
				var formData = new FormData();
				formData.append("userName", $scope.userInfo.username);
				formData.append('password', $scope.userInfo.password);

				$http({
					url : "/admin/login",
					method : 'POST',
					data : formData,
					headers : {
						'Content-Type' : undefined
					},
					transformRequest : angular.identity
				}).success(function(response) {
					
					if(response != null && response.status == -1){
						$scope.message = "Incorrect User Name Or Password !!";
						localStorage.removeItem("sessionId");
						localStorage.removeItem("userName");
					}else{
						localStorage.setItem("sessionId", response.sessionId);
						localStorage.setItem("userName", response.userName);
						window.location.href = "/dashboard.html";
					}
				}).error(function(e) {
					alert("Oops.. something went wrong!");
					console.log(e);
				});
				;
			}
		});