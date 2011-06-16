/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

var loggedIn = false;

$(document).ready(function() {

	// when page loads check if user is logged in and adjust login header
	$.ajax({
		url : 'rest/session',
		dataType : 'json',
		success : function(result) {
			loggedIn = result;
			if (loggedIn) {
				$('#welcome #login').hide();
				$('#welcome #logout').show();
			} else {
				$('#welcome #logout').hide();
			}
		}
	});
	
	// prepare playfield
	for (i=1; i<=10; i++) {
		$('#playfield').append('<tr></tr>');
	}
	for (i=1; i<=16; i++) {
		$('#playfield tr').append('<td></td>');
	}

	//cache nav  
	var nav = $("#navigation");  
	
	// add indicators and hovers to submenu parents  
	nav.find("li").each(function() {  
		if ($(this).find("ul").length > 0) {  
		
			// show subnav on hover  
			$(this).mouseenter(function() {  
				$(this).find("ul").stop(true, true).slideDown();  
			});  
		
			// hide submenus on exit  
			$(this).mouseleave(function() {  
				$(this).find("ul").stop(true, true).slideUp();  
			});  
		}  
	});  


});

