'use strict';

/* Controllers */

angular.module('myApp.controllers', [])
  .controller('LandingPageController', [function() {

  }])
  .controller('WaitlistController',['$scope', 'partyService', 'textMessageService', function($scope, partyService, textMessageService) {
    $scope.parties = partyService.parties; //binds FB parties to scope

    // Object to store data from the waitlist form.
    $scope.newParty = {name: '', phone: '', size: '', done: false, notified: 'No'};

    // Function to save a new party to the waitlist.
    $scope.saveParty = function() {
      partyService.saveParty($scope.newParty, $scope.currentUser.id);
      $scope.newParty = {name: '', phone: '', size: '', done: false, notified: 'No'};
    };

    //function to send text message to party
    $scope.sendTextMessage = function(party){
      textMessageService.sendTextMessage(party);
    };
  }])
  .controller('AuthController', ['$scope', 'authService', function($scope, authService ) {

    //obj bound to inputs on reg and login
    $scope.user = {email: '', password: ''};

    $scope.authService = authService;


    //methods for registering, login, and logout new user using authService
    $scope.register = function() {
      authService.register($scope.user);
    };

    $scope.login = function(){
      authService.login($scope.user);
    };

    $scope.logout = function(){
      authService.logout();
    };
  }]);
