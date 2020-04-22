package thirtydaychallenge

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

type testMatrix struct {
	data        [][]int
	invocations int
}

func (t *testMatrix) Get(row, col int) int {
	t.invocations++
	return t.data[row][col]
}

func (t testMatrix) Dimensions() []int {
	return []int{len(t.data), len(t.data[0])}
}

func TestLeftMostColumnWithOne(t *testing.T) {
	cases := []struct {
		given    testMatrix
		expected int
	}{
		{
			testMatrix{
				data: [][]int{
					{0, 0},
					{0, 1},
				},
			},
			1,
		},
		{
			testMatrix{
				data: [][]int{
					{0, 0},
					{0, 0},
				},
			},
			-1,
		},
		{
			testMatrix{
				data: [][]int{
					{0, 0},
					{1, 0},
				},
			},
			0,
		},
		{
			testMatrix{
				data: [][]int{
					{0, 0, 0, 1},
					{0, 0, 1, 1},
					{0, 1, 1, 1},
				},
			},
			1,
		},
		{
			testMatrix{
				data: [][]int{
					{0, 0, 0, 1},
					{0, 0, 1, 1},
					{0, 1, 1, 1},
				},
			},
			1,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("leftMostColumnWithOne(%v)", tc.given), func(t *testing.T) {
			actual := leftMostColumnWithOne(&tc.given)
			assert.Equal(t, tc.expected, actual)
			assert.LessOrEqual(t, tc.given.invocations, 1000)
		})
	}
}
