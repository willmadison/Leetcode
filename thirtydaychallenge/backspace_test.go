package thirtydaychallenge

import (
	"fmt"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestBackspaceCompare(t *testing.T) {
	cases := []struct {
		given struct {
			s, t string
		}
		expected bool
	}{
		{
			struct {
				s, t string
			}{s: "ab#c", t: "ad#c"},
			true,
		},
		{
			struct {
				s, t string
			}{s: "ab##", t: "c#d#"},
			true,
		},
		{
			struct {
				s, t string
			}{s: "a##c", t: "#a#c"},
			true,
		},
		{
			struct {
				s, t string
			}{s: "a#c", t: "b"},
			false,
		},
	}

	for _, tc := range cases {
		t.Run(fmt.Sprintf("backspaceCompare(%v, %v)", tc.given.s, tc.given.t), func(t *testing.T) {
			actual := backspaceCompare(tc.given.s, tc.given.t)
			assert.Equal(t, tc.expected, actual)
		})
	}
}
