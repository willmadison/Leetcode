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

func TestSurvivedRobotsHealths(t *testing.T) {
	cases := []struct {
		given struct {
			positions  []int
			healths    []int
			directions string
		}
		expected []int
	}{
		/*
			{
				struct {
					positions  []int
					healths    []int
					directions string
				}{
					positions:  []int{5, 4, 3, 2, 1},
					healths:    []int{2, 17, 9, 15, 10},
					directions: "RRRRR",
				},
				[]int{2, 17, 9, 15, 10},
			},
		*/
		{
			struct {
				positions  []int
				healths    []int
				directions string
			}{
				positions:  []int{3, 5, 2, 6},
				healths:    []int{10, 10, 15, 12},
				directions: "RLRL",
			},
			[]int{14},
		},
		/*
			{
				struct {
					positions  []int
					healths    []int
					directions string
				}{
					positions:  []int{13, 3},
					healths:    []int{17, 2},
					directions: "LR",
				},
				[]int{16},
			},
		*/
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("survivedRobotsHealths(%v, %v, %v): %v", tc.given.positions, tc.given.healths, tc.given.directions, tc.expected), func(t *testing.T) {
			actual := survivedRobotsHealths(tc.given.positions, tc.given.healths, tc.given.directions)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestLuckyNumbers(t *testing.T) {
	cases := []struct {
		given    [][]int
		expected []int
	}{
		{
			[][]int{
				{3, 7, 8},
				{9, 11, 13},
				{15, 16, 17},
			},
			[]int{15},
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("luckyNumbers(%v): %v", tc.given, tc.expected), func(t *testing.T) {
			actual := luckyNumbers(tc.given)
			assert.ElementsMatch(t, tc.expected, actual)
		})
	}
}

func TestBuildMatrix(t *testing.T) {
	cases := []struct {
		given struct {
			k             int
			rowConditions [][]int
			colConditions [][]int
		}
		expected [][]int
	}{
		{
			struct {
				k             int
				rowConditions [][]int
				colConditions [][]int
			}{
				k:             3,
				rowConditions: [][]int{{1, 2}, {3, 2}},
				colConditions: [][]int{{2, 1}, {3, 2}},
			},
			[][]int{{3, 0, 0}, {0, 0, 1}, {0, 2, 0}},
		},
		{
			struct {
				k             int
				rowConditions [][]int
				colConditions [][]int
			}{
				k:             3,
				rowConditions: [][]int{{1, 2}, {2, 3}, {3, 1}, {2, 3}},
				colConditions: [][]int{{2, 1}},
			},
			[][]int{},
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("buildMatrix(%v, %v, %v): %v", tc.given.k, tc.given.rowConditions, tc.given.colConditions, tc.expected), func(t *testing.T) {
			actual := buildMatrix(tc.given.k, tc.given.rowConditions, tc.given.colConditions)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestSortPeople(t *testing.T) {
	cases := []struct {
		given struct {
			names   []string
			heights []int
		}
		expected []string
	}{
		{
			struct {
				names   []string
				heights []int
			}{
				[]string{"Mary", "John", "Emma"},
				[]int{180, 165, 170},
			},
			[]string{"Mary", "Emma", "John"},
		},
		{
			struct {
				names   []string
				heights []int
			}{
				[]string{"Alice", "Bob", "Bob"},
				[]int{155, 185, 150},
			},
			[]string{"Bob", "Alice", "Bob"},
		},
		{
			struct {
				names   []string
				heights []int
			}{
				[]string{"IEO", "Sgizfdfrims", "QTASHKQ", "Vk", "RPJOFYZUBFSIYp", "EPCFFt", "VOYGWWNCf", "WSpmqvb"},
				[]int{17233, 32521, 14087, 42738, 46669, 65662, 43204, 8224},
			},
			[]string{"EPCFFt", "RPJOFYZUBFSIYp", "VOYGWWNCf", "Vk", "Sgizfdfrims", "IEO", "QTASHKQ", "WSpmqvb"},
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("sortPeople(%v, %v): %v", tc.given.names, tc.given.heights, tc.expected), func(t *testing.T) {
			actual := sortPeople(tc.given.names, tc.given.heights)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestRemoveElement(t *testing.T) {
	cases := []struct {
		given struct {
			nums []int
			val  int
		}
		expected int
	}{
		{
			struct {
				nums []int
				val  int
			}{
				[]int{3, 2, 2, 3},
				3,
			},
			2,
		},
		{
			struct {
				nums []int
				val  int
			}{
				[]int{0, 1, 2, 2, 3, 0, 4, 2},
				2,
			},
			5,
		},
		{
			struct {
				nums []int
				val  int
			}{
				[]int{1},
				1,
			},
			0,
		},
		{
			struct {
				nums []int
				val  int
			}{
				[]int{3, 3},
				3,
			},
			0,
		},
		{
			struct {
				nums []int
				val  int
			}{
				[]int{2, 2, 3},
				2,
			},
			1,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("removeElement(%v, %v): %v", tc.given.nums, tc.given.val, tc.expected), func(t *testing.T) {
			actual := removeElement(tc.given.nums, tc.given.val)
			assert.Equal(t, tc.expected, actual)

			for i := 0; i < tc.expected; i++ {
				assert.NotEqual(t, tc.given.nums[i], tc.given.val)
			}
		})
	}
}

func TestFrequencySort(t *testing.T) {
	cases := []struct {
		given    []int
		expected []int
	}{
		{
			given:    []int{1, 1, 2, 2, 2, 3},
			expected: []int{3, 1, 1, 2, 2, 2},
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("frequencySort(%v): %v", tc.given, tc.expected), func(t *testing.T) {
			actual := frequencySort(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestSortJumbled(t *testing.T) {
	cases := []struct {
		given struct {
			mapping []int
			nums    []int
		}
		expected []int
	}{
		{
			struct {
				mapping []int
				nums    []int
			}{
				[]int{8, 9, 4, 0, 2, 1, 3, 5, 7, 6},
				[]int{991, 338, 38},
			},
			[]int{338, 38, 991},
		},
		{
			struct {
				mapping []int
				nums    []int
			}{
				[]int{0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
				[]int{789, 456, 123},
			},
			[]int{123, 456, 789},
		},
		{
			struct {
				mapping []int
				nums    []int
			}{
				[]int{9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
				[]int{0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
			},
			[]int{9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("sortJumbled(%v, %v): %v", tc.given.mapping, tc.given.nums, tc.expected), func(t *testing.T) {
			actual := sortJumbled(tc.given.mapping, tc.given.nums)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestMinSwaps(t *testing.T) {
	cases := []struct {
		given    []int
		expected int
	}{
		{
			[]int{0, 1, 0, 1, 1, 0, 0},
			1,
		},
		{
			[]int{0, 1, 1, 1, 0, 0, 1, 1, 0},
			2,
		},
		{
			[]int{0, 1, 1, 1, 1, 0, 0},
			0,
		},
		{
			[]int{1, 1, 0, 0, 1},
			0,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("minSwaps(%v): %v", tc.given, tc.expected), func(t *testing.T) {
			actual := minSwaps(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestCanBeEqual(t *testing.T) {
	cases := []struct {
		given struct {
			target, source []int
		}
		expected bool
	}{
		{
			struct {
				target, source []int
			}{
				target: []int{1, 2, 3, 4},
				source: []int{2, 4, 1, 3},
			},
			true,
		},
		{
			struct {
				target, source []int
			}{
				target: []int{3, 7, 9},
				source: []int{3, 7, 11},
			},
			false,
		},
		{
			struct {
				target, source []int
			}{
				target: []int{7},
				source: []int{7},
			},
			true,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("canBeEqual(%v, %v): %v", tc.given.target, tc.given.source, tc.expected), func(t *testing.T) {
			actual := canBeEqual(tc.given.target, tc.given.source)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestKthDistinct(t *testing.T) {
	cases := []struct {
		given struct {
			strings []string
			k       int
		}
		expected string
	}{
		{
			struct {
				strings []string
				k       int
			}{
				strings: []string{"d", "b", "c", "b", "c", "a"},
				k:       2,
			},
			"a",
		},
		{
			struct {
				strings []string
				k       int
			}{
				strings: []string{"aaa", "aa", "a"},
				k:       1,
			},
			"aaa",
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("kthDistinct(%v, %v): %v", tc.given.strings, tc.given.k, tc.expected), func(t *testing.T) {
			actual := kthDistinct(tc.given.strings, tc.given.k)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestMinimumPushes(t *testing.T) {
	cases := []struct {
		given    string
		expected int
	}{
		{
			"abcde",
			5,
		},
		{
			"xyzxyzxyzxyz",
			12,
		},
		{
			"aabbccddeeffgghhiiiiii",
			24,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("minimumPushes(%v): %v", tc.given, tc.expected), func(t *testing.T) {
			actual := minimumPushes(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}

func TestNumberToWords(t *testing.T) {
	cases := []struct {
		given    int
		expected string
	}{
		{
			5,
			"Five",
		},
		{
			12,
			"Twelve",
		},
		{
			24,
			"Twenty Four",
		},
		{
			1234567,
			"One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven",
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("numberToWords(%v): %v", tc.given, tc.expected), func(t *testing.T) {
			actual := numberToWords(tc.given)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
