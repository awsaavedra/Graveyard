'use strict';

/* Controllers */

angular.module('myApp.controllers', [])
  .controller('LandingPageController', [function() {

  }])
  .controller('WaitlistController',['$scope', '$firebase', function($scope, $firebase) {
    //connecting scope.parties to live firebase data
    var partiesRef = new Firebase('https://waitandeat-alexander.firebaseio.com/parties');
    $scope.parties = $firebase(partiesRef);
    //object to store data to waitlist form
    $scope.newParty = { name: '', phone: '', size: ''};

    //for saving parties to DB
    $scope.saveParty = function(){
      $scope.parties.$add($scope.newParty);
      $scope.newParty = { name: '', phone: '', size: '', done:false };
    };
    //function to send text message to party
    $scope.sendTextMessage = function(party){
      var textMessageRef = new Firebase('https://waitandeat-alexander.firebaseio.com/textMessages');
      var textMessages = $firebase(textMessageRef);
      var newTextMessage = {
        phoneNumber: party.phone,
        size: party.size,
        name: party.name,
        done:false 
      };
      textMessages.$add(newTextMessage);
    };
  }]);