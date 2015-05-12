'use strict';

/* Controllers */

angular.module('myApp.controllers', [])
  .controller('LandingPageController', [function() {

  }])
  .controller('WaitlistController',['$scope', '$firebase', 'FIREBASE_URL', function($scope, $firebase, FIREBASE_URL) {
    //connecting scope.parties to live firebase data
    var partiesRef = new Firebase(FIREBASE_URL + 'parties');
    $scope.parties = $firebase(partiesRef);
    //object to store data to waitlist form
    $scope.newParty = { name: '', phone: '', size: '', done:false, notified: 'No'};

    //for saving parties to DB
    $scope.saveParty = function(){
      $scope.parties.$add($scope.newParty);
      $scope.newParty = { name: '', phone: '', size: '', done:false, notified: 'No' };
    };
    //function to send text message to party
    $scope.sendTextMessage = function(party){
      var textMessageRef = new Firebase(FIREBASE_URL + 'textMessages');
      var textMessages = $firebase(textMessageRef);
      var newTextMessage = {
        phoneNumber: party.phone,
        size: party.size,
        name: party.name,
        done:false 
      };
      textMessages.$add(newTextMessage);
      party.notified = "Yes";
      $scope.parties.$save(party.$id); //give id of party being saved
    };
  }])
  .controller('AuthController', ['$scope', '$firebaseSimpleLogin', '$location', 'FIREBASE_URL', function($scope, $firebaseSimpleLogin, $location, FIREBASE_URL ) {
    var authRef = new Firebase(FIREBASE_URL);
    var auth = $firebaseSimpleLogin(authRef); //returned object

    $scope.user = {email: '', password: ''};
    $scope.register = function() {
      auth.$createUser($scope.user.email, $scope.user.password).then(function(data) { //promise
        console.log(data);
        $scope.login();
      });
    };

    $scope.login = function(){
      auth.$login('password', $scope.user).then(function(data){
        console.log(data);
        //redir users to waitlist
        $location.path('/waitlist');
      });
    };
    $scope.logout = function(){
      auth.$logout();
      //redir users to landing page
      $location.path('/')
    };
  }]);
