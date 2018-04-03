package com.iflytek.study.sort;

import javafx.scene.SubScene;

import java.util.Arrays;

/**
 * @author : wei
 * @date : 2018/4/3
 */
public class HeapSort {

    public static void main(String[] args){
        int[] attr = {4,6,8,5,9};
        System.out.println(Arrays.toString(attr));
        heapSort(attr,attr.length);
        System.out.println(Arrays.toString(attr));
    }

    public static void heapSort(int[] array, int length){
        if(length == 1){
            return ;
        }
        //构建大顶堆
        for(int i = length /2 -1; i >= 0 ; i --){
            heapAdjust(array,i,array.length);
        }

        //上一步已经建立了大顶堆，每次去调整就可以了
        for(int j = length -1; j >= 0 ; j --){
            swap(array,0,j);        //把每次最大的元素交换，找出最大的元素
            heapAdjust(array,0,j);      //重新堆调整
        }

    }

    /**
     * 大顶堆的调整算法
     * @param array
     * @param i
     * @param length
     */
    public static void heapAdjust(int[] array,int i, int length){
        for(int k = 2*i + 1 ; k < length ; k = k * 2 + 1){
            //比较i 的左子树和右子树，取较大的那一个
            if(array[k] < array[k+1] && k + 1 < length){
                k = k + 1;
            }
            //如果子节点 > 当前节点，则交换
            if(array[i] < array[k]){
                swap(array,i,k);
            }
        }
    }

    /**
     * 交换数组中两个下标的数
     * @param array
     * @param aIndex
     * @param bIndex
     */
    public static void swap(int[] array,int aIndex, int bIndex){
        int tmp = array[aIndex];
        array[aIndex] = array[bIndex];
        array[bIndex] = tmp;
    }
}
