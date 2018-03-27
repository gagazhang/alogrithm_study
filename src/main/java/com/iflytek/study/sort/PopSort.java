package com.iflytek.study.sort;

/**
 *
 * 冒泡排序
 * @author : wei
 * @date : 2018/3/27
 */
public class PopSort {

    public static void main(String[] args){
        int[] array = new int[] {3,5,28,34,1,0,-1};
        printArray(array);
        popSort(array);
        printArray(array);
    }

    public  static void printArray(int[] array){
        for(int i = 0 ; i < array.length; i++){
            System.out.print(array[i]);
            System.out.print(",");
        }
    }

    public static void popSort(int[] sortArray){
        for(int length = sortArray.length; length > 0 ; length --){
            for(int i= 0 ; i < (length - 1) ; i ++){
                if(sortArray[i] > sortArray[i + 1]){
                    int tmp = sortArray[i + 1];
                    sortArray[i + 1] = sortArray[i];
                    sortArray[i] = tmp;
                }
            }
        }
    }
}
