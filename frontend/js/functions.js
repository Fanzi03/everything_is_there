"use strict";
let random = (min, max) => {
	return min - Math.random() + (max - min);
};

let ucFirst = (str) => {
	if(!str) return str;
	return str[0].toUpperCase() + str.slice(1);
};

let checkSpam = (str) => {
	if(!str) return str;

	str = str.toLowerCase();
	return str.includes("viagra") || str.includes("xxx");
};

let truncate = (str, maxLength) => {
	if(!str) return str;
	return (str.length > maxLength) ? str.slice(0, maxLength - 1) + "…" : str;	
};

let extractCurrencyValue = (str) => {
	if(!str) return str;
	return Number(str.replace(/[^\d.-]/g, ""));
};

let camelize = (str) => {
	if(!str) return str;

	return str.split("-").map(
		(word, index) => index == 0 ? word : word[0].toUpperCase() + word.slice(1)
	).join('');

};

let filterRange = (arr, min, max) => {
	return arr.filter(value => (min <= value) && (value <= max));
};

let filterRangeInPlace = (arr, min, max) => {
	for(let i = 0; i < arr.length; i++){
		if(arr[i] > max || arr[i] < min){
			arr.splice(i,1);
			i--;
		}
	}
	return arr;

};

let sorting = (arr, isNorm) => {
	if(isNorm){
		return arr.sort((a,b) => {
			if(a > b) return 1;
			if(a == b) return 0;
			if(a < b) return -1;
		});
	}	

	return arr.sort((a,b) => {
		if(a > b) return -1;
		if(a ==b) return 0;
		if(a < b) return 1;
	});
};

let copySorted = (arr) => {
	return Array.from(arr).sort((a,b) => a.localeCompare(b));
};

function Calculator() {
	this.methods ={
		"-" : (a,b) => a - b,
		"+" : (a,b) => a + b,

	}

	this.addMethod = (str_operation, fun) =>{
		this.methods[str_operation] = fun;
	};

	this.calculate = (str) => {
		let array = str.split(" "); 
		let operation = array[1];
		let a = +array[0], b = +array[2];

		if(!this.methods[operation] || isNaN(a) || isNaN(b)){
			return NaN;
		}
		return this.methods[operation](a,b);
	};
}

let getNamesArrayFromObjectsArray = (arr) => {
	/*
	let arrNames = [];
	for(let i = 0; i < arr.length; i++){
		arrNames.push(arr[i].name);	
	}
	return arrNames;
	*/

	// functional
	return arr.map(obj => obj.name);
};

let makeFullNamesFromArray = (arr) =>{
	/*
	let usersMapped = [];
	for(let i = 0; i < arr.length; i++){
		let anyUser = arr[i];
		usersMapped.push({
			fullName : `${anyUser.name} ${anyUser.surname}`,
			id : anyUser.id
		});
	}
	return usersMapped;
	*/

	return arr.map(obj => 
		({
			fullName : `${obj.name} ${obj.surname}`,
			id : obj.id
		})
	);
};

let sortByAge = (arr) => {
	return arr.sort((a, b) => a.age - b.age);
};

let shuffle = (arr) => {
	return arr.sort( () => Math.random() - 0.5);
};

let getAverageAge = (arr) => {
	/*
	let sumAge = 0;
	for(let i = 0; i < arr.length; i++){
		sumAge += arr[i].age; 	
	}

	return sumAge / arr.length;
	*/

	return arr.reduce((prev, user) => prev + user.age, 0) / arr.length;
};

let unique = (arr)  => {
	return Array.from(new Set(arr));
};

let groupById = (arr) => {
	return arr.reduce((obj,value) =>{ 
			obj[value.id] = value;
			return obj;
		}, {}
	);
};

let sumSalaries = (obj) => {
	return Object.values(obj).reduce((sum, current) => sum + current, 0);
};

let sumProperties = (obj) => {
	return Object.keys(obj).length; 
};

let topSalary = (obj = {}) => {
	let richName = null, richSalary = 0;

	for(const [name, salary] of Object.entries(obj)){
		if(richSalary < salary){
			richName = name;
			richSalary = salary;
		}
	}
	return richName;
};

let getWeekDay = (date) => {
	let days = ['SU','MO', 'TU', 'WE', 'TH', 'FR', 'SA'];
	return days[date.getDay()];
}

let getEuropeanDay = (date) => {
	return date.getDay()-1;
};

let getSecondsToday = () => {
	let nowTime = new Date(); 
	let startTime = new Date(-nowTime.getHours());

	return nowTime - startTime;
}


let room = {
  number: 23
};

let meetup = {
  title: "Conference",
  occupiedBy: [{name: "John"}, {name: "Alice"}],
  place: room
};

// circular references
room.occupiedBy = meetup;
meetup.self = meetup;

console.log( JSON.stringify(meetup, function replacer(key, value) {
	return (key !="" && value == meetup) ? undefined : value;
}));

/* result should be:
{
  "title":"Conference",
  "occupiedBy":[{"name":"John"},{"name":"Alice"}],
  "place":{"number":23}
}
*/

















