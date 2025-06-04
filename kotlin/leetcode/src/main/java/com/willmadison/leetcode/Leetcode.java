package com.willmadison.leetcode;

import java.util.*;
import java.util.stream.IntStream;

public class Leetcode {

    public static void main(String[] args) {
        System.out.println("Hello, Leetcode!");
    }

    public List<Integer> findWordsContaining(String[] words, char x) {
        return IntStream.range(0, words.length)
                .filter(i -> words[i].indexOf(x) >= 0)
                .boxed()
                .toList();
    }

    public int longestPalindrome(String[] words) {
        Map<String, Integer> count = new HashMap<>();

        for (String word : words) {
            count.merge(word, 1, Integer::sum);
        }

        int answer = 0;
        boolean central = false;

        for (Map.Entry<String, Integer> entry : count.entrySet()) {
            String word = entry.getKey();
            int countOfTheWord = entry.getValue();

            if (word.charAt(0) == word.charAt(1)) {
                if (countOfTheWord % 2 == 0) {
                    answer += countOfTheWord;
                } else {
                    answer += countOfTheWord - 1;
                    central = true;
                }
            } else if (word.charAt(0) < word.charAt(1)) {
                String reversedWord = "" + word.charAt(1) + word.charAt(0);
                if (count.containsKey(reversedWord)) {
                    answer += 2 * Math.min(countOfTheWord, count.get(reversedWord));
                }
            }
        }

        if (central) {
            answer++;
        }

        return 2 * answer;
    }

    public int differenceOfSums(int n, int m) {
        return IntStream.rangeClosed(1, n)
                .map(i -> i % m == 0 ? -i : i)
                .sum();
    }

    public void reverseString(char[] s) {
        int start = 0;
        int end = s.length - 1;

        while (start < end) {
            char temp = s[start];
            s[start] = s[end];
            s[end] = temp;

            start++;
            end--;
        }
    }

    public int[] sortedSquares(int[] nums) {
        int[] squares = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            squares[i] = nums[i] * nums[i];
        }

        Arrays.sort(squares);
        return squares;
    }

    public double findMaxAverage(int[] nums, int k) {
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }

        double maxAverage = sum * 1.0d / k;

        for (int i = k; i < nums.length; i++) {
            sum += nums[i] - nums[i - k];
            maxAverage = Math.max(maxAverage, sum * 1.0d / k);
        }

        return maxAverage;
    }

    public int longestOnes(int[] nums, int k) {
        int zerosFlipped = 0;

        int maxWindowSize = Integer.MIN_VALUE;

        int start = 0;
        int end = 0;

        while (end < nums.length) {
            if (nums[end] == 0) {
                zerosFlipped++;
            }

            end++;

            while (zerosFlipped > k) {
                if (nums[start] == 0) {
                    zerosFlipped--;
                }

                start++;
            }

            maxWindowSize = Math.max(maxWindowSize, end - start);
        }

        return maxWindowSize;
    }

    public int[] runningSum(int[] nums) {
        int[] runningSums = new int[nums.length];
        runningSums[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            runningSums[i] = runningSums[i - 1] + nums[i];
        }

        return runningSums;
    }

    public int minStartValue(int[] nums) {
        int[] prefixSums = new int[nums.length];
        prefixSums[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            prefixSums[i] = prefixSums[i - 1] + nums[i];
        }

        int minPrefix = Arrays.stream(prefixSums).min().orElseThrow();

        if (minPrefix < 0) {
            return Math.abs(minPrefix) + 1;
        }

        return 1;
    }

    public int[] getAverages(int[] nums, int k) {
        if (k == 0) {
            return nums;
        }

        int windowSize = 2 * k + 1;
        int n = nums.length;
        int[] averages = new int[n];
        Arrays.fill(averages, -1);

        if (windowSize > n) {
            return averages;
        }

        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; ++i) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        for (int i = k; i < (n - k); ++i) {
            int leftBound = i - k, rightBound = i + k;
            long subArraySum = prefix[rightBound + 1] - prefix[leftBound];
            int average = (int) (subArraySum / windowSize);
            averages[i] = average;
        }

        return averages;
    }

    public boolean checkIfPangram(String sentence) {
        Set<Character> characters = new HashSet<>();

        for (char c : sentence.toCharArray()) {
            characters.add(c);
        }

        return characters.size() == 26;
    }

    public int missingNumber(int[] nums) {
        int expectedSum = nums.length * (nums.length + 1) / 2;
        int actualSum = 0;

        for (int i : nums) {
            actualSum += i;
        }

        return expectedSum - actualSum;
    }
}
