	.data
a:
	70
	80
	40
	20
	10
	30
	50
	60
n:
	8
	.text
main:
	add %x0, %x0, %x3 
	subi %x0, %x0, %x4 
	load %x0, $n, %x5 
outerloop:
	addi %x3, 1, %x3
	add %x3, %x0, %x9
	subi %x3, 1, %x4 
	blt %x3, %x5, innerloop
	beq %x3, %x5, exitloop 
innerloop:
	blt %x9, %x0, outerloop
	blt %x4, %x0, outerloop
	load %x9, $a, %x7
	load %x4, $a, %x6 
	blt %x6, %x7, swap
	beq %x6, %x7, exitinnerloop
	bgt %x6, %x7, exitinnerloop
swap:
	store %x6, $a, %x9 
	store %x7, $a, %x4 
	subi %x9, 1, %x9 
	subi %x9, 1, %x4 
	jmp innerloop 
exitinnerloop:
	jmp outerloop 
exitloop:
	end