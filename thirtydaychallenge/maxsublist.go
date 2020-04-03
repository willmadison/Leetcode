package thirtydaychallenge

import "math"

// https://leetcode.com/explore/featured/card/30-day-leetcoding-challenge/528/week-1/3285/

func maxSubArray(numbers []int) int {
	maxSum := math.MinInt64
	sum := math.MinInt64

	for _, i := range numbers {
		sum = max(0, sum)
		sum += i
		maxSum = max(sum, maxSum)
	}

	return maxSum
}

func max(a, b int) int {
	if a > b {
		return a
	}

	return b
}
