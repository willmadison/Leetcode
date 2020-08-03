package april

func productExceptSelf(nums []int) []int {
	products := make([]int, len(nums), len(nums))
	leftToRight := make([]int, len(nums), len(nums))
	rightToLeft := make([]int, len(nums), len(nums))

	leftToRight[0] = 1
	rightToLeft[len(nums)-1] = 1

	for i := 0; i < len(nums)-1; i++ {
		leftToRight[i+1] = nums[i] * leftToRight[i]
	}

	for i := len(nums) - 1; i > 0; i-- {
		rightToLeft[i-1] = nums[i] * rightToLeft[i]
	}

	for i := range nums {
		products[i] = leftToRight[i] * rightToLeft[i]
	}

	return products
}
