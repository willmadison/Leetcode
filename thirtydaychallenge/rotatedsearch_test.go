package thirtydaychallenge

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestSearch(t *testing.T) {
	cases := []struct {
		given struct {
			haystack []int
			needle   int
		}
		expected int
	}{
		{
			struct {
				haystack []int
				needle   int
			}{
				[]int{4, 5, 6, 7, 0, 1, 2},
				0,
			},
			4,
		},
		{
			struct {
				haystack []int
				needle   int
			}{
				[]int{4, 5, 6, 7, 0, 1, 2},
				3,
			},
			-1,
		},
		{
			struct {
				haystack []int
				needle   int
			}{
				[]int{},
				5,
			},
			-1,
		},
		{
			struct {
				haystack []int
				needle   int
			}{
				[]int{1},
				0,
			},
			-1,
		},
		{
			struct {
				haystack []int
				needle   int
			}{
				[]int{1, 3},
				0,
			},
			-1,
		},
		{
			struct {
				haystack []int
				needle   int
			}{
				[]int{3, 1},
				3,
			},
			0,
		},
		{
			struct {
				haystack []int
				needle   int
			}{
				[]int{3, 1},
				1,
			},
			1,
		},
		{
			struct {
				haystack []int
				needle   int
			}{
				[]int{1, 3, 5},
				5,
			},
			2,
		},
		{
			struct {
				haystack []int
				needle   int
			}{
				[]int{5, 1, 3},
				5,
			},
			0,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("search(%v, %v)", tc.given.haystack, tc.given.needle), func(t *testing.T) {
			actual := search(tc.given.haystack, tc.given.needle)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
