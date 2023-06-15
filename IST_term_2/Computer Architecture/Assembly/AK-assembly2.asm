#Program variables and data
.data
typeOfOperation:	.asciiz	"Choose type of operation you want to perform(S - encrypt, d - decrypt):"
encryptionStartPrompt:		.asciiz	"Enter sentence to encode:"
keyPrompt:		.asciiz	"Enter the key:"
dSytuation:		.asciiz	"Value entered equal d"
tryagain:		.asciiz	"\nDo you want to try again?(1 - yes, 0 - no):"
input:			.space	81
space:			.asciiz " "
d:			.asciiz "d"
s:			.asciiz "s"
inputSize:		.word	51
inputLetterSize:	.word	3
keySize:		.word	9
inputKey:		.space	81
const:			.byte	26


.global main

#Execution of program
.text
main:
#Display first prompt
	li $v0, 4
	la $a0, typeOfOperation
	syscall
#Take string data: type of operation
	li $v0, 8
	la $a0, input
	lw $a1, inputLetterSize
	syscall
#Reaction based on input
	la $s0, input
	lbu $t0, 0($s0) #User input
	la $s1, d
	lbu $t1, 0($s1) #Letter "d"
	la $s2, s
	lbu $t2, 0($s2) #Letter "s"
#Compare input with possible options
	beq $t1, $t0, decrypt #compare input with "d"
	beq $t2, $t0, encrypt #compare input with "s"
	j wantToTryAgain
removeSpacesAndInterpunction:
	lb $t1, ($t0)
	la $t2, space
	beq $t1, $t2, encrypt
	addu $t0, $t0, 1

#Encrypting the user input value	
encrypt:
	jal inputStrAndKey
encryptStart:
#Display values
	#add $t3, $a0, 0
	la $t3, inputKey
	la $t0, input
	lb $t9, const 
	add $t1, $t3, 0
	#la $t1, inputKey
loop:
	#Load byte of word
	lb $t8, 0($t0)
	lb $t7, 0($t1)

	#End program if end of word or \n
	beq $t8,10,wantToTryAgain
	beqz $t8, wantToTryAgain
	
	#If key lower
checkKey:
	#bge $t7, 97, changeKey
postCheckKey:
	#If Uppercase
	ble $t8, 90, uppercaseOrOther
	bge $t8, 97, lowercaseOrOther
	
lowercaseOrOther:
	bge $t8, 123, loop
	sub $t8, $t8, 32
	j uppercaseLoop
	
	
uppercaseOrOther:
	bge $t8, 65, uppercaseLoop
	ble $t8, 64, numericOrOther
numericOrOther:
	bge $t8, 48, numericOrOther2
	j skipEncrypt
	
numericOrOther2:
	ble $t8, 57, skipEncrypt
	j skipEncrypt
	
uppercaseLoop:
	#If end of key
	bne $t7,10,uppercaseLoop1
	
	add $t1, $t3, 0
	lb $t7, 0($t1)

uppercaseLoop1:
	add $t8, $t8, $t7
	div $t8, $t9
	mfhi $t8
	add $t8, $t8, 'A'
	
	#Display return value
displayEncrypted:
	li $v0, 11
	la $a0, 0($t8)
	syscall
	
	#Next letter in word
	add $t1,$t1,1
skipEncrypt:
	add $t0,$t0,1
	j loop
	
	
decrypt:
	jal inputStrAndKey
decryptStart:
#Display values
	#add $t3, $a0, 0
	la $t3, inputKey
	la $t0, input
	lb $t9, const 
	add $t1, $t3, 0
	#la $t1, inputKey
loopD:
	#Load byte of word
	lb $t8, 0($t0)
	lb $t7, 0($t1)

	#End program if end of word or \n
	beq $t8,10,wantToTryAgain
	beqz $t8, wantToTryAgain
	
	#If Uppercase
	ble $t8, 90, uppercaseOrOtherD
	bge $t8, 97, lowercaseOrOtherD
	
lowercaseOrOtherD:
	bge $t8, 123, loopD
	sub $t8, $t8, 32
	j uppercaseOrOtherD
	
	
uppercaseOrOtherD:
	bge $t8, 65, uppercaseLoopD
	ble $t8, 64, numericOrOtherD
numericOrOtherD:
	bge $t8, 48, numericOrOther2D
	j skipDecrypt
	
numericOrOther2D:
	ble $t8, 57, skipDecrypt
	j skipDecrypt
	
uppercaseLoopD:
	#If end of key
	bne $t7,10,uppercaseLoop1D
	
	add $t1, $t3, 0
	lb $t7, 0($t1)

uppercaseLoop1D:
	sub $t8, $t8, $t7
	add $t8, $t8, 26
	div $t8, $t9
	mfhi $t8
	add $t8, $t8, 'A'

	#Display return value
displayDecrypt:
	li $v0, 11
	la $a0, 0($t8)
	syscall
	
	#Next letter in word
	add $t1,$t1,1
skipDecrypt:
	add $t0,$t0,1
	j loopD
	
#End program
end:
	li $v0, 10
	syscall
	
inputStrAndKey:
#Input expression and key
#Display prompt assking for input string
	li $v0, 4
	la $a0, encryptionStartPrompt
	syscall
#Take string data: sentence to encode
	li $v0, 8
	la $a0, input
	lw $a1, inputSize
	syscall
#Display prompt assking for input string
	li $v0, 4
	la $a0, keyPrompt
	syscall
#Take string data: sentence to encode
	li $v0, 8
	la $a0, inputKey
	lw $a1, keySize
	syscall
	jr $ra
	
changeKey:
	bge $t7, 123, end
	sub $t7, $t7, 32
	j postCheckKey
	
wantToTryAgain:
	#Display prompt
	li $v0, 4
	la $a0, tryagain
	syscall
#Take string data: type of operation
	li $v0, 8
	la $a0, input
	lw $a1, inputLetterSize
	syscall
#Reaction based on input
	la $t1, input
	lb $t1, 0($t1)
	beq $t1, '0', end
	beq $t1, '1', main
	j wantToTryAgain
