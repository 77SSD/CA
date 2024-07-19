	.data
n:
	10
	.text
main:
	addi %x0, 65535, %x3 
	addi %x0, 1, %x4 
	store %x0, 0, %x3 
	subi %x3, 1, %x3 
	store %x4, 0, %x3 
	load %x0, $n, %x5
	subi %x3, 1, %x3
	addi %x0, 2, %x4
	beq %x0, %x5, endloop
	beq %x5, 1, endloop 
loopc:
	beq %x4, %x5, endloop
	addi %x3, 1, %x6
	addi %x3, 2, %x7
	load %x6, 0, %x8
	load %x7, 0, %x9
	add %x8, %x9, %x10
	store %x10, 0, %x3 
	subi %x3, 1, %x3 
	addi %x4, 1, %x4 
	jmp loopc
endloop:
	end 