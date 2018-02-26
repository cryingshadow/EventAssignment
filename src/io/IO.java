package io;

public class IO {

    private final InputReader reader;
    private final AssignmentWriter writer;

    public IO(final String[] args) {
        this.reader = new CSVFileReader(args[0]);
        this.writer = new ConsoleAndFileWriter(args[1]);
    }

    public InputReader getReader() {
        return this.reader;
    }

    public AssignmentWriter getWriter() {
        return this.writer;
    }

}
