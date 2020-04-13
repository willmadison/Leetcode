package thirtydaychallenge

func findMaxLength(nums []int) int {
	// This approach is similar to parentheis matching in that we'll increment and decrement
	// counts accordingly. When we encounter the same count again we've seen equal 1s and 0s
	// so calculate the delta between indicies which saw that count.

	indiciesByCount := map[int]int{}

	indiciesByCount[0] = -1 // Count is initially zero before we begin evaluation...

	var count, maxLength int

	for i, n := range nums {
		switch n {
		case 0:
			count--
		case 1:
			count++
		}

		if firstSeenAt, seen := indiciesByCount[count]; !seen {
			indiciesByCount[count] = i
		} else {
			maxLength = max(maxLength, i-firstSeenAt)
		}
	}

	return maxLength
}
