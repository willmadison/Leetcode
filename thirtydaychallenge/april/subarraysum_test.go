package april

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestSubarraySum(t *testing.T) {
	cases := []struct {
		given struct {
			nums      []int
			targetSum int
		}
		expected int
	}{
		{
			struct {
				nums      []int
				targetSum int
			}{
				[]int{1, 1, 1},
				2,
			},
			2,
		},
		{
			struct {
				nums      []int
				targetSum int
			}{
				[]int{1, 3, 4, 7},
				7,
			},
			2,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("subarraySum(%v, %v", tc.given.nums, tc.given.targetSum), func(t *testing.T) {
			actual := subarraySum(tc.given.nums, tc.given.targetSum)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
