package org.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BinaryTest {

	@Test	
	void checkBinary(){
		var a = 0b1;
		var b = 0b0;

		var or = a | b; // 0b1
		var xor = a ^ b; // 0b1
		var and = a & b; // 0b0
		var notA = ~a; 

		// check opertions | ^ & ~
		System.out.println("{"+ a + "}," + "{"+ b + "}");
		assertThat(a).isEqualTo(or).isEqualTo(xor).isNotEqualTo(and).isNotEqualTo(notA);

		//check even
		System.out.println("{a = "+ a + "}");
		assertThat(isEven(a)).isFalse();

		assertThat(isEven(0b10)).isTrue();


		//check hashFunction
		int key = 0b111;
		String str = "Hello world";
		String strHash = hashFunction(str, key);
		String antiStringHash = hashFunction(strHash, key);

		System.out.println("{key: " + key + "}," + "{str:" + str + "}");

		System.out.println("{" + strHash + "}");


		System.out.println("{" + hashFunction(strHash,key) + "}");

		assertThat(str).isEqualTo(antiStringHash);

		System.out.println(-100 >> -1);
		System.out.println(Integer.bitCount(10)); //0b1010 -> ans = 2
		System.out.println(bitCount(10));
		assertThat(bitCount(10)).isEqualTo(Integer.bitCount(10));

		System.out.println(0xCC);

		int x,x1,x2;
		x = x1 = x2 = 2 + 2;
		System.out.println(x1 + x2 + x);

		System.out.println(++x);
	}

	public boolean isEven(int n){
		// n = 2 -> 0b10 & 0b01 = 0b00 - even
		// n = 3 -> 0b11 & 0b01 = 0b01 - not even
		var a = n & 1;
		System.out.println("{n & 1 = " + a + "}");
		return (n & 1) == 0;
	}

	public String hashFunction(String str, int key){
		// str = 2 -> 0b010 ^ 0b111 = 101 
		char[] array = str.toCharArray();

		for(int i = 0; i < array.length; i++){
			array[i] ^= key; 
		}

		return new String(array);
	}
	public int bitCount(int n){
		//int n = 123 -> 0b1111011
		int count = 0;

		while (n != 0) {
			n &= (n - 1); // убираем младший установленный бит
			// 0b1111011 = 0b1111011 & 0b1111011 - 0b0000001
			// 0b1111011 = 0b1111011 & 0b1111010 
			// 0b1111011 = 0b1111010
			// 122  то есть когда мы отнимаем один и амперсируем то отнимаем один бит ? 
			count++;
		}
		return count;
	}

	public static void main(String[] args) {
	}










}
