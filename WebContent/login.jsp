<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<title>HealthBot | Login</title>
<link href="css/bootstrap.css" rel='stylesheet' type='text/css' />
<link href="css/style.css" rel='stylesheet' type='text/css' />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link
	href='http://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700,800'
	rel='stylesheet' type='text/css'>
<script type="application/x-javascript">
	
	
	
	
	
	
	

	 addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 












</script>
<script src="js/jquery-1.9.1.min.js"></script>
<!--hover-effect-->
<script src="js/hover_pack.js"></script>
<script type="text/javascript" src="js/jquery.mixitup.min.js"></script>
<script type="text/javascript">
	$(function() {

		var filterList = {

			init : function() {

				// MixItUp plugin
				// http://mixitup.io
				$('#portfoliolist').mixitup({
					targetSelector : '.portfolio',
					filterSelector : '.filter',
					effects : [ 'fade' ],
					easing : 'snap',
					// call the hover effect
					onMixEnd : filterList.hoverEffect()
				});

			},

			hoverEffect : function() {

				// Simple parallax effect
				$('#portfoliolist .portfolio').hover(function() {
					$(this).find('.label').stop().animate({
						bottom : 0
					}, 200, 'easeOutQuad');
					$(this).find('img').stop().animate({
						top : -30
					}, 500, 'easeOutQuad');
				}, function() {
					$(this).find('.label').stop().animate({
						bottom : -40
					}, 200, 'easeInQuad');
					$(this).find('img').stop().animate({
						top : 0
					}, 300, 'easeOutQuad');
				});

			}

		};

		// Run the show!
		filterList.init();

	});
</script>


</head>
<body>
	<!--start header-->
	<div class="header">
		<div class="header-top">
			<div class="container">

				<div class="menu">
					<a class="toggleMenu" href="#"><img src="images/nav_icon.png"
						alt="" /></a>
					<ul class="nav" id="nav">

						<li><a>HealthBot</a></li>
						<div class="clear"></div>
					</ul>
				</div>
				<div class="clear"></div>
				<script type="text/javascript" src="js/responsive-nav.js"></script>
				<script type="text/javascript" src="js/move-top.js"></script>
				<script type="text/javascript" src="js/easing.js"></script>
				<script type="text/javascript">
					jQuery(document).ready(function($) {
						$(".scroll").click(function(event) {
							event.preventDefault();
							$('html,body').animate({
								scrollTop : $(this.hash).offset().top
							}, 1200);
						});
					});
				</script>
			</div>
		</div>
	</div>
	<!--end header-->
	<!--start contact-->
	<div class="contact" id="contact">
		<div class="container">
			<div class="row">



				<h3 class="m_3 col-sm-offset-2">Login</h3>
				<div class="m_4">
					<span class="bottom_line"> </span>
				</div>

				<form class="form-horizontal" method="post"
					action="servlets/LoginServlet">
					<a class="col-sm-offset-2 col-sm-4" style="color: Olive Green;">${successMessage}</a><br>
					<a class="col-sm-offset-2 col-sm-4" style="color: Olive Green;">${errorMessage}</a><br>
					<div class="form-group">
						<label for="inputUserName" class="control-label col-sm-2">Username</label>
						<div class="col-sm-3">
							<input class="form-control" placeholder="Username" type="text"
								id="inputUserName" name="username" />
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="control-label col-sm-2">Password</label>
						<div class="col-sm-3">
							<input class="form-control" placeholder="Password"
								type="password" id="inputPassword" name="password" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-5">
							<button type="submit" class="btn btn-default">Login</button>
							<a href="register">Dont have an account? Sign Up!</a>
						</div>
					</div>
				</form>

				<%-- 	<form method="post" action="user/login">
					<div >
						<p >
							<label for="author">Username</label> <input id="author"
								name="username" type="text" value="" size="30"
								aria-required="true">
						</p>
						<p>
							<label for="author">Password</label> <input id="author"
								name="password" type="password" value="" size="30"
								aria-required="true">
						</p>
						<p>${errorMessage}</p>
					</div>
					<div class="clear"></div>
					<div>
						<input type="submit" value="Login"> <a
							href="user/register">Dont have an account? Sign Up!</a>
					</div>
				</form> --%>
			</div>
		</div>
	</div>

	<div class="footer-bottom">
		<div class="container">
			<ul class="footer-nav">
				<li><a href="#">Home</a></li>
				<li><a href="#">Features</a></li>
				<li><a href="#">Contact</a></li>
			</ul>
			<div class="copy">
				<p>
					© 2017 Template <a href="#" target="_blank">| HealthBot</a>
				</p>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<!--end contact-->

	<script type="text/javascript">
		$(document).ready(function() {

			var defaults = {
				containerID : 'toTop', // fading element id
				containerHoverID : 'toTopHover', // fading element hover id
				scrollSpeed : 1200,
				easingType : 'linear'
			};

			$().UItoTop({
				easingType : 'easeOutQuart'
			});

		});
	</script>
	<a href="#" id="toTop" style="display: block;"><span
		id="toTopHover" style="opacity: 1;"></span></a>

</body>
</html>