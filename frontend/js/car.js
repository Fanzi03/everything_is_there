export function car(name, age){
	return {
		name, 
		age
	};
};

export function run(car){
	return {
		status: `car: ${JSON.stringify(car)}, is running`
	};
};

