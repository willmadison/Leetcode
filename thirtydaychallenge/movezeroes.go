package thirtydaychallenge

func moveZeroes(nums []int) {
	for i := 0; i < len(nums); i++ {
		if nums[i] == 0 {
			n := findNextNonZeroAfter(i, nums)

			if n != -1 {
				nums[i], nums[n] = nums[n], nums[i]
			} else {
				break
			}
		}
	}
}

func findNextNonZeroAfter(i int, nums []int) int {
	for j := i + 1; j < len(nums); j++ {
		if nums[j] != 0 {
			return j
		}
	}

	return -1
}
