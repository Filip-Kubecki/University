.data
initialPrompt:		.asciiz	"Enter power of exponent you want to use: "
valueOfX:		.space	4
fp1:			.float	1.0
fp2:			.float	1000.0
floatLimiter:		.float	0.0000001

.text
main:
	#Display prompt
	li	$v0, 4
	la	$a0, initialPrompt
	syscall
	#Read Float
	li	$v0, 6
	syscall
	
	#Declaring variables
	lwc1	$f2, fp1	#counter
	lwc1	$f4, fp1	#value one
	lwc1	$f6, fp2	#limiter 
	lwc1	$f20, floatLimiter	#smallest float
	subi	$sp, $sp, 4
	swc1	$f4, 0($sp)
loop:	
	lwc1	$f8, 0($sp) #a_n-1 
	
	div.s	$f10, $f0, $f2	# x/n 
	
	mul.s	$f10, $f10, $f8	# a_n-1 * (x/n)
	
	#Warunek gdzie limiter
	c.le.s	$f10, $f20
	bc1t	addStack

	subi	$sp, $sp, 4	#make place in stack
	swc1	$f10, 0($sp)	#store value a_n on stack
	
	add.s	$f2, $f2, $f4
	j loop
	
addStack:
	li	$t0, 0
loop2:
	beq	$t0, 1000, end

	lwc1	$f2, 0($sp)
	
	add.s	$f30, $f30, $f2
	
	addi	$sp, $sp, 4	#move stack pointer
	
	addi	$t0, $t0, 1
	j loop2
	
	
end:
	#Display float value
	mov.s	$f12, $f30
	li	$v0, 2
	syscall
	
	#Terminate execution
	li	$v0, 10
	syscall
	
##################################
#Display float value
	mov.s	$f12, $f10
	li	$v0, 2
	syscall	
	li	$a0, ' '
	li	$v0, 11
	syscall	
	
	
.ktext 0x80000180
	mfc0	$k0,$14   	# Coprocessor 0 register $14 has address of trapping instruction
	addi	$k0,$k0,32 	# Add 4 to point to next instruction
	mtc0	$k0,$14   	# Store new address back into $14
	eret          	# Error return; set PC to value in $14
 

	
