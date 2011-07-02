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

var roborace = {
	
	jQuery : $,
	
	settings : {
		numberColumns : 24,
		numberRows : 16
	},
	
	state : {
		loggedIn : false
	},
	
	init : function() {
		
		this.checkLoginState();
		this.createBattlefield();
		this.createNavigation();
		
	},
	
	checkLoginState : function() {
		
		this.jQuery.ajax({
			url : 'rest/session',
			dataType : 'json',
			success : function(result) {
				
				this.state.loggedIn = result;
				log('result from login check', this, result);
				
				if (loggedIn) {
					$('#welcome #login').hide();
					$('#welcome #logout').show();
				} else {
					$('#welcome #logout').hide();
				}
			}
		});
		
	},
	
	createBattlefield : function() {
		
		var $ = this.jQuery;
		var columnString = '';
		var rowString = '';
		
		for (var i = 1;  i <= this.settings.numberColumns; i++) {
			columnString += '<td></td>';
		}
		for (var i = 1; i <= this.settings.numberRows; i++) {
			rowString += '<tr>' + columnString + '</tr>';
		}
		$('#battlefield').append(rowString);
		
	},
	
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
			roborace.createMap();
		});
		
	},
	
	createMap : function() {
		
		// here we would load the map data from the server
		var mapData = {
			rows : [
				{ columns : [ 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL' ] },
				{ columns : [ 'WALL', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'WALL' ] },
				{ columns : [ 'WALL', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'WALL' ] },
				{ columns : [ 'WALL', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'WALL' ] },
				{ columns : [ 'WALL', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'WALL' ] },
				{ columns : [ 'WALL', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'WALL' ] },
				{ columns : [ 'WALL', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'WALL' ] },
				{ columns : [ 'WALL', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'WALL' ] },
				{ columns : [ 'WALL', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'WALL' ] },
				{ columns : [ 'WALL', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'CONVEYOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'WALL' ] },
				{ columns : [ 'WALL', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'CONVEYOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'WALL' ] },
				{ columns : [ 'WALL', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'CONVEYOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'WALL' ] },
				{ columns : [ 'WALL', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'CONVEYOR', 'CONVEYOR', 'CONVEYOR', 'CONVEYOR', 'FLOOR', 'WALL' ] },
				{ columns : [ 'WALL', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'WALL' ] },
				{ columns : [ 'WALL', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'FLOOR', 'WALL' ] },
				{ columns : [ 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL', 'WALL' ] }
			]
		};
		
		var rowString = '';
		for (var r = 0; r < mapData.rows.length; r++) {
			var eachRow = mapData.rows[r];
			var columnString = '';
			for (var c = 0; c < eachRow.columns.length; c++) {
				var eachColumn = eachRow.columns[c];
				columnString += '<td class="' + eachColumn + '"></td>';
			}
			rowString += '<tr>' + columnString + '</tr>';
		}		
		
		$('#battlefield tbody').replaceWith(rowString);
		
	}
	
};

roborace.init();



















