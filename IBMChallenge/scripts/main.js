var cars = new Array();
cars.push(['Economy', 34.99, 'Chevy Spark or similar', 'economy.jpg']);
cars.push(['Compact', 35.99, 'Nissan Versa or similar', 'compact.jpg']); 
cars.push(['Intermediate', 43.99, 'Toyota Corolla, Ford Focus or similar', 'intermediate.jpg']);
cars.push(['Standard', 49.98, 'Chrysler 200 or similar', 'standard.jpg']);
cars.push(['Full Size', 49.99, 'Ford Fusion, Nissan Altima or similar', 'fullsize.jpg']);
cars.push(['Premium', 35.12, 'Chrysler 300 or similar'], 'premium.jpg');
cars.push(['Luxury', 0, 'Cadillac ATS, Lincoln MKZ or similar', 'luxury.jpg']);
cars.push(['Minivan', 50.52, 'Dodge Grand Caravan, Chrysler Town and Country or similar', 'minivan.png']);
cars.push(['Intermediate SUV', 97.99, 'Toyota Rav 4 or similar', 'intermediateSUV.jpg']);
cars.push(['Standard SUV', 97.99, 'Hyundai Santa Fe, or similar', 'standardSUV.jpg']);
cars.push(['Luxury SUV', -1, 'Cadillac Escalade, Lincoln Navigator or similar', 'luxurySUV.png']);
cars.push(['Premium Elite', -1, 'Lincoln MKX or similar', 'premiumelite.jpg']);
cars.push(['Large SUV', 0, 'Chevy Tahoe, Ford Expedition, or similar', 'largeSUV.jpg']);
cars.push(['Pickup Truck', 49.99, 'Chevy Colorado, Dodge Dakota or similar']);
cars.push(['Large Pickup', 59.99, 'Dodge Ram 1500 Quad Cab or similar']);
cars.push(['Full Size Crossover', -1, 'Ford Edge or similar']);
cars.push(['Premium SUV', 0, 'Chevy Suburban, Ford Expedition XL or similar']);
cars.push(['Convertible', 0, 'Ford Mustang or similar']);

var vehicleFeeRecovery = 0.78;
var rentalCarSurchargeRate = 2.00;
var tireAndBatteryFeeRate = 0.02;
var underageFee = 30.00;
var salesTax = 0.06;
var total;

var back = new Array();
var currentPage = "#index";
var name;
var creditcard;
var email;
var carIndexSelected = -1;

$(document).ready(function(){

	//populate car list
	var list = $('#car_list');

	for(var i = 0; i < cars.length; i++){
		if(cars[i][1] > 0){
			list.append("<li><h2>"+cars[i][0]+"</h2><h3>$"+cars[i][1]+"/day</h3><p>"+cars[i][2]+"</p>");
			//TODO add the images
			//list.append("<li><img src=\"images/cars/"+cars[i][3]+"\"/><h2>"+cars[i][0]+"</h2><h3>$"+cars[i][1]+"/day</h3><p>"+cars[i][2]+"</p>");

		}
		else if(cars[i][1] == 0){
			//list.append("<li><h2>"+cars[i][0]+"</h2><h3>"+Call+"/day</h3><p>"+cars[i][2]+"</p>");
		}
	}

	$('li').click(function(){
		//if(carIndexSelected > -1)
			//$('li').get(carIndexSelected).removeClass('selected');
		carIndexSelected = $(this).index();
		console.log(carIndexSelected);
		$(this).addClass('selected');
	});
});

var hide = 'hidden';

function continueAsGuest(){
	$('#index').addClass(hide);
	$('#guest').removeClass(hide);
	$('#back_arrow').removeClass(hide);
	back.push('#index');
	currentPage = '#guest';
}

function rentCar() {
	$('#guest').addClass(hide);
	$('#rent_car').removeClass(hide);
	back.push('#guest');
	currentPage = '#rent_car';

	//$('#pickup_date').value = (Date()).format("mm/dd/yy");

	//console.log($('#pickup_date').value);
}

function existingReservation(){
	console.error("Existing reservation not yet implemented");
}

function selectCar(){
	$('#rent_car').addClass(hide);
	$('#select_car').removeClass(hide);
	back.push('#rent_car');
	currentPage = '#select_car';
}

function userInfo(){
	$('#select_car').addClass(hide);
	$('#user_info').removeClass(hide);
	back.push('#select_car');
	currentPage = '#user_info';

	name = $('#username').value;
	phoneNumber = $('#phonenumber').value;
	creditcard = $('#creditcard').value;
}

function summary(){
	$('#user_info').addClass(hide);
	$('#summary').removeClass(hide);
	back.push('#user_info');
	currentPage = '#summary';
}

function submit(){
	$('#summary').addClass(hide);
	$('#email_sent').removeClass(hide);
	$('#back_arrow').addClass(hide);
}

function goBack(){
	$(currentPage).addClass(hide);
	currentPage = back.pop();
	$(currentPage).removeClass(hide);
	if(currentPage == '#index')
		$('#back_arrow').addClass(hide);
}

function diffLocation(){
	$('#return_location').removeClass(hide);
	$('#diffLocButton').addClass(hide);
}

