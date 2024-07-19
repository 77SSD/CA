	.data
a:
	12321
	.text
main:
	load %x0, $a, %x3
	load %x0, $a, %x9
	add %x0, %x0, %x5 
loop:
	divi %x3, 10, %x4
	beq %x3, %x0, endloop
	muli %x5, 10, %x5 
	add %x5, %x31, %x5 
	add %x4, %x0, %x3 
	jmp loop 
endloop:
		beq %x9, %x5, yespalindrome
		bgt %x9, %x5, nopalindrome 
		blt %x9, %x5, nopalindrome
yespalindrome:
		addi %x0, 1, %x10 
		end 
nopalindrome:
		subi %x0, 1, %x10 
		end 