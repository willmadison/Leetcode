package august

import (
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestRateLimitedLogger(t *testing.T) {
	logger := Constructor()

	assert.True(t, logger.ShouldPrintMessage(1, "foo"))

	assert.True(t, logger.ShouldPrintMessage(2, "bar"))

	assert.False(t, logger.ShouldPrintMessage(3, "foo"))

	assert.False(t, logger.ShouldPrintMessage(8, "bar"))

	assert.False(t, logger.ShouldPrintMessage(10, "foo"))

	assert.True(t, logger.ShouldPrintMessage(11, "foo"))
}
