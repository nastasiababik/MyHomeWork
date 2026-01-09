package leetcode;

import java.util.HashMap;
import java.util.HashSet;

public class Solution {

        public int[] twoSum(int[] nums, int target) {
            HashMap<Integer, Integer> map = new HashMap<>();

            for(int i = 0; i < nums.length; i++){
                int num1 = nums[i];
                int num2 = target - num1;

                if(map.containsKey(num2)){
                    return new int[] {map.get(num2), i};
                }
                map.put(num1, i);

            }
            throw new IllegalArgumentException("Not found solution");
        }

    public boolean isPalindrome(int x) {
        if (x < 0)
            return false;
        if (x >= 10) {
            String str = Integer.toString(x);

            for (int i = 0; i < str.length() / 2; i++) {
                char ch1 = str.charAt(i);
                char ch2 = str.charAt(str.length() - 1 - i);

                if (ch1 != ch2)
                    return false;

            }
        }
        return true;
    }


    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        int i = 0;


        while(i<nums.length){
            set.add(nums[i]);
            i++;
        }

        for(int j = 0; j<nums.length; j++){
            if(set.contains(nums[j])) return true;
        }

        return false;

    }

}
