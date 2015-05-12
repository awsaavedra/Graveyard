'use strict';

/* Services */


// Demonstrate how to register services
// In this case it is a simple value service.
angular.module('myApp.services', [])
  .factory('FIREBASE_URL', function(){
  	return 'https://waitandeat-alexander.firebaseio.com/';
  });
