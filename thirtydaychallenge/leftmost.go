package thirtydaychallenge

import "math"

type BinaryMatrix interface {
	Get(int, int) int
	Dimensions() []int
}

//type coordinate struct {
//	row, col int
//}

func leftMostColumnWithOne(b BinaryMatrix) int {
	d := b.Dimensions()
	rows, columns := d[0], d[1]

	leftmost := coordinate{0, columns - 1}

	lastColumnWith1 := math.MinInt64

	for leftmost.col >= 0 && leftmost.row <= rows-1 {
		atBottomLeft := leftmost.row == rows-1 && leftmost.col == 0

		val := b.Get(leftmost.row, leftmost.col)

		switch val {
		case 0:
			if leftmost.row+1 < rows {
				leftmost.row++
			} else if leftmost.col-1 >= 0 {
				leftmost.col--
			}
		case 1:
			lastColumnWith1 = leftmost.col
			leftmost.col--
		}

		if atBottomLeft {
			break
		}
	}

	if lastColumnWith1 == math.MinInt64 {
		return -1
	}

	return lastColumnWith1
}
