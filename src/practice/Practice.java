package practice;

import java.util.*;

public class Practice {
    public static void main(String[] args) {
        Practice p = new Practice();
        System.out.println(p.firstUniqChar("aabb"));
    }

    public int firstUniqChar(String s) {
        Map<Character, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < s.length(); i++) {
            Integer val = map.get(s.charAt(i));
            if (val != null)
                map.put(s.charAt(i), -1);
            else
                map.put(s.charAt(i), i);
        }
        return map.keySet().stream().map(key -> map.get(key)).filter(val -> val >= 0)
                .findFirst().orElse(-1);

        /*int freq [] = new int[26];
        for(int i = 0; i < s.length(); i ++)
            freq [s.charAt(i) - 'a'] ++;
        for(int i = 0; i < s.length(); i ++)
            if(freq [s.charAt(i) - 'a'] == 1)
                return i;
        return -1;*/
    }

    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>(s.length(), 1.0f);
        int i = 0, j = 0, ans = 0;
        for (; i < s.length(); i++) {
            Integer index = map.get(s.charAt(i));
            if (index != null)
                j = Math.max(j, index + 1);
            map.put(s.charAt(i), i);
            ans = Math.max(ans, i - j + 1);
        }
        return ans;
    }

    public boolean checkInclusion(String s1, String s2) {
        int len1 = s1.length(), len2 = s2.length();
        if (len1 > len2) return false;
        int[] count = new int[26];
        for (char c : s1.toCharArray()) {
            count[c - 'a']++;
        }
        int i = 0, j = 0, counter = len1;
        while (j < len1) {
            if (count[s2.charAt(j++) - 'a']-- > 0) {
                counter--;
            }
        }
        while (j < len2 && counter != 0) {
            if (count[s2.charAt(i++) - 'a']++ >= 0)
                counter++;
            if (count[s2.charAt(j++) - 'a']-- > 0)
                counter--;
        }
        return counter == 0;
    }

    public boolean checkInclusion2(String s1, String s2) {
        int[] map = new int[26];
        for (char c : s1.toCharArray())
            map[c - 'a']++;
        int j = 0, i = 0;
        int count_chars = s1.length();
        while (j < s2.length()) {
            if (map[s2.charAt(j++) - 'a']-- > 0)
                count_chars--;
            if (count_chars == 0)
                return true;
            if (j - i == s1.length() && map[s2.charAt(i++) - 'a']++ >= 0)
                count_chars++;
        }
        return false;
    }

    public List<List<Integer>> generate(int numRows) {

        List<List<Integer>> list = new ArrayList<>(numRows);
        List<Integer> in = new ArrayList<>(1);
        in.add(1);
        list.add(in);
        if (numRows == 1)
            return list;
        for (int i = 1; i < numRows; i++) {
            in = list.get(i - 1);
            List<Integer> ll = new ArrayList<>(i + 1);
            for (int j = 0; j < i + 1; j++) {
                if (j == 0)
                    ll.add(in.get(j));
                else if (j == in.size())
                    ll.add(in.get(j - 1));
                else
                    ll.add(in.get(j) + in.get(j - 1));
            }
            list.add(ll);
        }
        return list;
    }

    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (nums[m] == target)
                return m;
            if (nums[m] > target)
                r = m - 1;
            else
                l = m + 1;
        }
        return -1;
    }

    //new int[]{-12, -11, 3, 5, 9, 10}
    public int[] sortedSquares(int[] nums) {
        int k;
        int i = 0, j = k = nums.length - 1;
        int[] res = new int[nums.length];
        while (i <= j) {
            int is = nums[i] * nums[i];
            int js = nums[j] * nums[j];
            if (is > js) {
                res[k--] = is;
                i++;
            } else {
                res[k--] = js;
                j--;
            }
        }
        return res;
    }

    public void rotate(int[] nums, int k) {
        k = k % nums.length;
        int l = 0, r = nums.length - 1;
        //reverse the array
        reverse(nums, l, r);
        //reverse start to k
        reverse(nums, l, k - 1);
        //reverse k to end
        reverse(nums, k, r);
    }

    private static void reverse(int[] nums, int l, int r) {
        while (l < r) {
            int v = nums[l];
            nums[l++] = nums[r];
            nums[r--] = v;
        }
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1, j = n - 1, k = m + n - 1;
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j])
                nums1[k--] = nums1[i--];
            else
                nums1[k--] = nums2[j--];

        }
        while (j >= 0) {
            nums1[k--] = nums2[j--];
        }
    }

    public void moveZeroes(int[] nums) {
        int i = 0, j = 1;
        while (j < nums.length) {
            if (nums[i] == 0 && nums[j] != 0) {
                nums[i++] = nums[j];
                nums[j++] = 0;
            } else if (nums[i] == 0 && nums[j] == 0) {
                j++;
            } else {
                i++;
                j++;
            }
        }
    }

    public int[] twoSum(int[] numbers, int target) {
        int start = 0, end = numbers.length - 1;
        while (start < end) {
            if (numbers[start] + numbers[end] == target) break;
            if (numbers[start] + numbers[end] < target) start++;
            else end--;
        }
        return new int[]{start + 1, end + 1};
    }

    public int[] intersect(int[] nums1, int[] nums2) {
        if (nums2.length > nums1.length)
            intersect(nums2, nums1);
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums1) {
            Integer orDefault = map.getOrDefault(n, 0);
            map.put(n, orDefault + 1);
        }

        List<Integer> list = new ArrayList<>();

        for (int n : nums2) {
            Integer integer = map.get(n);
            if (integer != null && integer > 0) {
                list.add(n);
                map.put(n, integer - 1);
            }

        }
        int res[] = new int[list.size()];
        int i = 0;
        for (int v : list) {
            res[i++] = v;
        }
        return res;
    }

    public int maxProfit(int[] prices) {
        //[7,1,5,3,6,4]
        int max = 0;
        int min = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] - min > max)
                max = prices[i] - min;
            if (prices[i] < min)
                min = prices[i];
        }
        return max;
    }
}
