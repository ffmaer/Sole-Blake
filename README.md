SOLE-BLAKE Implementation
==========
A Project for Basic Algorithms, Fall 2010 Tengchao Zhou , New York University

The project mainly achieved four goals. The first achievement is that it has one-block overhead and last block that is a partial block implementation; the second one is that it can read binary file do SOLE-Encoding and decode the SOLE-Encoding back to a binary file; the third one is that it has a simple Java interface; finally the last one is that it implements Blake 256 version, which is a finalist of SHA-3 competition.

How to insert EOF two blocks before the end?
-------------------------
We actually need 3 blocks buffer to ensure EOF is inserted before the last ending two blocks . If it is still far from the EOF, every time when you have four full blocks in you buffer, you can just send two blocks to the SOLE encoder. And SOLE encoder will handle the rest of the work. So two full blocks were left in your buffer. When you read the last binary bit of your input file, you will discover that your four block buffer is not fully filled (in rare case, you will have exactly have four full blocks; this also depends on the block size you choose; if you choose a b to be a multiple of 8, this is more than likely to happen). If you have more than three blocks, you can safely send the two full blocks to you SOLE encoder; then you send the rest (that's a little bit more than one block) to the EOF handler. If you have less or equal to three blocks in your buffer, you just send all these blocks directly to the EOF handler.

What if the last block is a partial block?
-------------------------
First I would like to tell why this is a problem. As SOLE actually convert a block into a value, for example, if we have a block size of 9 (b=9), sometime we will have the last block like 0000110, which means the last two bits are empty. SOLE will take this block as value 6 (binary to decimal) and do the rest of the work. However, when the SOLE decoder see 6 what it will give back to us is 000000110, which is not our originally input. Because the SOLE decoder will take this as a normal block and think it will be 9 bits, and covert 6 to a 9 bits binary string by padding zero in the front. However this is incorrect. I will show you how I solve this problem. We will label the empty bits with x, and we get 0000110xx. Since the first bit of the last block is 0 we flip all the bits that are not empty, so we get 1111001xx and then we reallocate the block, which mean we put those xx in the front of the block instead of putting them at the end, so we have xx111001; then we substitute every x with 0 so we get 00111001, then we can safely send this to the decoder. When decoding, we get rid all zeros until we hit the first one 111001 and then we flip the number back so we get 000110; then we pad the number with x at the end in order to make it 9 bits 000110xx. So we need an extra one bit to remember whether we have done this transformation to the last block or not. So we ends up with having for EOFs. Concretely, B + 0 means EOF; B + 1 means EOF with an extra 1(this is another problem); B + 2 means EOF with last block flipped; B + 3 means EOF with and extra 1 and last block flipped.

Why Blake?
-------------------------
This project gives me a self introduction of some famous hash functions like MD5, SHA-1, etc (I would also like to mention MD6). Since I did not find a very nice hash function that I can directly plug into SOLE so I did learn how to build a hash function from the very beginning. And I did try to do that, and I knew it was like reinventing the wheel. I was stopped by Dodis, so I finally choose to use Blake, which is a nice fit of SOLE to make it a on-line hash function.

Some extra words before closing up
-------------------------
This project gave me a good chance to actually read a binary file at the bit level. And Java has something called signed byte, which is painful when I need to convert bits back to bytes. I was trying to implement the code in C++ with a GUI web interface using GMP library for big number operations but later on I switch back to Java because I do not have knowledge in building a C++ graphical user interface. I was trying to build a very nice web interface, but due to the time limitation, I can't actually build one.

* Blake's author's website http://www.131002.net/blake/
* XOR-Hash : A Hash Function Based on XOR, http://c.ffm.cn/gAfD8N
