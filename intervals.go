package leetcode

import (
	"sort"
)

func merge(intervals [][]int) [][]int {
	sort.Slice(intervals, func(i, j int) bool {
		return intervals[i][0] < intervals[j][0]
	})

	merged := [][]int{intervals[0]}

	for i := 1; i < len(intervals); i++ {
		last := merged[len(merged)-1]

		if intervals[i][0] > last[1] {
			merged = append(merged, intervals[i])
		} else {
			last[1] = max(intervals[i][1], last[1])
		}
	}

	return merged
}

func max(a, b int) int {
	if a > b {
		return a
	}

	return b
}
