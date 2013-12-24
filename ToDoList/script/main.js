var users = [];

var defaultList = ["Default List", "<li><input type=\"checkbox\"><label>This is a reminder!</label></li>",
				"<li><input type=\"checkbox\"><label>Type in the box and press Enter</label></li>",
				"<li><input type=\"checkbox\"><label>Check the box when you're finished with the task</label></li>",
				"<li><input type=\"checkbox\"><label>Click the Red menu button to see more to-do lists</label></li>",
				"<li><input type=\"checkbox\"><label>This web app is also responsive! It is also designed for mobile and tablets!</label></li>",
				"<li><input type=\"checkbox\"><label>To Do: connect with a database to save lists and items</label></li>",
				"<li><input type=\"checkbox\"><label>To Do: connect with Facebook so sharing of lists is possible, and using a profile image from Facebook</label></li>",
				"<li><input type=\"checkbox\"><label>To Do: Possibility of being a chrome app!</label></li>"];
				
var Sydney = ["Sydney", "<li><input type=\"checkbox\"><label>Hello</label></li>",
				"<li><input type=\"checkbox\"><label>Second List testing</label></li>",
				"<li><input type=\"checkbox\"><label>HI HI HI HI HI HI</label></li>"];

users.push({name: "Sydney Richardson", userName: "sydney", password: "123", toDoList: [defaultList, Sydney]});

var currentUser;
var currentList;
var listSelected;

$(document).ready(function(){
	//load the remindersContent with the content
	var log = true;
	//on click of the "Create an Account" button, hide the overlayLogin and show overlayCreate
	$('#create').on('click', function(){
		$('#overlayLogin').fadeOut('fast');
		$('#overlayLogin').addClass('hide');
		$('#overlayCreate').fadeIn('fast');
		log = false;
	});
	$('#create2').on('click', function(){
		$('#overlayCreate').fadeOut('fast');
		$('#overlayCreate').addClass('hide');
		$('#overlayLogin').fadeIn('fast');
		log = true;
	});
	
	//on submit button, hide the overlay stuff 
	var submitClicks = 0;
	$('.submitButton').on('mousedown', function(){
		$(this).addClass('shadow');
	}).on('mouseup', function(){
		//validate form before continuing
		submitClicks++;
		$(this).removeClass('shadow');
		var validation = validate(log);
		
		if(validation){
			var overlay = $('#overlay');
			overlay.fadeOut('fast');
			setTimeout(function(){overlay.addClass('hide')},800);
			$('#showList').removeClass('hide');
			//loadLists();
			//load the first list contents into the reminders div
			load(0);
			//highlight the list that it is currently in, which is the first
			listSelected = $('#toDoListSelect li').first().next();
			listSelected.addClass('highlightList');
		}
		if(!validation && submitClicks <= 1){
			$('#overlayLogin').append('<h4>Username or password is incorrect, or you need to create an account</h4>');
		}
	});
	
	$('.password').on('keypress', function(){
		if(event.which == 13){
			//validate form before continuing
			submitClicks++;
			$(this).removeClass('shadow');
			var validation = validate(log);
		
			if(validation){
				var overlay = $('#overlay');
				overlay.fadeOut('fast');
				setTimeout(function(){overlay.addClass('hide')},800);
				$('#showList').removeClass('hide');
				//load the lists and reminders
				//loadLists();
				load(0);
				//highlight the list that it is currently in, which is the first
				listSelected = $('#toDoListSelect li').first().next();
				listSelected.addClass('highlightList');
			}
			if(!validation && submitClicks <= 1){
				$('#overlayLogin').append('<h4>Username or password is incorrect, or you need to create an account</h4>');
			}
		}
	});
	
	var leftOpen = false;
	$('.menu_button').on('mousedown', function(){
		$(this).addClass('clicked');
	}).on('mouseup', function(){
		$(this).removeClass('clicked');
		//add the animation stuff
		var width = $(document).width();
		animateLeftPanel(leftOpen, width);
		if(leftOpen)
			leftOpen=false;
		else
			leftOpen=true;
	});
	
	$('#reminderInput').on('keypress', function(event){
		if(event.which == 13){
			var reminder = $(this).val();
			var message = "<li><input type=\"checkbox\"><label>"+reminder+"</label></li>"
			var list = $('#showList').first().prepend(message);
			currentUser.toDoList[currentList].push(message);
			$(this).val("");
		}
	});
	
	$('input:checkbox').on('click', function(){
		var check = $('input:checked').parent().fadeOut('fast');
		setTimeout(function(){check.remove();},800);
		removeReminderFromList(check.index());
	});
	
	$('#logout').on('mousedown', function(){
		$(this).addClass('clicked');
	}).on('mouseup', function(){
		var width = $(document).width();
		$(this).removeClass('clicked');
		animateLeftPanel(leftOpen, width);
		logOut();
	});
	
	$("#createNewList").on('mousedown', function(){
		//add the border shadow class
		$(this).addClass('shadow');
	}).on('mouseup', function(){
		var button = $(this);
		button.removeClass('shadow');
		//add prepend a new li to the create new list
		var listName = prompt("Name your new list");
		if(listName != null){
			button.parent().append("<li>"+listName+"<div class=\"delete\"></div></li>");
			
			//add the list to the currentUser's toDoList array
			currentUser.toDoList.push([listName]);
			
			//clear what's in the reminders content and make the new list the viewable list
			selectDiffList(listName);
			
			//highlight the selected list in the toDoListSelect div
			listSelected = $('#toDoListSelect li').last();
			listSelected.addClass('highlightList');
		}
	});
	
	
	//highlight a list that is selected and load that list
	$('#toDoListSelect li').on('click', function(){
		if(listSelected != null){
			listSelected.removeClass('highlightList');
		}
		listSelected = $(this);
		alert(listSelected.html());
		listSelected.addClass('highlightList');
		selectDiffList(listSelected.text());
	});
	
	$(".delete").on('click', function(){
		//get the li that it is closest to
		var deleteThis = $(this).closest("li");
		listSelected = deleteThis.next();
		
		//remove from the html code
		deleteThis.fadeOut('fast');
		setTimeout(function(){deleteThis.remove()},800);
		
		
		if(currentUser.toDoList[currentList][0] == deleteThis.text()){
			//delete the list from the array
			deleteList(deleteThis.text());
			//change the current list
			alert(listSelected.html());
			listSelected.addClass('highlightList');
			selectDiffList(listSelected.text());
		}
		else{
			//delete the list from the array
			deleteList(deleteThis.text());
		}
	});
	
});

function animateLeftPanel(open, currentWidth){
	if(currentWidth < 801){
		if(open){
			$('#left_menu').animate({
				'width': '0%'
			}, 1000);
			$('.remindersContent').animate({
				'width': '100%'
			}, 1000);
			$('#userInfo').addClass('hide');
			$("#toDoListSelect").addClass('hide');
			$('#logout').addClass('hide');
		}
		else{
			$('#left_menu').animate({
				'width': '55%'
			}, 1000);
			$('.remindersContent').animate({
				'width': '45%'
			}, 1000);
			$('#userInfo').removeClass('hide');
			$("#toDoListSelect").removeClass('hide');
			$('#logout').removeClass('hide');
		}
	}
	else{
		if(open){
			$('#left_menu').animate({
				'width': '0%'
			}, 1000);
			$('.remindersContent').animate({
				'width': '100%'
			}, 1000);
			$('#userInfo').addClass('hide');
			$("#toDoListSelect").addClass('hide');
			$('#logout').addClass('hide');
		}
		else{
			$('#left_menu').animate({
				'width': '19%'
			}, 1000);
			$('.remindersContent').animate({
				'width': '81%'
			}, 1000);
			$('#userInfo').removeClass('hide');
			$("#toDoListSelect").removeClass('hide');
			$('#logout').removeClass('hide');
		}
	}
}

function validate(loginBool){
	if(loginBool){
		//using the login option
		var username = $('#overlayLogin').find(".username");
		var password = $('#overlayLogin').find(".password");
		var validateTrue = login(username.val().toLowerCase(), password.val());
		
		username.val('');
		password.val('');
		
		if(!validateTrue)
			return false;
		else
			return true;
	}
	else{
		//using the create account option
		var name = $('#overlayCreate').find(".name");
		var username = $('#overlayCreate').find(".username");
		var password = $('#overlayCreate').find(".password");
		createNewUser(name.val(), username.val().toLowerCase(), password.val());
		
		name.val('');
		username.val('');
		password.val('');
		return true;
	}
}

function login(uName, pass){
	for(var i = 0; i < users.length; i++){
		if(users[i].userName == uName){
			if(users[i].password == pass){
				//login user into the system
				currentUser = users[i];
				currentList = 0;
				//populate user info card stuff
				userInfoStuff();
				return true;
			}
			else{
				//display incorrect password function
				return false;
			}
		}
	}
	return false;
}

function logOut(){
	currentUser = null;
	//clear the unordered list of reminders and lists and user accounts
	//var list = $('#showList');
	//display the login and overlay dialog
	$('#overlay').removeClass('hide').fadeIn('fast');
	$('#showList').addClass('hide');
}

function createNewUser(fName, uName, pass){
	//check if they already have an account, if not create a new account
	var haveAccount = false;
	for(var i = 0; i < users.length; i++){
		if(users[i].userName == uName){
			if(users[i].password == pass){
				//login user into the system
				currentUser = users[i];
				currentList = 0
				//populate user info card stuff
				userInfoStuff();
				haveAccount = true;
			}
		}	
	}
	if(!haveAccount){
		var newUser = {
			name: fName,
			userName: uName,
			password: pass,
			toDoLists: [defaultList]
		};
		users.push(newUser);
		currentUser = newUser;
		currentList = 0;
		//populate user info card stuff
		userInfoStuff();
	}
}

function userInfoStuff(){
	$('#userInfo').text(currentUser.name);
}

function loadLists(){
	var listList = $('#toDoListSelect');
	for(var i = 0; i < currentUser.toDoList.length; i++){
		listList.append("<li>"+currentUser.toDoList[i][0]+"<div class=\"delete\"></div></li>");
	}
}

function load(listIndex){
	//load the list onto the main reminder unordered list
	var list = $('#showList');
	for(var i = 1; i < currentUser.toDoList[listIndex].length; i++){
		//alert("load "+i);
		list.append(currentUser.toDoList[listIndex][i]);
	}
}

function selectDiffList(listName){
	//clear what's in the #showlist content
	$('#showList').children().detach();
	
	//populate with the selected list
	for(var i = 0; i < currentUser.toDoList.length; i++){
		if(currentUser.toDoList[i][0] == listName){
			//set the currentList index to the new one
			currentList = i;
			//display this list into the main page
			load(i);
		}
	}
}

function removeReminderFromList(indx){
	currentUser.toDoList[currentList].splice(indx, 1);
}

function deleteList(listName){
	//find the list in the currentUser toDoList array
	for(var i = 0; i < currentUser.toDoList.length; i++){
		if(currentUser.toDoList[i][0] == listName){
			//remove the list
			currentUser.toDoList.splice(i, 1);
			break;	
		}
	}
}