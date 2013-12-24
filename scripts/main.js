var topMargin = 180;
$(document).ready(function(){
	getTopMargin();
	window.onresize = getTopMargin();
	
	$('a[href^="#"]').on('click',function (e) {
	    e.preventDefault();
	    var target = this.hash,
	    $target = $(target);
	    var place = $target.offset().top - topMargin;
	    $('html, body').stop().animate({
	        'scrollTop': place
	    }, 900, 'swing', function () {
	        window.location.hash = target;
	    });
	});
	$('.manifest_content').on('click', function(){
		//$('.explain').hide();
		//$(this).next().next().next().slideToggle();
	});
	$(document).on('scroll', function(){
		var pos = $(window).scrollTop();
		var expand = true;
		if(pos > 0 && screenSize >1330 && expand == true){
			$('header').addClass('smallHeader');
			$('#content').animate({top: "75px"}, 'fast');
			//$('#content').css('top: 75px;');
			//console.log($('header').next().name);
			topMargin = 75;
			expand = false;
		}
		else if(pos == 0){
			$('header').removeClass('smallHeader');
			$('#content').animate({top: "180px"}, 'fast');
			$('#content').css('top: 180px;');
			topMargin = 180;
			expand = true;
		}
	});

});
function getTopMargin(){
	var screenSize = $(document).width();
	if(screenSize < 480)
		topMargin = 150;
	else if(screenSize < 800)
		topMargin = 120;
	else {
		topMargin = 180;
	}
}