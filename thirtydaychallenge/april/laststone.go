package april

import "container/heap"

type priorityQueue []int

func (p priorityQueue) Len() int {
	return len(p)
}

func (p priorityQueue) Less(i, j int) bool {
	return p[i] > p[j]
}

func (p priorityQueue) Swap(i, j int) {
	p[i], p[j] = p[j], p[i]
}

func (p *priorityQueue) Push(x interface{}) {
	item := x.(int)
	*p = append(*p, item)
}

func (p *priorityQueue) Pop() interface{} {
	old := *p
	n := len(old)
	item := old[n-1]
	*p = old[0 : n-1]
	return item
}

func lastStoneWeight(stones []int) int {
	p := make(priorityQueue, len(stones))
	for _, stone := range stones {
		heap.Push(&p, stone)
	}

	for p.Len() >= 2 {
		a := heap.Pop(&p).(int)
		b := heap.Pop(&p).(int)

		resultantStone := smash(a, b)

		if resultantStone > 0 {
			heap.Push(&p, resultantStone)
		}
	}

	if p.Len() == 1 {
		last := heap.Pop(&p)
		return last.(int)
	}

	return 0
}

func smash(a, b int) int {
	return abs(a - b)
}

func abs(i int) int {
	if i < 0 {
		return -i
	}

	return i
}
