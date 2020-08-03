package august

type Logger struct {
	timestampsByMessage map[string]int
}

func Constructor() Logger {
	return Logger{timestampsByMessage: make(map[string]int)}
}

func (l *Logger) ShouldPrintMessage(timestamp int, message string) bool {
	if lastPrinted, present := l.timestampsByMessage[message]; !present || timestamp >= lastPrinted+10 {
		l.timestampsByMessage[message] = timestamp
		return true
	}

	return false
}
