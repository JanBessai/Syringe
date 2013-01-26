/*
 */
package edu.udo.cs.ls14.syringe.interpreter;

/**
 *
 * @author Jan
 */
public class TypeError extends RuntimeException {
    private final Class expected;
    private final Object got;
    public TypeError(Class expected, Object got) {
        super(String.format("Type Error: Cannot match %s with %s", expected.getSimpleName(), got.toString()));
        this.expected = expected;
        this.got = got;
    }

    public Class getExpected() {
        return expected;
    }

    public Object getGot() {
        return got;
    }
}
