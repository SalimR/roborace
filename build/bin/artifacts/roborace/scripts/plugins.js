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

// usage: log('inside coolFunc', this, arguments);
window.log = function() {
	log.history = log.history || [];   // store logs to an array for reference
	log.history.push(arguments);

	//noinspection CallerJS
	arguments.callee = arguments.callee.caller;

	if (this.console) {
		console.log(Array.prototype.slice.call(arguments));
	}
};

// make it safe to use console.log always
(function(b) {

	function c() {
	}

	for (var d = "assert,count,debug,dir,dirxml,error,exception,group,groupCollapsed,groupEnd,info,log,markTimeline,profile,profileEnd,time,timeEnd,trace,warn".split(","),a; a = d.pop();) {
		b[a] = b[a] || c
	}
	
})(window.console = window.console || {});


// place any jQuery/helper plugins in here, instead of separate, slower script files.

