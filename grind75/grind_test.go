package grind75

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestTwoSum(t *testing.T) {
	cases := []struct {
		given struct {
			nums   []int
			target int
		}
		expected []int
	}{
		{
			struct {
				nums   []int
				target int
			}{nums: []int{2, 7, 11, 15}, target: 9},
			[]int{0, 1},
		},
		{
			struct {
				nums   []int
				target int
			}{nums: []int{3, 2, 4}, target: 6},
			[]int{1, 2},
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("twoSum(%v, %v)", tc.given.nums, tc.given.target), func(t *testing.T) {
			actual := twoSum(tc.given.nums, tc.given.target)
			assert.ElementsMatch(t, tc.expected, actual)
		})
	}
}

func TestIsValid(t *testing.T) {
	cases := []struct {
		given    string
		expected bool
	}{
		{
			"()",
			true,
		},
		{
			"()[]{}",
			true,
		},
		{
			"(]",
			false,
		},
		{
			"([)]",
			false,
		},
		{
			"{[]}",
			true,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("isValid(%v)", tc.given), func(t *testing.T) {
			actual := isValid(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestMaxProfit(t *testing.T) {
	cases := []struct {
		name  string
		given struct {
			prices []int
		}
		expected int
	}{
		{
			"max profit possible",
			struct {
				prices []int
			}{
				prices: []int{7, 1, 5, 3, 6, 4},
			},
			5,
		},
		{
			"no max profit possible",
			struct {
				prices []int
			}{
				prices: []int{7, 6, 4, 3, 1},
			},
			0,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("[%v] - maxProfit(%v)", tc.name, tc.given.prices), func(t *testing.T) {
			actual := maxProfit(tc.given.prices)
			assert.Equal(t, tc.expected, actual)
		})
	}

}

func TestInvertTree(t *testing.T) {
	one := TreeNode{Val: 1}
	three := TreeNode{Val: 3}
	six := TreeNode{Val: 6}
	nine := TreeNode{Val: 9}
	two := TreeNode{Val: 2, Left: &one, Right: &three}
	seven := TreeNode{Val: 7, Left: &six, Right: &nine}
	four := TreeNode{Val: 4, Left: &two, Right: &seven}

	twoPrime := TreeNode{Val: 2, Left: &three, Right: &one}
	sevenPrime := TreeNode{Val: 7, Left: &nine, Right: &six}
	fourPrime := TreeNode{Val: 4, Left: &sevenPrime, Right: &twoPrime}

	actual := invertTree(&four)
	assert.Equal(t, &fourPrime, actual)
}
