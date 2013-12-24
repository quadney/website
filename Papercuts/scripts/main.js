$(document).ready(function(){
	$('a[href^="#"]').on('click',function (e) {
	    e.preventDefault();
	    var target = this.hash,
	    $target = $(target);
	    var place = $target.offset().top - 280;
	    $('html, body').stop().animate({
	        'scrollTop': place
	    }, 900, 'swing', function () {
	        window.location.hash = target;
	    });
	});
	var images = $('#slideshow > img');
	var currentimage = 0;
	$('#left-arrow').on('click', function(){
  		$(images[currentimage]).fadeOut('fast');
  		currentimage--;
  		if(currentimage < 0)
  			currentimage = images.length - 1;
  		setTimeout(function(){
  			$(images[currentimage]).fadeIn('fast')
  		}, 300);
      $('#caption').text($(images[currentimage]).attr("alt"));
	});
	$('#right-arrow').on('click', function(){
  		$(images[currentimage]).fadeOut('fast');
  		currentimage++;
  		if(currentimage == images.length)
  			currentimage = 0;
  		setTimeout(function(){
  			$(images[currentimage]).fadeIn('fast')
  		}, 300);
      $('#caption').text($(images[currentimage]).attr("alt"));
	});
	$(window).scroll(function () { 
          var value = $(this).scrollTop();
          if ( value > 890 ){
          		$('#filler').css({
          			'display': 'block'
          		});
              $('header').addClass('headerFixed');
              $('nav').addClass('navFixed');
              $('#logo').addClass('logoFixed');
          }
          else {
          		$('#filler').css({
          			'display': 'none'
          		});
              $('header').removeClass('headerFixed');
              $('nav').removeClass('navFixed');
              $('#logo').removeClass('logoFixed');
          }
    });
  if(($(document).width() > 767) && ($(document).width() < 1000)){
      var player = $('#unityPlayer').children();
      player.attr("height", "506px");
      player.attr("width", "760px");
      player.attr("style", "display: block; width: 760px; height: 506px;");
  }
  $(window).resize(function(){
      var player = $('#unityPlayer').children();
      if(($(document).width() > 767) && ($(document).width() < 1000)){
        player.attr("height", "506px");
        player.attr("width", "760px");
        player.attr("style", "display: block; width: 760px; height: 506px;");
      }
      else if($(document).width() > 999){
        player.attr("height", "600px");
        player.attr("width", "960px");
        player.attr("style", "display: block; width: 960px; height: 600px;");
      }
  });
});