# Makefile for Java project

# Variables
JAVAC = javac
JAVA = java
JFLAGS = -d bin -cp bin
SOURCES = GETClient.java AggregationServer.java ContentServer.java
CLASSES = $(SOURCES:.java=.class)
BIN_DIR = bin/

# Default target
all: $(BIN_DIR) $(CLASSES)

# Rule to create the bin directory
$(BIN_DIR):
	mkdir -p $(BIN_DIR)

# Generic rule for compiling java files
%.class: %.java
	$(JAVAC) $(JFLAGS) $<

# Rule to run the GETClient
run: all
	$(JAVA) -cp $(BIN_DIR) GETClient

# Rule to clean the bin directory
clean:
	$(RM) -r $(BIN_DIR)
