package april

// https://leetcode.com/explore/featured/card/30-day-leetcoding-challenge/528/week-1/3283/

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
