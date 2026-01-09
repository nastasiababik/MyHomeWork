package com.example;

import leetcode.Solution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");

        int[] nums = {1, 2, 3};
 //       moveZeroes(nums);

        String s = "str";
        String t = "tsr";

        System.out.println(plusOne(nums));



    }

    public static void moveZeroes(int[] nums) {
        int nonZeroIndex = 0;
        int replace = 0;

        for(int i=0; i<nums.length; i++){
            if(nums[i]!=0){
                replace = nums[i];
                nums[i]=nums[nonZeroIndex];
                nums[nonZeroIndex]=replace;
                nonZeroIndex++;
            }
            System.out.println(i + ":" + Arrays.toString(nums));
        }

    }

    public static boolean containsDuplicate(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        int i = 0;
        while(i<nums.length){

            if(!set.add(nums[i])) return true;
            i++;
        }
        return false;
    }

    public static int singleNumber(int[] nums) {
        int result = 0;
        int i = 0;
        while(i<nums.length){
            result = result ^ nums[i];
            i++;


        }
        return result;
    }

    public static int[] plusOne(int[] digits) {
        //2 варианта последнее число меньше 9 (не нужно плюсовать предыдущее) и равно 9 -- нужно плюсовать предыдущее
        // и например {9, 9, 9} 9 +1 -> { 1,0,0,0}

        for(int i=digits.length-1; i >=0; i--){

            if(digits[i] <9){
                digits[i]++;
                return digits;
            } else digits[i]=0;
        }

        int[] digitsNew = new int[digits.length+1];
        digitsNew[0] = 1;

        /*
        for(int i=0; i<digitsNew.length; i++){
            digitsNew[i+1] = digits[i];
        }
         */


        return digitsNew;
    }

    public static boolean isAnagram(String s, String t) {
        //сравнить длину строк, если не совпадает то сразу вернуть false
        //заполнить HashMap ключ символ, а значение сколько раз встречается в первой строке
        //в цикле сравнить символ из строки2 с мапой:
        //* если символ есть то уменьшаем значение в мапе
        //* если символа нет то или его счетчик обнулился то вернуть false

        if(s.length() != t.length()){
            return false;
        }

        HashMap<Character, Integer> mapS = new HashMap<>();

        for(char c: s.toCharArray()){
            mapS.put(c, mapS.getOrDefault(c, 0)+1);
        }

        for(char c: t.toCharArray()){
            if(!mapS.containsKey(c) || mapS.get(c) <= 0) {
                return false;
            }
            mapS.put(c, mapS.get(c)-1);
        }
        return true;
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        //проверить равны ли 2 элемента
        //затем сравнить разность индексов по модулю у дубликатов
        //обновлять индекс элемента при каждом нахождении дубликата
        HashMap<Integer,Integer> map = new HashMap<>();
        //ключ - значение числа, значение -- его индекс

        for(int i =0; i<nums.length; i++){
            if(map.containsKey(nums[i])){
                int j = map.get(nums[i]);
                if(Math.abs(j-i)<=k){
                    return true;
                }
            }
            map.put(nums[i], i);
        }
        return false;
    }

    public void rotate(int[] nums, int k) {
        //создать массив такой же длинны
        //вычислить позицию после ротации в новом массиве

        int[] arr = new int[nums.length];
        int j = 0;

        for(int i =0; i<nums.length; i++){
            j = (i+k) % nums.length;
            if(j==0) return;
            arr[j] = nums[i];
        }

        System.arraycopy(arr, 0, nums, 0, nums.length);

    }

    public void reverseString(char[] s) {
        int left = 0;
        int right = s.length-1;
        char c;

        while(left<right){
            c = s[left];
            s[left]= s[right];
            s[right] = c;

            left++;
            right--;
        }

    }



}