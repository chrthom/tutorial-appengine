if (window.location.protocol != 'https:' && window.location.hostname != 'localhost')
    window.location.href = 'https:' + window.location.href.substring(window.location.protocol.length);

angular.module('techsurvey', ['ionic', 'ngRoute']).config(function($routeProvider) {
    $routeProvider.when('/', {
        controller: 'LandingPageCtrl',
        templateUrl: 'templates/landingpage.html'
    }).when('/new', {
        controller: 'TechCreateCtrl',
        templateUrl: 'templates/techdetails.html'
    }).when('/edit/:id', {
        controller: 'TechEditCtrl',
        templateUrl: 'templates/techdetails.html'
    }).when('/compare', {
        controller: 'CompareCtrl',
        templateUrl: 'templates/compare.html'
    }).otherwise({
        redirectTo: '/'
    })
}).controller('MainCtrl', function($scope, $location) {
    $scope.$on('$routeChangeStart', function(next, current) {
        switch ($location.path()) {
            case '/new':
                $scope.pageTitle = 'Technologie hinzufügen';
                break;
			case '/compare':
                $scope.pageTitle = 'Technologie vergleichen';
                break;
            default:
                $scope.pageTitle = 'Top Technologien';
        }
    });
}).controller('LandingPageCtrl', function($scope, $http, $timeout) {
    $scope.techs = [];
    $scope.getTechs = function() {
        $http.get('/_ah/api/techsurvey/v1/tech').then(function(response) {
            $scope.techs = response.data.items;
            $timeout($scope.getTechs, 5000);
        });
    };
    $scope.getTechs();
}).controller('TechCreateCtrl', function($scope, $http, $location) {
    $scope.tech = {};
    $scope.save = function() {
        $http.post('/_ah/api/techsurvey/v1/tech', $scope.tech).then(function() {
            $location.path('/');
        });
        $scope.busy = true;
    };
}).controller('TechEditCtrl', function($scope, $http, $location, $routeParams) {
    $scope.editMode = true;
    $scope.tech = {};
    $http.get('/_ah/api/techsurvey/v1/tech/' + $routeParams.id).then(function(response) {
        $scope.tech = response.data;
    });
    $scope.save = function() {
        $http.put('/_ah/api/techsurvey/v1/tech', $scope.tech).then(function() {
            $location.path('/');
        });
        $scope.busy = true;
    };
    $scope.delete = function() {
        $http.delete('/_ah/api/techsurvey/v1/tech/' + $scope.tech.id).then(function() {
            $location.path('/');
        });
        $scope.busy = true;
    };
}).controller('CompareCtrl', function($scope, $http, $location) {
	$scope.tech1 = {};
	$scope.tech2 = {};
	$scope.notEnoughTechs = false;
	$scope.getTechs = function() {
        $http.get('/_ah/api/techsurvey/v1/tech').then(function(response) {
            var techs = response.data.items;
            if (techs.length > 1) {
				$scope.tech1 = techs[Math.floor(Math.random()*techs.length)];
				do {
					$scope.tech2 = techs[Math.floor(Math.random()*techs.length)];
				} while ($scope.tech1.name == $scope.tech2.name)
			} else {
				$scope.notEnoughTechs = true;
			}
		});
    };
	$scope.getTechs();
	$scope.choose = function(preference) {
	    $http.post('_ah/api/techsurvey/v1/choose', {
	        preference: preference,
	        leftTechId: $scope.tech1.id,
	        rightTechId: $scope.tech2.id
	    });
	    $scope.getTechs();
	};
});