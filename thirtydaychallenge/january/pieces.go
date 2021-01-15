package january

func canFormArray(a []int, pieces [][]int) bool {
	pieceLocationsByValue := map[int]int{}

	for i, piece := range pieces {
		for _, v := range piece {
			pieceLocationsByValue[v] = i
		}
	}

	for i := 0; i < len(a); {
		var index int
		var present bool

		if index, present = pieceLocationsByValue[a[i]]; !present {
			return false
		}

		if len(pieces[index]) == 1 {
			i++
		} else {
			items := pieces[index]

			var current int

			for {
				if a[i] == items[current] {
					i++
					current++

					if i >= len(a) || current >= len(items) {
						break
					}
				} else {
					return false
				}
			}
		}
	}

	return true
}
