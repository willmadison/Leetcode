package thirtydaychallenge

func subarraySum(nums []int, targetSum int) int {
	var hits, sum int

	occurrencesBySum := map[int]int{}

	occurrencesBySum[0] = 1

	for _, i := range nums {
		sum += i

		delta := sum - targetSum

		if _, present := occurrencesBySum[delta]; present {
			hits += occurrencesBySum[delta]
		}

		occurrencesBySum[sum]++
	}

	return hits
}
