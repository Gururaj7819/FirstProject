var app = angular.module("mainModule", []);

app.controller("mainController", function($scope, $http, $location) {
	if (localStorage.getItem("sessionId") && localStorage.getItem("userName")) {
		$scope.systemDetails = {};
		$scope.userName = localStorage.getItem("userName");
		$scope.isAllocatedSelected = true;
		$scope.isDeallocatedSelected = true;
	}else{
		window.location.href = "index.html";
	}
	$scope.filterAllSystems="";
	$scope.resetSystemForm = function(){
		$scope.systemDetails = {};
		$scope.systemInvalid = "";
		$scope.allocationObj = {};
		
	}
	
	 $scope.logout = function(){
		 localStorage.removeItem("sessionId");
		 localStorage.removeItem("userName");
		 window.location.href="index.html";
     }
	 
	 $scope.systemToBeDeleted = function(system){
		 $scope.systemToBeDeleted = system;
	 }


	$scope.saveSystem = function() {
		var isValid = $scope.validateSystem();
		if(!isValid)
			return;
		var urlString = "/system/saveSystem?userName=" + localStorage.getItem("userName") +"&sessionId=" + localStorage.getItem("sessionId");
		$http({
			url : urlString,
			method : 'POST',
			data : JSON.stringify($scope.systemDetails),
			headers : {
				'Content-Type' : 'application/json'
			},
			transformRequest : angular.identity
		}).success(function(response) {

			if (response.status == -1) {
				alert("Session expired! Please login to continue");
				localStorage.removeItem("sessionId");
				localStorage.removeItem("userName");
				window.location.href="index.html";
			}else if(response.status == 101){
				alert("Duplicate systemId!");
			}
			else {
				console.log(response);
				alert("Saved successfully");
				$scope.getAllSystemDetails();
				$scope.resetSystemForm();
				$('#addSystemModal').modal('hide');
				
			}
		}).error(function(e) {
			alert("Oops.. something went wrong!");
			console.log(e);
		});
		;
	}
	
	$scope.deleteSystem = function() {
		var urlString = "/system/deleteSystem?userName=" + localStorage.getItem("userName") +"&sessionId=" + localStorage.getItem("sessionId");
		$http({
			url : urlString,
			method : 'POST',
			data : JSON.stringify($scope.systemToBeDeleted),
			headers : {
				'Content-Type' : 'application/json'
			},
			transformRequest : angular.identity
		}).success(function(response) {

			if (response.status == -1) {
				alert("Session expired! Please login to continue");
				localStorage.removeItem("sessionId");
				localStorage.removeItem("userName");
				window.location.href="index.html";
			}else if(response.status == 101){
				alert("Delete Unsuccessful");
			}
			else {
				alert("Deleted successfully");
				$scope.getAllSystemDetails();
				
			}
			$scope.systemToBeDeleted = undefined;
		}).error(function(e) {
			alert("Oops.. something went wrong!");
			console.log(e);
		});
		;
	}
	
	$scope.validateSystem = function(){
		$scope.systemInvalid = "";
		if($scope.systemDetails.systemId == null || $scope.systemDetails.systemId == undefined || $scope.systemDetails.systemId == ""){
			$scope.systemInvalid = "System id should not be empty!!";
			return false;
		}
		if($scope.systemDetails.model == null || $scope.systemDetails.model == undefined || $scope.systemDetails.model == ""){
			$scope.systemInvalid = "System model should not be empty!!";
			return false;
		}
		return true;
	}
	
	$scope.openPopupForAllocation = function(system){
		$scope.systemForAllocation = system;
		$scope.allocationObj = {};
		$scope.allocationObj.userName = "";
		$scope.allocationObj.systemId = $scope.systemForAllocation.systemId;
		$("#allocationPopup").modal('toggle');
	}
	
	
	function pushSystemToAllocation(systemInp){
		for(var i=0;i<$scope.allSystems.length;i++){
			if($scope.allSystems[i].systemId == systemInp.systemId){
				$scope.allSystems[i] = systemInp;
				break;
			}
		}
	}
	
	$scope.allocate = function(){
		var isValid = $scope.validateAllocation();
		if(!isValid)
			return;
		$http({
			url :  "/allocation/allocate?userName=" + localStorage.getItem("userName") +"&sessionId=" + localStorage.getItem("sessionId"),
			method : 'POST',
			data : JSON.stringify($scope.allocationObj),
			headers : {
				'Content-Type' : 'application/json'
			},
			transformRequest : angular.identity
		}).success(function(response) {

			if (response.status == -1) {
				alert("Session expired! Please login to continue");
				localStorage.removeItem("sessionId");
				localStorage.removeItem("userName");
				window.location.href="index.html";
			}else{
				
				alert('SystemId '+$scope.systemForAllocation.systemId+' allocated succesfully');
				pushSystemToAllocation(response.addldata);
				
			}
			$("#allocationPopup").modal('toggle');
			$scope.resetSystemForm();
		}).error(function(e) {
			alert("Oops.. something went wrong!");
			console.log(e);
		});
			
	}
	
	$scope.showAllocateDetails = function(system){
		$scope.allocationDetail = system.latestAllocation;
		$("#allocationDetailPopup").modal('toggle');
	}
	$scope.deallocate = function(system){
		$scope.systemForAllocation = system;
		$scope.allocationObj = $scope.systemForAllocation.latestAllocation;
		$http({
			url :  "/allocation/deallocate?userName=" + localStorage.getItem("userName") +"&sessionId=" + localStorage.getItem("sessionId"),
			method : 'POST',
			data : JSON.stringify($scope.allocationObj),
			headers : {
				'Content-Type' : 'application/json'
			},
			transformRequest : angular.identity
		}).success(function(response) {

			if (response.status == -1) {
				alert("Session expired! Please login to continue");
				localStorage.removeItem("sessionId");
				localStorage.removeItem("userName");
				window.location.href="index.html";
			}else{
				alert('SystemId '+$scope.systemForAllocation.systemId+' deallocated succesfully');
				pushSystemToAllocation(response.addldata);
			}
		}).error(function(e) {
			alert("Oops.. something went wrong!");
			console.log(e);
		});
			
	}
	
	$scope.validateAllocation = function(){
		$scope.allocationInvalid = "";
		if($scope.allocationObj.userName == null || $scope.allocationObj.userName == undefined || $scope.allocationObj.userName == ""){
			$scope.allocationInvalid = "Username should not be empty !!";
			return false;
		}
		return true;
	}
	
	$scope.fetchSystemInfo = function(system){
		$scope.systemDetails = system;
		//$scope.systemDetails.dateOfPurchaseFromUI = $scope.systemDetails.dateOfPurchase ? new Date($scope.systemDetails.dateOfPurchase).toString("MM/MM/YYYY") : "";
		if($scope.systemDetails.dateOfPurchase){
			var dateOfPurchase = new Date($scope.systemDetails.dateOfPurchase);
			var dd = dateOfPurchase.getDate();
			var mm = dateOfPurchase.getMonth() + 1; //January is 0!

			var yyyy = dateOfPurchase.getFullYear();
			if (dd < 10) {
			  dd = '0' + dd;
			} 
			if (mm < 10) {
			  mm = '0' + mm;
			} 
			dateOfPurchase = dd + '/' + mm + '/' + yyyy;
			$scope.systemDetails.dateOfPurchaseFromUI  = dateOfPurchase;
		}
		$("#dateOfPurchase").datepicker("update", $scope.systemDetails.dateOfPurchaseFromUI);
	}
	
	
	$scope.getAllSytemByAllocation = function(){
		if($scope.isAllocatedSelected && $scope.isDeallocatedSelected){
			$scope.getAllSystemDetails();
		}else if(!$scope.isAllocatedSelected && !$scope.isDeallocatedSelected){
			$scope.allSystems = [];
			if($scope.allSystems.length < 1){
				$scope.noData = "No data available!";
			}
		}
		else{
			var allocated = false;
			var formData = new FormData();
			if($scope.isAllocatedSelected)
				allocated = true;
			
			$http({
				url : "/system/findByAllocation?userName=" + localStorage.getItem("userName") +"&sessionId=" + localStorage.getItem("sessionId")+"&allocated="+allocated,
				method : 'GET',
				data : formData,
				headers : {
					'Content-Type' : undefined
				},
				transformRequest : angular.identity
			}).success(function(response) {

				if (response.status == -1) {
					alert("Session expired! Please login to continue");
					localStorage.removeItem("sessionId");
					localStorage.removeItem("userName");
					window.location.href="index.html";
				}
				else {
					$scope.allSystems = response.data;
					if($scope.allSystems.length < 1){
						$scope.noData = "No data available!";
					}
					
				}
			}).error(function(e) {
				alert("Oops.. something went wrong!");
				console.log(e);
			});
		}
		
	}
	
	$scope.getAllSystemDetails = function(){
		var formData = new FormData();
		$http({
			url :  "/system/getAllSystems?userName=" + localStorage.getItem("userName") +"&sessionId=" + localStorage.getItem("sessionId"),
			method : 'GET',
			data : formData,
			headers : {
				'Content-Type' : undefined
			},
			transformRequest : angular.identity
		}).success(function(response) {

			if (response.status == -1) {
				alert("Session expired! Please login to continue");
				localStorage.removeItem("sessionId");
				localStorage.removeItem("userName");
				window.location.href="index.html";
			}
			else {
				$scope.allSystems = response.data;
				if($scope.allSystems.length < 1){
					$scope.noData = "No data available!";
				}
				
			}
		}).error(function(e) {
			alert("Oops.. something went wrong!");
			console.log(e);
		});
	}
	$scope.getAllSystemDetails();
});

app.directive('datepicker', function() {
	return {
		restrict : 'A',
		require : 'ngModel',
		compile : function() {
			return {
				pre : function(scope, element, attrs, ngModelCtrl) {
					var format, dateObj;
					format = (!attrs.dpFormat) ? 'd/m/yyyy' : attrs.dpFormat;
					if (!attrs.initDate && !attrs.dpFormat) {
						// If there is no initDate attribute than we will get
						// todays date as the default
						dateObj = new Date();
						scope[attrs.ngModel] = dateObj.getDate() + '/'
								+ (dateObj.getMonth() + 1) + '/'
								+ dateObj.getFullYear();
					} else if (!attrs.initDate) {
						// Otherwise set as the init date
						scope[attrs.ngModel] = attrs.initDate;
					} else {
						// I could put some complex logic that changes the order
						// of the date string I
						// create from the dateObj based on the format, but I'll
						// leave that for now
						// Or I could switch case and limit the types of
						// formats...
					}
					// Initialize the date-picker
					$(element).datepicker({
						format : format,
					}).on('changeDate', function(ev) {
						// To me this looks cleaner than adding $apply(); after
						// everything.
						scope.$apply(function() {
							ngModelCtrl.$setViewValue(ev.format(format));
						});
					});
				}
			}
		}
	}
});