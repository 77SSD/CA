	.data
a:
	13
	.text
main:
		load %x0, $a, %x3
		divi %x3, 2, %x4
		beq %x31, %x0, yeseven
		beq %x31, 1, yesodd
yeseven:
		subi %x0, 1, %x10
		end
yesodd:
		addi %x0, 1, %x10 
		end