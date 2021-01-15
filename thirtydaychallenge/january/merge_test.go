package january

import (
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestMerge(t *testing.T) {
	nums1 := []int{0}
	nums2 := []int{1}

	merge(nums1, 0, nums2, 1)

	assert.Equal(t, []int{1}, nums1)
}

// [1, 2, 3, 0, 0, 0]
// [1, 2, 2, 3, 0, 0]
