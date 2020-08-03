package april

import "errors"

type stack interface {
	Push(i int)
	Pop() (int, error)
	Peek() (int, error)
}

type intStack struct {
	data []int
	size int
}

func (s *intStack) Push(value int) {
	s.data = append(s.data, value)
	s.size++
}

func (s *intStack) Pop() (int, error) {
	if s.size > 0 {
		value := s.data[s.size-1]
		s.size--
		s.data = s.data[:s.size]
		return value, nil
	}

	return 0, errors.New("No Such Element")
}

func (s *intStack) Peek() (int, error) {
	if s.size > 0 {
		value := s.data[s.size-1]
		return value, nil
	}

	return 0, errors.New("No Such Element")
}

type MinStack struct {
	data     *intStack
	minimums *intStack
}

func Constructor() MinStack {
	return MinStack{data: &intStack{data: []int{}},
		minimums: &intStack{data: []int{}}}
}

func (m *MinStack) Push(x int) {
	m.data.Push(x)

	currentMin, _ := m.minimums.Peek()

	if m.minimums.size == 0 || x <= currentMin {
		m.minimums.Push(x)
	}
}

func (m *MinStack) Pop() {
	min, _ := m.minimums.Peek()
	top, _ := m.data.Peek()

	if top == min {
		m.minimums.Pop()
	}

	m.data.Pop()
}

func (m *MinStack) Top() int {
	top, _ := m.data.Peek()
	return top
}

func (m *MinStack) GetMin() int {
	min, _ := m.minimums.Peek()
	return min
}
