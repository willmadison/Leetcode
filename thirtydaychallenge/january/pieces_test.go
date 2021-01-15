package january

import (
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestCanFormArray(t *testing.T) {
	cases := []struct {
		given struct {
			arr    []int
			pieces [][]int
		}
		expected bool
	}{
		{
			struct {
				arr    []int
				pieces [][]int
			}{arr: []int{85}, pieces: [][]int{{85}}},
			true,
		},
		{
			struct {
				arr    []int
				pieces [][]int
			}{arr: []int{15, 88}, pieces: [][]int{{88}, {15}}},
			true,
		},
		{
			struct {
				arr    []int
				pieces [][]int
			}{arr: []int{91, 4, 64, 78}, pieces: [][]int{{78}, {4, 64}, {91}}},
			true,
		},
		{
			struct {
				arr    []int
				pieces [][]int
			}{arr: []int{49, 18, 16}, pieces: [][]int{{16, 18, 49}}},
			false,
		},
	}

	for _, tc := range cases {
		actual := canFormArray(tc.given.arr, tc.given.pieces)
		assert.Equal(t, tc.expected, actual)
	}
}
