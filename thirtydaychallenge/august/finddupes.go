package august

func findDuplicates(nums []int) []int {
	var dupes []int

	for _, i := range nums {
		if nums[abs(i)-1] < 0 {
			dupes = append(dupes, abs(i))
			continue
		}

		nums[abs(i)-1] *= -1
	}

	return dupes
}

func abs(i int) int {
	if i < 0 {
		return -i
	}

	return i
}
