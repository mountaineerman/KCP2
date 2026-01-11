package com.example.kpho;

public class KPhoProp {

    /** Note: Length includes Header + Payload. */
    public static final int kPhoPacketLengthInBytes = 33;

    /** The character used in the delimiter of a Packet. Note: 0x60 = '<'. */
    public static final byte delimiterByte = '<';

    /** The number of consecutive delimiterBytes that mark the delimiter (beginning) of a packet. */
    public static final int numberOfDelimiterBytes = 3;

    /** A byte used to indicate a variable has not been filled (0x00) */
    public static final byte nullByte = 0;
}
