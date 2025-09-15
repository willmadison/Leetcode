package com.willmadison.leetcode;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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

    public int maxDifference(String s) {
        Map<Character, Integer> characterCounts = new HashMap<>();

        for (Character c : s.toCharArray()) {
            characterCounts.merge(c, 1, Integer::sum);
        }

        int maxOdd = 1;
        int minEven = Integer.MAX_VALUE;

        for (Map.Entry<Character, Integer> entry : characterCounts.entrySet()) {
            Integer count = entry.getValue();

            if (count % 2 == 0) {
                minEven = Math.min(minEven, count);
            } else {
                maxOdd = Math.max(maxOdd, count);
            }
        }

        return maxOdd - minEven;
    }

    public int maxAdjacentDistance(int[] nums) {
        int maxDifference = Math.max(Integer.MIN_VALUE,
                Math.abs(nums[0] - nums[nums.length - 1]));

        for (int i = 0; i < nums.length - 1; i++) {
            int difference = Math.abs(nums[i] - nums[i + 1]);
            maxDifference = Math.max(maxDifference, difference);
        }

        return maxDifference;
    }

    public int numberOfPairs(int[][] points) {
        int numPairs = 0;
        int n = points.length;

        for (int i = 0; i < n; i++) {
            int[] pointA = points[i];
            for (int j = 0; j < n; j++) {
                int[] pointB = points[j];
                if (
                        i == j ||
                                !(pointA[0] <= pointB[0] && pointA[1] >= pointB[1])
                ) {
                    continue;
                }
                if (n == 2) {
                    numPairs++;
                    continue;
                }

                boolean legal = true;

                for (int k = 0; k < n; k++) {
                    if (k == i || k == j) {
                        continue;
                    }

                    int[] pointTmp = points[k];
                    boolean isXContained =
                            pointTmp[0] >= pointA[0] && pointTmp[0] <= pointB[0];
                    boolean isYContained =
                            pointTmp[1] <= pointA[1] && pointTmp[1] >= pointB[1];
                    if (isXContained && isYContained) {
                        legal = false;
                        break;
                    }
                }

                if (legal) {
                    numPairs++;
                }
            }
        }

        return numPairs;
    }

    public int minimumDeletions(String word, int k) {
        Map<Character, Integer> countsByCharacter = new HashMap<>();
        for (char c : word.toCharArray()) {
            countsByCharacter.merge(c, 1, Integer::sum);
        }

        int minDeletions = word.length();

        for (int a : countsByCharacter.values()) {
            int deleted = 0;
            for (int b : countsByCharacter.values()) {
                if (a > b) {
                    deleted += b;
                } else if (b > a + k) {
                    deleted += b - (a + k);
                }
            }
            minDeletions = Math.min(minDeletions, deleted);
        }

        return minDeletions;
    }

    public String[] divideString(String s, int k, char fill) {
        List<String> partitionedString = new ArrayList<>();
        int n = s.length();
        int startIndex = 0;

        while (startIndex < n) {
            int end = Math.min(startIndex + k, n);
            partitionedString.add(s.substring(startIndex, end));
            startIndex += k;
        }

        String last = partitionedString.getLast();

        if (last.length() < k) {
            last += String.valueOf(fill).repeat(k - last.length());
            partitionedString.set(partitionedString.size() - 1, last);
        }

        return partitionedString.toArray(new String[0]);
    }


    public int findClosest(int x, int y, int z) {
        int xDistance = Math.abs(x - z);
        int yDistance = Math.abs(y - z);

        if (xDistance == yDistance) return 0;
        return xDistance < yDistance ? 1 : 2;
    }

    public int maxFreqSum(@NotNull String s) {
        Map<Character, Integer> countsByVowel = new HashMap<>(
                Map.of('a', 0, 'e', 0, 'i', 0, 'o', 0, 'u', 0)
        );

        Map<Character, Integer> countsByConsonant = new HashMap<>();

        for (char c : s.toCharArray()) {
            if (isVowel(c)) {
                countsByVowel.merge(c, 1, Integer::sum);
            } else {
                countsByConsonant.merge(c, 1, Integer::sum);
            }
        }

        int maxVowelCount = countsByVowel.values().stream().mapToInt(Integer::intValue).max().orElse(0);
        int maxConsonantCount = countsByConsonant.values().stream().mapToInt(Integer::intValue).max().orElse(0);

        return maxVowelCount+maxConsonantCount;
    }

    private boolean isVowel(char c) {
        return "aeiou".indexOf(c) >= 0;
    }

    Set<String> asIsLookup;
    Map<String, String> caseInsensitiveLookup;
    Map<String, String> devoweledLookup;

    public String[] spellchecker(String[] wordlist, String[] queries) {
        asIsLookup = new HashSet<>();
        caseInsensitiveLookup = new HashMap<>();
        devoweledLookup = new HashMap<>();

        for (String word: wordlist) {
            asIsLookup.add(word);

            String lowercased = word.toLowerCase();
            caseInsensitiveLookup.putIfAbsent(lowercased, word);

            devoweledLookup.putIfAbsent(devowel(lowercased), word);
        }

        String[] answers = new String[queries.length];

        int t = 0;

        for (String query: queries) {
            answers[t++] = search(query);
        }

        return answers;
    }

    public String search(String query) {
        if (asIsLookup.contains(query))
            return query;

        String lowercased = query.toLowerCase();
        if (caseInsensitiveLookup.containsKey(lowercased))
            return caseInsensitiveLookup.get(lowercased);

        String devoweled = devowel(lowercased);
        if (devoweledLookup.containsKey(devoweled))
            return devoweledLookup.get(devoweled);

        return "";
    }

    public String devowel(String word) {
        StringBuilder ans = new StringBuilder();
        for (char c: word.toCharArray())
            ans.append(isVowel(c) ? '*' : c);
        return ans.toString();
    }

    public int canBeTypedWords(String text, String brokenLetters) {
        Set<Character> letters = brokenLetters.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet());

        String[] words = text.split(Pattern.quote(" "));

        int canBeTypedWords = 0;

        for (String word : words) {
            boolean canType = true;

            for (Character letter: letters) {
                if (word.indexOf(letter) >= 0) {
                    canType = false;
                    break;
                }
            }

            if (canType) {
                canBeTypedWords++;
            }
        }

        return canBeTypedWords;
    }

}
