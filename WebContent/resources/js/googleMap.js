function initMap() {
    var directionsService = new google.maps.DirectionsService;
    var directionsDisplay = new google.maps.DirectionsRenderer;
    var posSecretaria = new google.maps.LatLng(2.4573845, -76.6349535,13);
    var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 7,
      center: posSecretaria
    });
    directionsDisplay.setMap(map);
    calculateAndDisplayRoute(directionsService, directionsDisplay);    
  }

  function calculateAndDisplayRoute(directionsService, directionsDisplay) {
	// Try HTML5 geolocation.
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
          var pos = {
            lat: position.coords.latitude,
            lng: position.coords.longitude
          };
          
          directionsService.route({
    	      origin: pos,
    	      destination: 'Cl. 5 #4-70, Popayán, Cauca',
    	      travelMode: 'DRIVING'
    	    }, function(response, status) {
    	      if (status === 'OK') {
    	    	  console.log('OK');
    	    	  console.log(response);
    	        directionsDisplay.setDirections(response);
    	      } else {
    	        console.log('Directions request failed due to ' + status);
    	      }
    	    });
          
          //map.setCenter(pos);
        }, function() {
          handleLocationError(true, infoWindow, map.getCenter());
        });
      } else {
        // Browser doesn't support Geolocation
        handleLocationError(false, infoWindow, map.getCenter());
      }
    }
  
  function handleLocationError(browserHasGeolocation, infoWindow, pos) {
	  console.log('Entro al Handler');
      infoWindow.setPosition(pos);
      infoWindow.setContent(browserHasGeolocation ?
                            'Error: The Geolocation service failed.' :
                            'Error: Your browser doesn\'t support geolocation.');
    }
    