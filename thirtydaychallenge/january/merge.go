package january

func merge(nums1 []int, m int, nums2 []int, n int) {
	var left, right int

	for left, right = 0, 0; left < m+n && right < n; {
		if left > m && nums1[left] == 0 {
			break
		}

		if nums2[right] <= nums1[left] {
			insertAt(left, nums2[right], nums1)
			left++
			right++
		} else {
			left++
		}
	}

	for right < n {
		if nums1[left] < nums2[right] && nums1[left] != 0 {
			left++
		} else {
			nums1[left] = nums2[right]
			right++
			left++
		}
	}
}

func insertAt(i int, value int, values []int) {
	for j := len(values) - 1; j > i; j-- {
		values[j] = values[j-1]
	}

	values[i] = value
}
