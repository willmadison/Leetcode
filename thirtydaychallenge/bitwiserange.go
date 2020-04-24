package thirtydaychallenge

func rangeBitwiseAnd(start, end int) int {
	result := start

	for i := start; i <= end; i++ {
		result &= i

		if result == 0 {
			break
		}
	}

	return result
}
