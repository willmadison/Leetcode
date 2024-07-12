package leetcode

import (
	"fmt"
	"math"
	"testing"

	"github.com/stretchr/testify/assert"
)

const epsilon = .00001

func TestFindMedianSortedArrays(t *testing.T) {
	cases := []struct {
		a, b     []int
		expected float64
	}{
		{
			a:        []int{1, 3},
			b:        []int{2},
			expected: 2.0,
		},
		{
			a:        []int{1, 2},
			b:        []int{3, 4},
			expected: 2.5,
		},
	}

	for _, tc := range cases {
		actual := findMedianSortedArrays(tc.a, tc.b)

		if math.Abs(actual-tc.expected) > epsilon {
			t.Fatalf("got %v, expected: %v", actual, tc.expected)
		}
	}
}

func TestLRUCacheBaseCase(t *testing.T) {
	cache := Constructor(2)

	cache.Put(1, 1)
	cache.Put(2, 2)

	assert.Equal(t, 1, cache.Get(1))

	cache.Put(3, 3) // Should evict the item keyed under 2 since it hasn't been accessed...

	assert.Equal(t, -1, cache.Get(2))

	cache.Put(4, 4) // Should evict the item keyed under 1

	assert.Equal(t, -1, cache.Get(1))
	assert.Equal(t, 3, cache.Get(3))
	assert.Equal(t, 4, cache.Get(4))
}

func TestLRUCacheSubmissionCase(t *testing.T) {
	cache := Constructor(2)

	assert.Equal(t, -1, cache.Get(2))

	cache.Put(2, 6)

	assert.Equal(t, -1, cache.Get(1))

	cache.Put(1, 5)
	cache.Put(1, 2)

	assert.Equal(t, 2, cache.Get(1))
	assert.Equal(t, 6, cache.Get(2))

	cache.Put(4, 7)

	assert.Equal(t, -1, cache.Get(1))
}

func TestSpiralMatrix(t *testing.T) {
	cases := []struct {
		given    [][]int
		expected []int
	}{
		{
			[][]int{
				{1, 2, 3},
				{4, 5, 6},
				{7, 8, 9},
			},
			[]int{1, 2, 3, 6, 9, 8, 7, 4, 5},
		},
		{
			[][]int{
				{1, 2, 3, 4},
				{5, 6, 7, 8},
				{9, 10, 11, 12},
			},
			[]int{1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7},
		},
		{
			[][]int{
				{1, 2, 3, 4},
				{5, 6, 7, 8},
				{9, 10, 11, 12},
				{13, 14, 15, 16},
			},
			[]int{1, 2, 3, 4, 8, 12, 16, 15, 14, 13, 9, 5, 6, 7, 11, 10},
		},
		{
			[][]int{},
			[]int{},
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("spiralOrder(%v)", tc.given), func(t *testing.T) {
			actual := spiralOrder(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}

}

func TestAverageWaitingTime(t *testing.T) {
	cases := []struct {
		customers [][]int
		expected  float64
	}{
		{
			[][]int{{1, 2}, {2, 5}, {4, 3}},
			5.0,
		},
		{
			[][]int{{5, 2}, {5, 4}, {10, 3}, {20, 1}},
			3.25,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("averageWaitingTime(%v): %v", tc.customers, tc.expected), func(t *testing.T) {
			actual := averageWaitingTime(tc.customers)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestCanPlaceFlowers(t *testing.T) {
	cases := []struct {
		given struct {
			flowerbed []int
			n         int
		}
		expected bool
	}{
		{
			struct {
				flowerbed []int
				n         int
			}{
				[]int{1, 0, 0, 0, 1},
				1,
			},
			true,
		},
		{
			struct {
				flowerbed []int
				n         int
			}{
				[]int{1, 0, 0, 0, 1},
				2,
			},
			false,
		},
		{
			struct {
				flowerbed []int
				n         int
			}{
				[]int{1, 0, 0, 0, 0, 0, 1},
				2,
			},
			true,
		},
		{
			struct {
				flowerbed []int
				n         int
			}{
				[]int{0, 0, 1, 0, 1},
				1,
			},
			true,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("canPlaceFlowers(%v, %v): %v", tc.given.flowerbed, tc.given.n, tc.expected), func(t *testing.T) {
			actual := canPlaceFlowers(tc.given.flowerbed, tc.given.n)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestReverseVowels(t *testing.T) {
	cases := []struct {
		given    string
		expected string
	}{
		{
			"hello",
			"holle",
		},
		{
			"leetcode",
			"leotcede",
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("reverseVowels(%v): %v", tc.given, tc.expected), func(t *testing.T) {
			actual := reverseVowels(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestReverseWords(t *testing.T) {
	cases := []struct {
		given    string
		expected string
	}{
		{
			"the sky is blue",
			"blue is sky the",
		},
		{
			"  hello world  ",
			"world hello",
		},
		{
			"a good   example",
			"example good a",
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("reverseWords(%v): %v", tc.given, tc.expected), func(t *testing.T) {
			actual := reverseWords(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestMinOperations(t *testing.T) {
	cases := []struct {
		given    []string
		expected int
	}{
		{
			[]string{"d1/", "d2/", "../", "d21/", "./"},
			2,
		},
		{
			[]string{"d1/", "d2/", "./", "d3/", "../", "d31/"},
			3,
		},
		{
			[]string{"d1/", "../", "../", "../"},
			0,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("minOperations(%v): %v", tc.given, tc.expected), func(t *testing.T) {
			actual := minOperations(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestProductExceptSelf(t *testing.T) {
	cases := []struct {
		given    []int
		expected []int
	}{
		{
			[]int{1, 2, 3, 4},
			[]int{24, 12, 8, 6},
		},
		{
			[]int{-1, 1, 0, -3, 3},
			[]int{0, 0, 9, 0, 0},
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("productExceptSelf(%v): %v", tc.given, tc.expected), func(t *testing.T) {
			actual := productExceptSelf(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestIncreasingTriplet(t *testing.T) {
	cases := []struct {
		given    []int
		expected bool
	}{
		{
			[]int{1, 2, 3, 4},
			true,
		},
		{
			[]int{-1, 1, 0, -3, 3},
			true,
		},
		{
			[]int{5, 4, 3, 2, 1},
			false,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("increasingTriplet(%v): %v", tc.given, tc.expected), func(t *testing.T) {
			actual := increasingTriplet(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestCompress(t *testing.T) {
	cases := []struct {
		given    []byte
		expected int
	}{
		{
			[]byte{'a', 'a', 'b', 'b', 'c', 'c', 'c'},
			6,
		},
		{
			[]byte{'a'},
			1,
		},
		{
			[]byte{'a', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b'},
			4,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("compress(%v): %v", tc.given, tc.expected), func(t *testing.T) {
			actual := compress(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestIsSubsequence(t *testing.T) {
	cases := []struct {
		given struct {
			s, t string
		}
		expected bool
	}{
		{
			struct {
				s, t string
			}{
				s: "abc",
				t: "ahbgdc",
			},
			true,
		},
		{
			struct {
				s, t string
			}{
				s: "axc",
				t: "ahbgdc",
			},
			false,
		},
		{
			struct {
				s, t string
			}{
				s: "bb",
				t: "ahbgdc",
			},
			false,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("isSubsequence(%v, %v): %v", tc.given.s, tc.given.t, tc.expected), func(t *testing.T) {
			actual := isSubsequence(tc.given.s, tc.given.t)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestReverseSubstring(t *testing.T) {
	cases := []struct {
		given    string
		expected string
	}{
		{
			given:    "(abcd)",
			expected: "dcba",
		},
		{
			given:    "(u(love)i)",
			expected: "iloveu",
		},
		{
			given:    "(ed(et(oc))el)",
			expected: "leetcode",
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("reverseParenthesis(%v): %v", tc.given, tc.expected), func(t *testing.T) {
			actual := reverseParentheses(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestMaximumGain(t *testing.T) {
	cases := []struct {
		given struct {
			s    string
			x, y int
		}
		expected int
	}{
		{
			struct {
				s    string
				x, y int
			}{
				s: "cdbcbbaaabab",
				x: 4,
				y: 5,
			},
			19,
		},
		{
			struct {
				s    string
				x, y int
			}{
				s: "aabbaaxybbaabb",
				x: 5,
				y: 4,
			},
			20,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("maximumGain(%v, %v, %v): %v", tc.given.s, tc.given.x, tc.given.y, tc.expected), func(t *testing.T) {
			actual := maximumGain(tc.given.s, tc.given.x, tc.given.y)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestMaxArea(t *testing.T) {
	cases := []struct {
		given    []int
		expected int
	}{
		{
			[]int{1, 8, 6, 2, 5, 4, 8, 3, 7},
			49,
		},
		{
			[]int{1, 1},
			1,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("maxArea(%v): %v", tc.given, tc.expected), func(t *testing.T) {
			actual := maxArea(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
