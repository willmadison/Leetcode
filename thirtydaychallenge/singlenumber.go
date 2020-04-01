package thirtydaychallenge

func singleNumber(numbers []int) int {
	occurrencesByNumber := map[int]int{}

	for _, number := range numbers {
		occurrencesByNumber[number]++
	}

	for number, occurrences := range occurrencesByNumber {
		if occurrences == 1 {
			return number
		}
	}

	return 0
}
