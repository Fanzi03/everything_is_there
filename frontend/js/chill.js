'use strict';

import {car, run} from "./car.js";

function checkEven(n){
	return (n & 1) === 0;
}

function pow(a, b){
	return a ** b;
}

function show100hello(){ for(let i = 1; i <= 100; i++){
		console.log("Hello" + i);
	}
}
let isEmpty = (objects) => {

	for(let object in objects){
		if(objects[object] !== undefined) return false;
	}
	return true;
}; 

let multiplyNumeric = (obj) => {
	for(let key in obj){
		if(typeof key !== "string")
		obj[key] *= 2;
	}
	return obj;
};

let sum = (obj) => {
	let sum = 0;
	for(let key in obj){
		sum += obj[key];
	}
	return sum;
};

let car1 = car("tesla", 12);
let car2 = structuredClone(car1);

console.log(JSON.stringify(car2));
