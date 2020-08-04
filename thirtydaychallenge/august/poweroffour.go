package august

import "math"

func isPowerOfFour(n int) bool {
	if n <= 0 {
		return false
	}

	x := int(math.Log(float64(n)) / math.Log(float64(4)))
	return n == int(math.Pow(4.0, float64(x)))
}
