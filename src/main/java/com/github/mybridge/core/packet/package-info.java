package com.github.mybridge.core.packet;

/**
 * Mysql protocol.
 * 
 * <p>
 * <a href="http://dev.mysql.com/doc/internals/en/client-server-protocol.html">Protocol</a>
 *</p>
 *<pre>
 *<h2>15.1. Organization</h2>
 *
 *The topic is: the contents of logical packets in MySQL version 5.0 client/server communication.

 The description is of logical packets. There will be only passing mention of non-logical considerations, such as physical packets, transport, buffering, and compression. If you are interested in those topics, you may wish to consult another document: "MySQL Client - Server Protocol Documentation" in the file net_doc.txt in the internals directory of the mysqldoc MySQL documentation repository.

 The description is of the version-5.0 protocol at the time of writing. Most of the examples show version-4.1 tests, which is okay because the changes from version-4.1 to version-5.0 were small.

 A typical description of a packet will include:

 "Bytes and Names". This is intended as a quick summary of the lengths and identifiers for every field in the packet, in order of appearance. The "Bytes" column contains the length in bytes. The Names column contains names which are taken from the MySQL source code whenever possible. If the version-4.0 and version-4.1 formats differ significantly, we will show both formats.

 Descriptions for each field. This contains text notes about the usage and possible contents.

 (If necessary) notes about alternative terms. Naming in this document is not authoritative and you will often see different words used for the same things, in other documents.

 (If necessary) references to program or header files in the MySQL source code. An example of such a reference is: sql/protocol.cc net_store_length() which means "in the sql subdirectory, in the protocol.cc file, the function named net_store_length".

 An Example. All examples have three columns:

 -- the field name
 -- a hexadecimal dump
 -- an ascii dump, if the field has character data
 All spaces and carriage returns in the hexadecimal dump are there for formatting purposes only.

 In the later sections, related to prepared statements, the notes should be considered unreliable and there are no examples.

 <h2>15.2. Elements</h2>
 Null-Terminated String: used for some variable-length character strings. The value '\0' (sometimes written 0x00) denotes the end of the string.

 Length Coded Binary: a variable-length number. To compute the value of a Length Coded Binary, one must examine the value of its first byte.

 Value Of     # Of Bytes  Description
 First Byte   Following
 ----------   ----------- -----------
 0-250        0           = value of first byte
 251          0           column value = NULL
 only appropriate in a Row Data Packet
 252          2           = value of following 16-bit word
 253          4           = value of following 32-bit word
 254          8           = value of following 64-bit word
 Thus the length of a Length Coded Binary, including the first byte, will vary from 1 to 9 bytes. The relevant MySQL source program is sql/protocol.cc net_store_length().

 All numbers are stored with the least significant byte first. All numbers are unsigned.

 Length Coded String: a variable-length string. Used instead of Null-Terminated String, especially for character strings which might contain '\0' or might be very long. The first part of a Length Coded String is a Length Coded Binary number (the length); the second part of a Length Coded String is the actual data. An example of a short Length Coded String is these three hexadecimal bytes: 02 61 62, which means "length = 2, contents = 'ab'".
 *</pre>
 */
