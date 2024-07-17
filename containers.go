package leetcode

import (
	"errors"
	"fmt"
)

type Queue[C any] interface {
	Enqueue(value C)
	Dequeue() (C, error)
	Peek() (C, error)
	Size() int
}

type queue[C any] struct {
	data []C
	size int
}

func NewQueue[C any]() Queue[C] {
	return &queue[C]{data: []C{}}
}

func (q *queue[C]) Enqueue(value C) {
	q.data = append(q.data, value)
	q.size += 1
}

func (q *queue[C]) Dequeue() (C, error) {
	if q.size > 0 {
		value := q.data[0]
		q.size -= 1
		q.data = q.data[1:]

		return value, nil
	}

	var zero C

	return zero, errors.New("no such element")
}

func (q *queue[C]) Peek() (C, error) {
	if q.size > 0 {
		value := q.data[0]
		return value, nil
	}

	var zero C

	return zero, errors.New("no such element")
}

func (q *queue[C]) String() string {
	return fmt.Sprintf("%v", q.data)
}

func (q *queue[C]) Size() int {
	return q.size
}

type Stack[C any] interface {
	Push(value C)
	Pop() (C, error)
	Peek() (C, error)
	Size() int
}

type stack[C any] struct {
	data []C
	size int
}

func NewStack[C any]() Stack[C] {
	return &stack[C]{data: []C{}}
}

func (s *stack[C]) Push(value C) {
	s.data = append(s.data, value)
	s.size += 1
}

func (s *stack[C]) Pop() (C, error) {
	if s.size > 0 {
		value := s.data[s.size-1]
		s.size -= 1
		s.data = s.data[:s.size]
		return value, nil
	}

	var zero C

	return zero, errors.New("no such element")
}

func (s *stack[C]) Peek() (C, error) {
	if s.size > 0 {
		value := s.data[s.size-1]
		return value, nil
	}

	var zero C

	return zero, errors.New("no such element")
}

func (s *stack[C]) String() string {
	return fmt.Sprintf("%v", s.data)
}

func (s *stack[C]) Size() int {
	return s.size
}
