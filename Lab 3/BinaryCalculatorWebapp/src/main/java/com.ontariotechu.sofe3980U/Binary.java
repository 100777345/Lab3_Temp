package com.ontariotechu.sofe3980U;

/**
 * Unsigned integer Binary variable
 *
 */
public class Binary
{
	private String number="0";  // string containing the binary value '0' or '1'
	/**
	* A constructor that generates a binary object.
	*
	* @param number a String of the binary values. It should conatins only zeros or ones with any length and order. otherwise, the value of "0" will be stored.   Trailing zeros will be excluded and empty string will be considered as zero.
	*/
    public Binary(String number) {
		for (int i = 0; i < number.length(); i++) {
			// check each character if it's not 0 or 1
			char ch=number.charAt(i);
			if(ch!='0' && ch!='1') {
				number="0"; // if not store "0" and end the function
				return;
			}
		}
		// remove any trailing zeros
		int beg;
		for (beg = 0; beg < number.length(); beg++) {
			if (number.charAt(beg)!='0')
				break;
		}
		//beg has the index of the first non zero digit in the number
		this.number=number.substring(beg); // exclude the trailing zeros if any
		// uncomment the following code
		
		if(this.number=="") { // replace empty strings with a single zero
			this.number="0";
		}
		
    }
	/**
	* Return the binary value of the variable
	*
	* @return the binary value in a string format.
	*/
	public String getValue()
	{
		return this.number;
	}
	/**
	* Adding two binary variables. For more information, visit <a href="https://www.wikihow.com/Add-Binary-Numbers"> Add-Binary-Numbers </a>.
	*
	* @param num1 The first addend object
	* @param num2 The second addend object
	* @return A binary variable with a value of <i>num1+num2</i>.
	*/
	public static Binary add(Binary num1,Binary num2)
	{
		// the index of the first digit of each number
		int ind1=num1.number.length()-1;
		int ind2=num2.number.length()-1;
		//initial variable
		int carry=0;
		String num3="";  // the binary value of the sum
		while(ind1>=0 ||  ind2>=0 || carry!=0) // loop until all digits are processed
		{
			int sum=carry; // previous carry
			if(ind1>=0){ // if num1 has a digit to add
				sum += (num1.number.charAt(ind1)=='1')? 1:0; // convert the digit to int and add it to sum
				ind1--; // update ind1
			}
			if(ind2>=0){ // if num2 has a digit to add
				sum += (num2.number.charAt(ind2)=='1')? 1:0; // convert the digit to int and add it to sum
				ind2--; //update ind2
			}
			carry=sum/2; // the new carry
			sum=sum%2;  // the resultant digit
			num3 =( (sum==0)? "0":"1")+num3; //convert sum to string and append it to num3
		}
		Binary result=new Binary(num3);  // create a binary object with the calculated value.
		return result;
		
	}
	/**
	* Performs bitwise OR on two binary variables.
	*
	* @param num1 The first Binary object
	* @param num2 The second Binary object
	* @return A binary variable with a value of the result of num1 OR num2.
	*/
	public static Binary or(Binary num1,Binary num2) 
	{
		Binary longerNum, shorterNum;
		String tempResult = ""; // Stores results of OR thus far
		
		// Determine longer/shorter number. OR operation stops at length of shorter number, and remaining digits are automatically taken from longer number.
		if (num1.number.length() >= num2.number.length()) {
			longerNum = num1;
			shorterNum = num2;
		} else {
			longerNum = num2;
			shorterNum = num1;
		}
		
		// Bitwise OR from right side, stops when all of shorter number has been processed
		for (int i = 0; i < shorterNum.number.length(); i++)
		{
			if ((num1.number.charAt((num1.number.length()-1-i)) == '0') && (num2.number.charAt((num2.number.length()-1-i)) == '0')) {
				tempResult = "0" + tempResult;
			} else {
				tempResult = "1" + tempResult;
			}
			
		}
		
		// Remaining digits are taken from the longer number
		for (int i = (longerNum.number.length() - shorterNum.number.length() - 1); i >= 0; i--)
		{
			tempResult = longerNum.number.charAt(i) + tempResult;
		}
		
		// Prepare and return final result
		Binary result = new Binary(tempResult);
		return result;
	}
	/**
	* OR two binary variables.
	*
	* @param num1 The first object
	* @param num2 The second object
	* @return A binary variable with a value of <i>num1 || num2</i>.
	*/
	public static Binary orAlt(Binary num1,Binary num2)
	{
		// the index of the first digit of each number
		int ind1=num1.number.length()-1;
		int ind2=num2.number.length()-1;
		//initial variable
		String num3="";  // the binary value of the result
		while(ind1>=0 ||  ind2>=0) // loop until all digits are processed
		{
			int tempR=0; // start with 0
			if(ind1>=0){ // if num1 has a digit to add
				tempR += (num1.number.charAt(ind1)=='1')? 1:0; // convert digit to int and add to tempR
				ind1--; // update ind1
			}
			if(ind2>=0){ // if num2 has a digit to add
				tempR += (num2.number.charAt(ind2)=='1')? 1:0; // convert digit to int and add to tempR
				ind2--; //update ind2
			}
			num3 =( (tempR ==0)? "0":"1")+num3; //convert tempR to string and append it to num3
		}
		Binary result=new Binary(num3);  // create a binary object with the calculated value.
		return result;
	}
	/**
	* Performs bitwise AND on two binary variables.
	*
	* @param num1 The first Binary object
	* @param num2 The second Binary object
	* @return A binary variable with a value of the result of num1 AND num2.
	*/
	public static Binary and(Binary num1,Binary num2) 
	{
		Binary shorterNum;
		String tempResult = ""; // Stores results of AND thus far
		
		// Determine shorter number, allowing the function to stop when operation is no longer necessary
		if (num1.number.length() >= num2.number.length()) {
			shorterNum = num2;
		} else {
			shorterNum = num1;
		}
		
		// Bitwise AND, from right side
		for (int i = 0; i < shorterNum.number.length(); i++)
		{
			if ((num1.number.charAt((num1.number.length()-1-i)) == '1') && (num2.number.charAt((num2.number.length()-1-i)) == '1'))
			{
				tempResult = "1" + tempResult; // If digit is 1 in both num1 and num2, result is 1
			} else {
				tempResult = "0" + tempResult; // else 0
			}
		}

		// Prepare and return final result
		Binary result = new Binary(tempResult);
		return result;
	}
	/**
	* Performs multiplication between two binary variables.
	*
	* @param num1 The first factor, is a Binary object
	* @param num2 The second factor, is a Binary object
	* @return A binary variable with a value of <i>num1*num2</i>.
	*/
	public static Binary multiply(Binary num1,Binary num2) 
	{
		
		Binary longerNum, shorterNum;
		Binary tempResult = new Binary("0"); // Stores sum of products/innerTempResult
		String innerTempResult = ""; // Temporarily stores product of longerNum and currently processing digit of shorterNum
		Binary innerTempBinary = new Binary (innerTempResult); // Binary form of innerTempResult
		
		// Determine longer/shorter number. Longer number is multiplicand, shorter number is multiplier.
		if (num1.number.length() >= num2.number.length()) {
			longerNum = num1;
			shorterNum = num2;
		} else {
			longerNum = num2;
			shorterNum = num1;
		}
		
		// Multiplication
		for (int i = 0; i < shorterNum.number.length(); i++)
		{
			if ((shorterNum.number.charAt(shorterNum.number.length()-1-i)) == '1') // If currently processing digit of multiplier is 1
			{
				for (int j = 0; j < i; j++)
				{
					innerTempResult = "0" + innerTempResult; // Padding innerTempResult with proper number of zeroes to the right
				}
				
				innerTempResult = longerNum.number + innerTempResult;
			} else {
				innerTempResult = "0"; // If currently processing digit of multiplier is 0, product is automatically 0
			}
		
			innerTempBinary.number = innerTempResult; // Convert innerTempResult to binary
			tempResult = Binary.add(innerTempBinary, tempResult); // Perform binary addition with results so far
			innerTempResult = ""; // Reset innerTempResult
			
		}
		
		// Prepare and return final result
		Binary result = new Binary(tempResult.number);
		return result;
	}
	
}	
