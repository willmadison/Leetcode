package thirtydaychallenge

func canJump(nums []int) bool {
	target := len(nums) - 1
	var start int

	if start == target {
		return true
	}

	adjacencyList := map[int]map[int]struct{}{}

	for i := range nums {
		adjacencyList[i] = map[int]struct{}{}
	}

	for i, maxJump := range nums {
		for j := i + 1; j <= i+maxJump && j < len(nums) && nums[j] > 0; j++ {
			adjacencyList[i][j] = struct{}{}
		}
	}

	for _, adjacents := range adjacencyList {
		if _, present := adjacents[target]; present {
			return len(adjacencyList[start]) > 0
		}
	}

	return false
}
