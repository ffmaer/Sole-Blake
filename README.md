SOLE-Blake Implementation
==========
A Project for Basic Algorithms, Fall 2010, Tengchao Zhou, New York University

The project mainly achieved five goals.
* It has one-block overhead.
* It can handle the case when the last block is a partial block.
* It can encode a binary file using SOLE and decode it.
* It has a simple Java interface.
* It implements Blake 256 version.

How to use it?
-------------------------
1. Use command line and enter the followings and you will get a 64-bit blake hash and an encodedthen decoded new file

        java SoleBlake lena.bmp 

2. Use command line and enter the followings and you can play around with different versions of SOLE-Blake encoding :)

        java SoleDemo

What is SOLE?
-------------------------
http://www.cs.nyu.edu/~dodis/ps/prefix.pdf

What is Blake?
-------------------------
https://131002.net/blake/
