/*************************************
 *Program: D-Heap                    *
 *Author: Michael Goberling          *
 *Class: CSCI3320, Fall 2017         *
 *Assignment #2                      *
 ************************************/

/***********************************************************
 *Colleagues:   Jacob Curtis - jacobcurtis@unomaha.edu     *
 *              Riley Hester - rhester@unomaha.edu         *
 *              Will Picken  - wpicken@unomaha.edu         *
 ***********************************************************/

/*************************************
 *Material: Figure 6.4 Weiss         *
 *************************************/

import java.util.Scanner;
import java.util.*;
import java.util.Arrays;

public class DHeap
{

    /******************************************************
     *Function Constructor:                               *
     *    Fills the array that makes up the heap          *
     *Input Parameters:                                   *
     *    items                                           *
     *Output:                                             *
     *    None                                            *
     *****************************************************/

    public DHeap( String[] items )
    {
        currentSize = items.length;
        array = new int[ currentSize + 1 ];

        int i = 1;
        for( String item : items )
        {
            array[ i++ ] = Integer.parseInt(item);
        }
        buildHeap( );
    }

    /*****************************************************
     *Function Insert:                                    *
     *    Inserts x into the heap, need to buildheap()    *
     *    after                                           *
     *Input Parameters:                                   *
     *    int x - value to insert                         *
     *Output:                                             *
     *    None                                            *
     *****************************************************/

    public void insert( int x )
    {
        if( isFull() )
        {
            throw new RuntimeException("Overflow Exception.");
        }

        if( currentSize == array.length - 1 )
        {
            enlargeArray( array.length * 2 + 1 );
        }

        // Percolate up
        array[currentSize + 1] = x;
        currentSize++;

        //for( array[ 0 ] = x; x.compareTo( array[ hole / 2 ] ) < 0; hole /= 2 )
        //for( array[0] = x; x < array[hole/D]; hole /= 2)
        //array[ hole ] = array[ hole / 2 ];
        //array[ hole ] = x;
    }

    /******************************************************
     *Function buildHeap:                                 *
     *    Orders the array based on value d               *
     *Input Parameters:                                   *
     *    None                                            *
     *Output:                                             *
     *    None                                            *
     *****************************************************/

    private void buildHeap( )
    {
        //Percolate the heap down based on d
        for(int i = ((currentSize / d) + (d - 2)); i > 0; i--)
        {
            percolateDown( i );
        }
    }

    /******************************************************
     *Function deleteMin:                                 *
     *    Deletes the smallest value in the heap          *
     *Input Parameters:                                   *
     *    None                                            *
     *Output:                                             *
     *    None                                            *
     *****************************************************/

    public void deleteMin( )
    {
        if( isEmpty( ) )
        {
            throw new RuntimeException("Underflow Exception.");
        }

        int minItem = findMin( );
        array [ 1 ] = array[currentSize];
        currentSize--;
        percolateDown( 1 );

    }

    /******************************************************
     *Function isEmpty:                                   *
     *    Checks if the array is empty                    *
     *Input Parameters:                                   *
     *    None                                            *
     *Output:                                             *
     *    boolean - true if empty, false otherwise        *
     *****************************************************/ 

    public boolean isEmpty( )
    {
        return currentSize == 0;
    }

    /******************************************************
     *Function isFull:                                    *
     *    Checks if the array is full                     *
     *Input Parameters:                                   *
     *    None                                            *
     *Output:                                             *
     *    boolean - true if full, false if otherwise      *
     *****************************************************/

    public boolean isFull()
    {
        return currentSize == array.length;
    }

    /******************************************************
     *Function findMin:                                   *
     *    Finds the smallest value in the heap            *
     *Input Parameters:                                   *
     *    None                                            *
     *Output:                                             *
     *    int - Really just returns array index [1]       *
     *****************************************************/

    public int findMin( ) 
    {
        if( isEmpty( ) )
        {
            throw new RuntimeException( );
        }
        return array[ 1 ];
    }

    /******************************************************
     *Function enlargeArray:                              *
     *    Copies the old array to a new one at a new size *
     *Input Parameters:                                   *
     *    int newSize - new size of the array             *
     *Output:                                             *
     *    None                                            *
     *****************************************************/

    private void enlargeArray( int newSize )
    {
        int[] old = array;
        array = new int[newSize];
        for(int i = 0; i < old.length; i++)
        {
            array[i] = old[i];
        }
    }

    /******************************************************
     *Function percolateDown:                             *
     *    Percolate down the heap to main order           *
     *Input Parameters:                                   *
     *    int hole - the first parent to look at          *
     *Output:                                             *
     *    None                                            *
     *****************************************************/

    private void percolateDown(int hole)
    {
        int child;
        int tmp = array[ hole ];
        for(; ((hole *d) - (d - 2)) <= currentSize; hole = child)
        {
            //counter to tell which child  you are comparing
            int x = 1;

            //counter to make sure d-amount of children are compared
            int counter = 1;

            //start at the first index under the parent
            child = ((hole * d) - (d - 2));

            for( ; counter < d; counter++)
            {
                if(child + (x - 1) == currentSize)
                {
                    break;
                }
                //check if the next child is smaller than the current child
                else if(child != currentSize && (array[child + x] < array[child]))
                {
                    //if it is, move to that child
                    child += x;
                }
                else
                {
                    //otherwise, compare the next child
                    x++;
                }
            }
            //check if the current hole is greater than its child
            if(tmp > array[child])
            {
                //if it is, then swap them
                array[hole] = array[child];
            }
            else
            {
                break;
            }
        }
        array[hole] = tmp;
    }

    /******************************************************
     *Function printArray:                                *
     *    iterates through the array to print it          *
     *Input Parameters:                                   *
     *    None                                            *
     *Output:                                             *
     *    None                                            *
     *****************************************************/

    public void printArray()
    {
        for(int i = 1; i <= currentSize; i++)
        {
            System.out.print(array[i]);
            System.out.print(" ");
        }
        System.out.println();
    }

    //size of the heap
    private int currentSize;
    //array to store the heap in
    private int[] array;
    //d-value used to decide how to order the heap
    private static int d;
    static DHeap h;

    public static void main(String[] args)
    {
        Scanner get = new Scanner(System.in);

        int choice = 0;
        int tempInsert = 0;
        boolean dCheck;
        boolean stringCheck = true;

        //try to split the elements into strings and then integers
        //try to take in the d value as an int
        do
        {
            try
            {
                System.out.println();
                System.out.print("Enter heap elements: ");        
                String input = get.nextLine();
                //Split the string
                String[] tempString = input.split(" ");
                dCheck = true;
                do
                {
                    try
                    {
                        System.out.print("Enter d: ");
                        d = get.nextInt();
                        dCheck = false; 
                    }
                    catch(InputMismatchException ime)
                    {
                        //get.nextLine();
                        System.out.println("Please enter a number for d... ");
                        get.next();
                    }

                }while(dCheck);

                h = new DHeap(tempString);
                stringCheck = false;
            }
            catch(NumberFormatException nfe)
            {
                System.out.print("Please enter appropriate numerial values...");
                get.nextLine();
            }
        }while(stringCheck);

        //Create the heap object
        //DHeap h = new DHeap(tempString);

        //while option 4 has not been chosen, continue to do stuff

        //anytime an invalid entry is recieved, the prompt will restart
        //and no changes will be made to the heap, sort of like an invalid
        //entry to a website

        while(choice != 4)
        {
            tempInsert = 0;

            System.out.printf("Output(d = %d): ", d);
            h.printArray();
            System.out.println();

            /*Needed to do inside the try-catch*/

            //System.out.println("Press 1) for insert, 2) for deleteMin, 3) for new d value, 4) to quit");
            //System.out.print("Enter choice: ");
            //choice = get.nextInt();

            try
            {
                System.out.println("Press 1) for insert, 2) for deleteMin, 3) for new d value, 4) to quit");
                System.out.print("Enter choice: ");
                choice = get.nextInt();

                //Insert
                if(choice == 1)
                {
                    System.out.print("Enter element to insert: ");
                    tempInsert = get.nextInt();
                    h.insert(tempInsert);
                    h.buildHeap();
                }
                //Delete Smallest Element
                if(choice == 2)
                {
                    h.deleteMin();
                    h.buildHeap();
                }
                //Reorder heap with new d-value
                if(choice == 3)
                {
                    System.out.print("Enter d: ");
                    d = get.nextInt();
                    h.buildHeap();
                }

                if(choice != 1 && choice != 2 && choice != 3 && choice != 4)
                {
                    System.out.println("Invalid entry recieved. Please choose again.");
                    System.out.println();
                }
            }
            catch(Exception E)
            {
                get.nextLine();
                System.out.println("Invalid entry recieved. Please choose again.");
                System.out.println();
            }
        }
        System.out.println("Program terminated");
    }

}
