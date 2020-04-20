package thirtydaychallenge

import (
	"errors"
)

func search(haystack []int, needle int) int {
	if len(haystack) <= 1 {
		switch len(haystack) {
		case 1:
			if needle == haystack[0] {
				return 0
			}
			fallthrough
		default:
			return -1
		}
	}

	pivot, err := findPivotPoint(haystack)

	low := 0
	high := len(haystack) - 1

	if err == nil {
		if needle >= haystack[pivot+1] && needle < haystack[0] {
			low = pivot + 1
		} else {
			high = pivot
		}
	}

	for low <= high {
		midpoint := (low + high) / 2

		if needle > haystack[midpoint] {
			low = midpoint + 1
		} else {
			high = midpoint - 1
		}
	}

	if low == len(haystack) || haystack[low] != needle {
		return -1
	}

	return low
}

func findPivotPoint(haystack []int) (int, error) {
	var pivotFound bool
	var pivot int

	for ; pivot <= len(haystack)-2; pivot++ {
		if haystack[pivot] > haystack[pivot+1] {
			pivotFound = true
			break
		}
	}

	if !pivotFound {
		return 0, errors.New("no pivot point")
	}

	return pivot, nil
}
