package thirtydaychallenge

func countElements(numbers []int) int {
	uniqueElements := map[int]struct{}{}

	for _, i := range numbers {
		uniqueElements[i] = struct{}{}
	}

	var count int

	for _, i := range numbers {
		if _, present := uniqueElements[i+1]; present {
			count++
		}
	}

	return count
}
