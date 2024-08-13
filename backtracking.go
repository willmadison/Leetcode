package leetcode

import (
	"sort"
)

func combinationSum2(candidates []int, target int) [][]int {
	results := [][]int{}

	sort.Ints(candidates)

	var backtrack func([]int, []int, int, int)

	backtrack = func(temp, candidates []int, totalLeft, index int) {
		switch {
		case totalLeft < 0:
			return
		case totalLeft == 0:
			dest := make([]int, len(temp))
			copy(dest, temp)
			results = append(results, dest)
		default:
			for i := index; i < len(candidates) && totalLeft >= candidates[i]; i++ {
				if i > index && candidates[i] == candidates[i-1] {
					continue
				}

				temp = append(temp, candidates[i])

				backtrack(temp, candidates, totalLeft-candidates[i], i+1)

				temp = temp[:len(temp)-1]
			}
		}
	}

	backtrack([]int{}, candidates, target, 0)

	return results
}
