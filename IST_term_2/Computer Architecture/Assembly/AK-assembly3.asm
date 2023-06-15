.data
userTextInput:		.space	100
userInputSize:		.word	53
promptEnd:		.asciiz "Do you want to start again(1 - YES, 0 - NO):"
prompt:			.asciiz "Enter string no longer than 50 character:"
promptHowManyVowels:	.asciiz "\nVoweles removed: "
promptOutput:		.asciiz "Output value: "
promptAllocatedData:	.asciiz "Data allocated for stack(Bytes): "
illegalCharArray:	.word	'a','e','i','o','u','y','A','E','I','O','U','Y'
buffer:			.word	52


.text
main:
#			Wiadomoœæ dla u¿ytkownika i zapisanie danych wejœciowych
#			SYSCALL: display string
	li	$v0, 4
	la	$a0, prompt
	syscall
#			SYSCALL: string input
	li	$v0, 8
	la	$a0, userTextInput
	lw	$a1, userInputSize
	syscall
	

	
#			Iterowanie ci¹gu znaków od koñca do pocz¹tku i wpisywanie wartoœci na stos
	la	$t2, userTextInput

	
	
	li	$t9, 0 #$t9 - licznik samoglosek
	li	$v0, 0
loop:
	add	$t9, $t9, $v0
	bge	$t3, 51, displayStack	#Warunek pêtli

	
	lb	$t4, 50($t2)	#Wpisanie ostatniego znaku do pamiêci	
	
	
	addi    $t3, $t3, 1	#Zwiêkszenie licznika pêtli
	subi	$t2, $t2, 1	#Przesuniêcie addresu o jeden w dó³
	
	move	$a0, $t4

	j deleteIllegalChars
post:
	addi	$sp, $sp, -1	#zmniejszenie wskaŸnika stosu
	sb	$t4, 0($sp)		#wpisanie wartoœci na stos
	j loop

deleteIllegalChars:
	la      $t1, illegalCharArray
	li	$t0, 0
	move	$v0, $zero
loopIllegarlArray:
	bge     $t0, 12, post

	lw      $t8, 0($t1)
	addi    $t1, $t1, 4
	
	beq	$t8, $a0, foundVowel

	addi    $t0, $t0, 1
	j loopIllegarlArray
	
foundVowel:
	addi	$v0, $zero, 1
	j loop
	

displayStack:
	jal stackHeight
displayStackPre:
	move	$t6, $v0
	addi	$t6, $t6, 1
	sub	$sp, $sp, $t6
		
	li	$t0, 0
	la	$t2, buffer
	add	$t2, $t2, $t6
	subi	$t2, $t2, 3
	
stackLoop:
	lb	$t1, 0($sp)
	addi	$sp, $sp, 1
	
	beqz	$t1 End
	
	addi	$t0, $t0, 1
	
	
	#Saving to buffer
	#subi	$t2, $t2, 1
	sb	$t1, 0($t2)
	subi	$t2, $t2, 1	#Przesuniêcie addresu o jeden w dó³
	
	j stackLoop

#			SYSCALL: TERMINATE EXECUTION
End:
	li	$t1, '\n'
	add	$t2, $t2, $t6
	sb	$t1, 0($t2)
	
	li	$t1, 0
	addi	$t2, $t2, 1
	sb	$t1, 0($t2)
	#Display output informations - vowel count, buffor
	#			SYSCALL: display prompt	
	li	$v0, 4
	la	$a0, promptHowManyVowels
	syscall

	li      $v0, 1    #Code for displaying Integer
	move	$a0, $t9
	syscall
	
	li      $v0, 11    #Code for displaying Integer
	li	$a0, '\n'
	syscall
	
	li      $v0, 4    #Code for displaying Integer
	la	$a0, promptOutput
	syscall
	
	li      $v0, 4    #Code for displaying String
	la	$a0, buffer
	syscall
	
	li      $v0, 4    #Code for displaying Integer
	la	$a0, promptAllocatedData
	syscall
	
	
	subi	$t0, $t0, 1 
	li      $v0, 1    #Code for displaying Integer
	move	$a0, $t0
	syscall
	
	
	li	$v0, 10
	syscall

stackHeight:
	lb	$t1, 0($sp)
	addi	$sp, $sp, 1
	
	beqz	$t1 displayStackPre
	
	addi	$v0, $v0, 1
	j stackHeight
	

