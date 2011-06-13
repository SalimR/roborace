<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@page session="false"%>
<!doctype html>

<!--[if lt IE 7 ]><html class="no-js ie6" lang="en"><![endif]-->
<!--[if IE 7 ]><html class="no-js ie7" lang="en"><![endif]-->
<!--[if IE 8 ]><html class="no-js ie8" lang="en"><![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--><html class="no-js" lang="en"><!--<![endif]-->
<head>
	<meta charset="utf-8">
	<title>Twissandra</title>
	<meta name="description" content="Cassandra Showcase">
	<meta name="author" content="Sascha Krüger">

	<!-- Mobile viewport optimized: j.mp/bplateviewport -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<!-- place favicon.ico & apple-touch-icon.png in the root of your domain and delete these references -->
	<link rel="shortcut icon" href="images/favicon.ico">

	<!-- CSS with version information to control caching -->
	<link rel="stylesheet" media="all" href="resources/style.css?v=1">

	<!-- all JavaScript at the bottom, except for Modernizr which enables HTML5 elements & feature detects -->
	<script src="scripts/libs/modernizr-1.7.min.js"></script>
	
</head>

<body>

	<div id="container">
		<header>
			<h1>Twissandra</h1>
		</header>
		<section id="main">
			<input id="username" name="username" type="text" />
			<input id="password" name="password" type="password" />
			<input type="submit" value="Register" />
		</section>
		<footer>
		</footer>
	</div>

	<!-- JavaScript at the bottom for fast page loading -->

	<!-- Grab Google CDN's jQuery, with a protocol relative URL; fall back to local if necessary -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.js"></script>
	<script>window.jQuery || document.write("<script src='scripts/libs/jquery-1.5.1.min.js'>\x3C/script>")</script>

	<!-- Javascript at the bottom for fast page loading -->
	<script src="scripts/plugins.js"></script>
	<script src="scripts/script.js"></script>

</body>
</html>
