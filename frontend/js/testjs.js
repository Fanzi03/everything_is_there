describe("sqrt", function() {

	it("raises to n-th sqrt", function(){
		assert.equal(sqrt(100), 10);
	});

	it("raises to n-th sqrt with minus variables", function(){
		for(let i = -1000; i < 0; i++)
		assert.ok(Number.isNaN(sqrt(i)));
	});

});
