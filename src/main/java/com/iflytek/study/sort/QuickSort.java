package com.iflytek.study.sort;

/**
 * @author : wei
 * @date : 2018/3/27
 */
public class QuickSort {

    public static void main(String[] args){
        int[] array = new int[]{10,3,5,20,89,4,0,-2,43};
        printArray(array);
        quickSort(array,0,array.length - 1);
        printArray(array);
    }

    public static void printArray(int array[]){
        for(int i = 0 ; i< array.length ; i ++){
            System.out.print(array[i] + " ,");
        }
        System.out.println("--------------------");
    }

    public static void quickSort(int array[],int firstIndex,int lastIndex){
        if(firstIndex >= lastIndex){
            return ;
        }
        int position = findPosition(array,firstIndex,lastIndex);
        System.out.println("fist position : " + position);
        quickSort(array,firstIndex,position -1);
        quickSort(array,position + 1,lastIndex);
    }

    private static int findPosition(int[] array, int firstIndex, int lastIndex) {
        if(firstIndex >= lastIndex){
            return firstIndex;
        }
        int postion = firstIndex;
        while(firstIndex < lastIndex){
            while(array[lastIndex] >= array[postion] && firstIndex < lastIndex){
                lastIndex --;
            }
            if(firstIndex < lastIndex){
                int tmp = array[lastIndex];
                array[lastIndex] = array[postion];
                array[postion] = tmp;
                postion = lastIndex;
            }
            while(array[firstIndex] <= array[postion] && firstIndex < lastIndex){
                firstIndex ++;
            }
            if(firstIndex < lastIndex){
                int tmp = array[firstIndex];
                array[firstIndex] = array[postion];
                array[postion] = tmp;
                postion = firstIndex;
            }
        }
        return postion;
    }
}
