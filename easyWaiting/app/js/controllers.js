'use strict';

/* Controllers */

angular.module('myApp.controllers', [])
  .controller('LandingPageController', [function() {

  }])
  .controller('WaitlistController', ['$scope', 'partyService', 'textMessageService', 'authService', function($scope, partyService, textMessageService, authService) {

    //Bind user's parties to $scope.parties
    authService.getCurrentUser().then(function(user) {
      if(user){
        $scope.parties = partyService.getPartiesByUserId(user.id);
      };
    })
    // Object to store data from the waitlist form.
    $scope.newParty = {name: '', phone: '', size: '', done: false, notified: 'No'};

    // Function to save a new party to the waitlist.
    $scope.saveParty = function() {
      partyService.saveParty($scope.newParty, $scope.currentUser.id);
      $scope.newParty = {name: '', phone: '', size: '', done: false, notified: 'No'};
    };

    //function to send text message to party
    $scope.sendTextMessage = function(party){
      textMessageService.sendTextMessage(party, $scope.currentUser.id);
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
