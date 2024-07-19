	.data
a:
	10
	.text
main:
	load %x0, $a, %x3 
	addi %x0, 1, %x4 
	addi %x0, 2, %x5	
	addi %x0, 2, %x6    
	beq %x3, %x4, yesprime
	beq %x3, %x5, yesprime 
loop:
	beq %x6, %x3, yesprime
	div %x3, %x6, %x7   
	beq %x31, %x0, noprime
	addi %x6, 1, %x6 
	jmp loop 
yesprime:
	addi %x0, 1, %x10 
	end 
noprime:
	subi %x0, 1, %x10 
	end 