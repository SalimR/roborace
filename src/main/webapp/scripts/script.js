/*
 * Copyright 2011 the original author or authors.
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

(function(window, $, undefined) {
	
	// use the correct document accordingly with window argument (sandbox)
	var document = window.document;
	
	// here we define a namespace for our game client
	var roborace = {
	
		// when game is started then the ballefield data will be placed here
		battlefield : null,
		
		userSession : {
			loggedIn : false,
			username : null,
			roles : []
		},
		
		/**
		 * Initialize the interface and game engine.
		 */
		init : function() {
			this.checkLoginState();
			this.createNavigation();
		},
	
		/**
		 * Determine the login state and adjust the interface accordingly.
		 */
		checkLoginState : function() {
		
			this.getJSON('rest/session', function(result) {
				roborace.userSession.loggedIn = result;
				if (result) {
					$('#login').addClass("hidden");
					$('#logout').removeClass("hidden");
				} else {
					$('#login').removeClass("hidden");
					$('#logout').addClass("hidden");
				}
			});
		
		},

		/** 
		 * Attaches event handlers to the navigation menu to show/hide submenus.
		 */
		createNavigation : function() {
		
			$("#navigation").find('li').each(function() {
				if ($(this).find('ul').length > 0) {
					$(this).mouseenter(function() {
						$(this).find('ul').stop(true, true).slideDown();
					});
					$(this).mouseleave(function() {
						$(this).find('ul').stop(true, true).slideUp();
					});
				}
			});
		
			$('#startGame').click(function() {
				roborace.loadBattlefield('42');
			});
			
		},

		/**
		 * Retrieves JSON data from the server.
		 * 
		 * @param url 
		 *            server URL to call
		 * @param callback 
		 *            callback function to call with the result data
		 */
		getJSON : function(url, callback) {
			return $.get(url, null, callback, 'json');
		},

		/**
		 * Posts JSON data to the server.
		 * 
		 * @param url 
		 *            server URL to call
		 * @param data 
		 *            data to pass in the HTTP body
		 * @param callback 
		 *            callback function to call with the result data
		 */
		postJSON : function(url, data, callback) {
			return $.post(url, data, callback, 'json');
		},
		
		/**
		 * Loads the map for the given mapId from the server
		 *
		 * @param mapId 
		 *            id of the map to load
		 */
		loadBattlefield : function(mapId) {
			
			// load the map data from the server
			this.getJSON('rest/battlefield/map/' + mapId, function(data) {
				
				// store battelfield information
				roborace.battlefield = data;
				
				roborace.displayMap(data.map);
				roborace.displayHandSlots();
				
			});
			
		},
		
		/**
		 *  Displays the map data on the battlefield
		 */
		displayMap : function(data) {
			
			var width = data.width;
			var height = data.height;
			
			var rowString = '';
			for (var rowIndex = 0; rowIndex < height; rowIndex++) {
				var row = data.tiles[rowIndex];
				var columnString = '';
				for (var columnIndex = 0; columnIndex < width; columnIndex++) {
					columnString += '<td class="' + row[columnIndex] + '"></td>';
				}
				rowString += '<tr>' + columnString + '</tr>';
			}
			
			$('#battlefield').html(rowString);
			
		},
		
		/**
		 * Reveal the card slots of the players hand.
		 */
		displayHandSlots : function() {
			$('#hand').removeClass('hidden');
		}
		

	};
	
	// initialize game client when document is loaded
	$(document).ready(roborace.init());
	
})(window, jQuery);
