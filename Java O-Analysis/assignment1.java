/************************************************************
*	Program Title: Maximum Sum Subsequence Finder			*
*	Author: Michael Goberling								*
*	Class: CSCI-3320, Spring 2017							*
*	Assignment #1											*
*	Colleagues: William Picken - wpicken@unomaha.edu		*
*				Nicolas Lee - nglee@up.com					*
*				Daniel Goudie - djgoudie@up.com				*
*	Edited in: Notepad++ and on the UNO loki server			*
************************************************************/

import java.util.*; //just to make sure
import java.util.Scanner;
import java.util.Random; //could be bad


public class assignment1
{

	//Variables declared to be used in Algorithm 3 for comparison to Algorithm 2
    public static int maxSumCheck = 0;
    public static int maxSum = 0;
    public static int alg3Start = 0;
    public static int resultStart = 0;
    public static int alg3End = 0;
    public static int resultEnd = 0;

	/************************************************************************
	*	Function	to fill array, call algorithm methods, and time 		*
	*				computation	: assignment1								*
	*																		*
	*	Input Parameters		: number of integers (n)					*
	*	Output					: N/A										*
	*************************************************************************/
	
    public assignment1( int n )
    {
        Random rand = new Random();
        int[] data = new int[n];

        for(int i = 0; i < n; i++)
        {
            int randomNum = rand.nextInt((9999 + 9999) + 1) + -9999;
            //Formula to get random number between 9999 & -9999 back.

            data[i] = randomNum;
            //Put random number into the array.
        }

		//Following array completion, check to see if we need to print the array
		
        if(n < 50)
        {
			//Fill a string to handle print duties
            String integerList = "";

            for(int i = 0; i < n; i++)
            {
                integerList += Integer.toString(data[i]) + " ";

            }
			//print array
            System.out.println(integerList);
        }


        System.out.println();

		//Algorithm 2
        
		long startTime1 = System.nanoTime();
        //Start the timer for alg 1
        maxSumSub2(data);
        //method for algorithm 2 prints sum , start and end indices
        long endTime1 = System.nanoTime();
        long algTime1 = (endTime1 - startTime1);
		//End time for alg 1, compute total time
        System.out.println("Execution Time: " + algTime1 + " nanoseconds");
        System.out.println();

		//Algorithm 3
        
		long startTime2 = System.nanoTime();
        //Start the timer for alg 2
        maxSumRec(data, 0, data.length - 1);
        //method for algorithm 3 prints sum, start, and end indices
        long endTime2 = System.nanoTime();
        long algTime2 = (endTime2 - startTime2);
        //End timer for alg 2, compute total time
        System.out.println("Execution Time: " + algTime2 + " nanoseconds");
        System.out.println();
		
		// Algorithm 4
		
        long startTime3 = System.nanoTime();
        //Start the timer for alg 3
        maxSumSub4(data);
		//method for algorithm 4 prints sum, start and end indices
        long endTime3 = System.nanoTime();
        long algTime3 = (endTime3 - startTime3);
        System.out.println("Execution Time: " + algTime3 + " nanoseconds");
        System.out.println();

    }
   
   /*************************************************************************
	*	Function	Find the maximum sub-sequence, quadratically: maxSumSub2*
	*																		*
	*	Input Parameters		: array (data)								*
	*	Output					: Print max sum, start index, and end index	*
	*************************************************************************/

    public static int maxSumSub2( int[] a )
    {
        int maxSum = 0;
        int sIndex = 0;
        int eIndex = 0;

        //Algorithm 2 from slides

        for( int i = 0; i < a.length; i++)
        {
            int thisSum = 0;
            for(int j = i; j < a.length; j++ )
            {
                thisSum += a[j];

                if ( thisSum > maxSum )
                {
                    sIndex = i;	//Beginning of the sequence that has fulfilled the if condition
                    eIndex = j; //Ending of the sequence that has fulfilled the if condition
                    maxSum = thisSum;
                }
            }
        }

        System.out.println("Max Sum: " + maxSum + " S_Index: " + sIndex + " E_Index: " + eIndex);

        return maxSum;
    }
  
	/****************************************************************************************************
	*	Function	Find the maximum sub-sequence, by dividing and conquering, recursively: maxSumRec	*
	*																									*
	*	Input Parameters		: array (data), 0 (left start), data.length - 1							*
	*	Output					: Print max sum, start index, and end index								*
	*****************************************************************************************************/
  
    private static int maxSumRec( int []a, int left, int right)

			   {
        if(left == right)
        {
            if( a[left] > 0)
            {
                return a[left];
            }
            else
            {
                return 0;
            }
        }

        int center = ( left + right ) / 2;
        int maxLeftSum = maxSumRec(a, left, center);
        int maxRightSum = maxSumRec(a, center + 1, right);
        int maxSumCheck = 0;
        int alg3Start = 0;
        int alg3End = 0;

        int maxLeftBorderSum = 0, leftBorderSum = 0;

        for(int i = center; i >= left; i--)
        {
            leftBorderSum += a[i];
            if( leftBorderSum > maxLeftBorderSum )
            {
                alg3Start = i;
                //start of the max
                maxLeftBorderSum = leftBorderSum;
            }
        }

        int maxRightBorderSum = 0;
        int rightBorderSum = 0;
        for(int i = center + 1; i <= right; i++)
        {
            rightBorderSum += a[ i ];
            if( rightBorderSum > maxRightBorderSum )
            {
                alg3End = i;
                ///end of the max
                maxRightBorderSum = rightBorderSum;
            }
        }


        maxSumCheck = max3(maxLeftSum, maxRightSum, maxLeftBorderSum + maxRightBorderSum);

        if(maxSumCheck > maxSum) //conditon where the sum we are checking grows larger than the previous max sum
        {
            maxSum = maxSumCheck;
            resultStart = alg3Start;
            resultEnd = alg3End;

            //To verify that the max sum is not only in one index location
            if(maxSum == a[alg3End])
            {
                resultStart = alg3End;
            }
            else if(maxSum == a[alg3Start]) //Condition to verify if maxSum is only in starting index
			{
                resultEnd = alg3Start;
            }

            if(resultStart > resultEnd)
            {
                resultStart = alg3End;
                resultEnd = alg3Start;
            }
        }

        //System.out.println(left + " " + right);  for debugging


        if(left + right == a.length - 1) //Verify we've hit the last recursive run
        {
            System.out.println("Max Sum: " + maxSum + " S_Index: " + resultStart + " E_Index " + resultEnd);
        }

        return max3(maxLeftSum, maxRightSum, maxLeftBorderSum + maxRightBorderSum);

    }
    /****************************************************************************************	
	*	Function	Find the largest value of the three inputs: max3						*
	*																						*
	*	Input Parameters		: maxLeft, maxRight, maxLeftRight 							*
	*	Output					: Largest value of the three inputs							*
	*****************************************************************************************/
	
    public static int max3(int maxLeft, int maxRight, int maxLeftRight)
    {
        if(maxLeft > maxRight && maxLeft > maxLeftRight)
        {
            return maxLeft;
        }
        else
        {
            if(maxRight > maxLeft && maxRight > maxLeftRight)
            {
                return maxRight;
            }
            else
            {
                return maxLeftRight;
            }
        }
    }

	/************************************************************************************************	
	*	Function	Find the maximum subsequence sum with O(N) (algorithm 2 adjustment): maxSumSub4	*
	*																								*
	*	Input Parameters		: data (array)					 									*
	*	Output					: Print maximum sum, left index, and right index					*
	*************************************************************************************************/

    public static int maxSumSub4( int[] a )
    {
        int maxSum = 0;
        int thisSum = 0;
        int sIndex = 0;
        int eIndex = 0;
        int newStart = 0;
   
   //Algorithm 4 from slides with conditionals to handle negative beginning and ending scenarios

        for( int j = 0; j < a.length; j++)
        {
            thisSum += a[j];

            if(thisSum == a[j]) //if the index we are currently pointing to is our sum at the moment, we have started a new start index
            {
                newStart = j;
            }

            if ( thisSum > maxSum )
            {
                sIndex = newStart;
                eIndex = j; 	//Once our current sum is the new maxSum, that means we have a new ending index, until it is possibly overwritten
                maxSum = thisSum;
            }
            else if( thisSum < 0 )
            {
                thisSum = 0;
            }

        }

        System.out.println("Max Sum: " + maxSum + " S_Index: " + sIndex + " E_Index: " + eIndex);

        return maxSum;
    }


	/************************************************************************************************	
	*	Function	Method to handle user input & create an object to run constructor : main		*
	*																								*
	*	Input Parameters		: N/A							 									*
	*	Output					: N/A																*
	*************************************************************************************************/

    public static void main( String args[])
    {
        int seqSize = -1;

        Scanner input = new Scanner(System.in);
        while(seqSize < 0)
        {
            try
            {
                System.out.print("Please enter the size of the problem (N): ");
                seqSize = input.nextInt();
            }
            catch( Exception e)
            {
                System.out.println("Please enter a valid integer.");
                input.next();			//Exception handling response from StackOverflow user: Amit Deshpande 
										//http://stackoverflow.com/questions/12702076/java-try-catch-with-inputmismatchexception-creates-infinite-loop
            }
        }

        System.out.println();
        assignment1 myAssignment = new assignment1(seqSize);

    }

}
