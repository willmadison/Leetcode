package april

// https://leetcode.com/explore/featured/card/30-day-leetcoding-challenge/528/week-1/3284/

var digitSquareSumsByNumber = map[int]int{}

func isHappy(n int) bool {
	digits := getDigitsOf(n)

	s := sumOfSquares(digits)

	if s == 1 {
		reset(&digitSquareSumsByNumber)
		return true
	}

	if _, seen := digitSquareSumsByNumber[n]; !seen {
		digitSquareSumsByNumber[n] = s
		return isHappy(s)
	}

	reset(&digitSquareSumsByNumber)
	return false
}

func getDigitsOf(n int) []int {
	var digits []int

	for n > 0 {
		digit := n % 10
		digits = append(digits, digit)
		n /= 10
	}

	return digits
}

func sumOfSquares(numbers []int) int {
	var s int

	for _, i := range numbers {
		s += square(i)
	}

	return s
}

func square(n int) int {
	return n * n
}

func reset(m *map[int]int) {
	*m = map[int]int{}
}
